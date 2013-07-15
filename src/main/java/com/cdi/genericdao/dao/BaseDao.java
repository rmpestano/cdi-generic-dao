/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdi.genericdao.dao;

import com.cdi.genericdao.model.BaseEntity;
import java.io.Serializable;
import java.util.List;
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

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    
    //utility database methods
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public T find(ID id, Class<T> type) {
        return (T) this.entityManager.find(type, id);
    }

    public void delete(ID id, Class<T> type) {
        Object ref = this.entityManager.getReference(type, id);
        this.entityManager.remove(ref);
    }

    public T update(T t) {
        return (T) this.entityManager.merge(t);
    }

    public void insert(T t) {
        this.entityManager.persist(t);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findAll(Class<T> type) {
        return entityManager.createQuery("Select entity FROM "+type.getSimpleName() +" entity").getResultList();
    } 

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<T> findWithNamedQuery(String namedQueryName) {
        return this.entityManager.createNamedQuery(namedQueryName).getResultList();
    }
    //put here other utility method you want to share with your daos
}
