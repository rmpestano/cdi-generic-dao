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
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 *
 * @author rmpestano
 */
@Model
public class MyBean implements Serializable{
    
    private List<Person> personList;
    private List<Car> carList;
    
    @Inject CarDao carDao;
    
    @Inject PersonDao personDao;
    
    @Inject BaseDao<Person,Long> genericPersonDao;
    
    @PostConstruct
    public void init(){
        if(personDao.findAll().isEmpty()){
            for (int i = 0; i < 10; i++) {
                Person p = new Person("Person"+i, i);
                personDao.insert(p);
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
}
