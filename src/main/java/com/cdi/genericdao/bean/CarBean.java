/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdi.genericdao.bean;

import com.cdi.genericdao.dao.CrudDao;
import com.cdi.genericdao.dao.CarDao;
import com.cdi.genericdao.model.Car;
import com.cdi.genericdao.model.Movie;
import com.cdi.genericdao.qualifier.Dao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author rmpestano
 */
@Named
@ViewAccessScoped
public class CarBean implements Serializable{
    
    private List<Car> carList;
    private List<Car> filteredValue;//datatable filteredValue attribute
    private Integer id;
    private Car car;
    
    @Inject CarDao carDao;
    
    @Inject @Dao 
    CrudDao<Car,Integer> genericDao;//reuse generic dao for basic crud operation in various entities
    
    @Inject @Dao 
    CrudDao<Movie,Long> genericDao2;//reuse generic dao for basic crud operation in various entities
//    @Inject @Dao 
//    BaseDao<Person,Long> genericDao;
//    @Inject @Dao 
//    BaseDao<Client,IDClass> genericDao;
    
    @PostConstruct
    public void init(){
        if(genericDao.findAll().isEmpty()){
            for (int i = 1; i < 10; i++) {
                Car c = new Car("Car"+i, i);
                genericDao.insert(c);
            }
        }
        //same as above
//         if(carDao.findAll().isEmpty()){
//            for (int i = 0; i < 10; i++) {
//                Car c = new Car("Car"+i, i);
//                carDao.insert(c);
//            }
//        }
        
        
    }
    
    public List<Car> getCarList(){
        if(carList == null){
            carList = carDao.findAll();
        }
        return carList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Car getCar() {
        if(car == null){
            car = new Car();
        }
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    
    
    public void findCarById(Integer id){
         car = genericDao.find(id);
    }

    public List<Car> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Car> filteredValue) {
        this.filteredValue = filteredValue;
    }
    
    public void remove(){
        if(car != null && car.getId() != null){
            genericDao.delete(car.getId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Car "+car.getModel() +" removed successfully"));
            clear();
        }
    }
    
    public void update(){
        String msg;
        if(car.getId() == null){
             genericDao.insert(car);  
             msg = "Car "+car.getModel() +" created successfully";
        }
        else{
           genericDao.update(car); 
           msg = "Car "+car.getModel() +" updated successfully";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
        clear();//reload car list
    }
    
    public void clear(){
        car = new Car();
        carList = null;
        id = null;
    }
    
    public void onRowSelect(SelectEvent event) {
        setId(((Car) event.getObject()).getId());
        findCarById(getId());  
    }  
           
    public void onRowUnselect(UnselectEvent event) {  
        car = new Car();
    }  
}
