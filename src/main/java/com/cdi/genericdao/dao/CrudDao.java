/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdi.genericdao.dao;

import com.cdi.genericdao.dao.BaseDao;
import com.cdi.genericdao.model.BaseEntity;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 *
 * @author rafael-pestano
 */
@Named("crudDao")
public class CrudDao<T extends BaseEntity<ID>, ID> implements Serializable{
    
    @Inject
    protected BaseDao<T,ID> dao;
    
    protected Class<T> entityClass;
    
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
   
    public EntityManager getEntityManager(){
        return dao.getEntityManager();
    }
    
    public T find(ID id){
        return dao.find(id, getEntityClass());
    }
    
    public void delete(ID id){
         dao.delete(id, getEntityClass());
    }
    
    public T update(T t){
        return dao.update(t);
    }
    
    public void insert(T t){
        dao.insert(t);
    }
    
    public List<T> findAll(){
        return dao.findAll(getEntityClass());
    }
    
    public List<T> findWithNamedQuery(String namedQueryName){
        return dao.findWithNamedQuery(namedQueryName);
    }
}
