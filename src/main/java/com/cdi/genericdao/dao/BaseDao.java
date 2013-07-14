/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdi.genericdao.dao;

import com.cdi.genericdao.model.BaseEntity;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rmpestano
 */
@Stateless
@Named("baseDao")
public class BaseDao<T extends BaseEntity<ID>, ID> implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;
    private Class<T> entityClass;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Class<T> getEntityClass() {
        if (entityClass == null) {
            //only works if one extends BaseDao, we will take care of it with CDI
            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    //utility database methods
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public T find(ID id) {
        return (T) this.entityManager.find(getEntityClass(), id);
    }

    public void delete(ID id) {
        Object ref = this.entityManager.getReference(getEntityClass(), id);
        this.entityManager.remove(ref);
    }

    public T update(T t) {
        return (T) this.entityManager.merge(t);
    }

    public void insert(T t) {
        this.entityManager.persist(t);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findAll() {
        return entityManager.createQuery("Select entity FROM "+getEntityClass().getSimpleName() +" entity").getResultList();
    } 

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findWithNamedQuery(String namedQueryName) {
        return this.entityManager.createNamedQuery(namedQueryName).getResultList();
    }
    //put here other utility method you want to share with your daos
}
