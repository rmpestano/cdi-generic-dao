/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdi.genericdao.producer;

import com.cdi.genericdao.dao.BaseDao;
import com.cdi.genericdao.model.BaseEntity;
import com.cdi.genericdao.qualifier.Dao;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rmpestano
 */
@Stateless
public class DaoProducer implements Serializable{
    private static final long serialVersionUID = 1L;
	
	 
	@PersistenceContext 
	EntityManager em;
	
	 @Produces @Dependent @Dao
	 public <ID, T extends BaseEntity<ID>> BaseDao<T,ID> produce(InjectionPoint ip){
		 if(ip.getAnnotated().isAnnotationPresent(Dao.class)){
			 BaseDao<T, ID> genericDao = new BaseDao<T,ID>();
			 ParameterizedType type = (ParameterizedType) ip.getType();         
			 Type[] typeArgs = type.getActualTypeArguments();        
			 Class<T> entityClass = (Class<T>) typeArgs[0];
			 genericDao.setEntityClass(entityClass);
			 genericDao.setEntityManager(em);
			 return genericDao;
		 }
		 throw new IllegalArgumentException("Annotation @Dao is required when injecting BaseDao");
	}
    
}
