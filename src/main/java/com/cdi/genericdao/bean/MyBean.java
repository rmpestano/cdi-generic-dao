/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdi.genericdao.bean;

import com.cdi.genericdao.dao.BaseDao;
import com.cdi.genericdao.dao.CarDao;
import com.cdi.genericdao.dao.PersonDao;
import com.cdi.genericdao.model.Car;
import com.cdi.genericdao.model.Person;
import com.cdi.genericdao.qualifier.Dao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author rmpestano
 */
@Named
@ViewAccessScoped
public class MyBean implements Serializable{
    
    private List<Person> personList;
    private List<Car> carList;
    private Long id;
    private Person person;
    
    @Inject CarDao carDao;
    
    @Inject PersonDao personDao;
    
    @Inject @Dao 
    BaseDao<Person,Long> genericPersonDao;
    
    @PostConstruct
    public void init(){
        if(genericPersonDao.findAll().isEmpty()){
            for (int i = 0; i < 10; i++) {
                Person p = new Person("Person"+i, i);
                genericPersonDao.insert(p);
            }
        }
        
         if(carDao.findAll().isEmpty()){
            for (int i = 0; i < 10; i++) {
                Car c = new Car("Car"+i, i);
                carDao.insert(c);
            }
        }
        
        
    }
    
    public List<Person> getPersonList(){
        if(personList == null){
            personList = genericPersonDao.findAll();
        }
        return personList;
    }
    
    public List<Car> getCarList(){
        if(carList == null){
            carList = carDao.findAll();
        }
        return carList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
    
    public void findPersonById(Long id){
         person = genericPersonDao.find(id);
    }
    
}
