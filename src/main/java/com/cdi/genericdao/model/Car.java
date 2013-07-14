/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdi.genericdao.model;

import javax.persistence.Entity;

/**
 *
 * @author rmpestano
 */
@Entity
public class Car extends BaseEntity<Integer>{
    
    private String model;
    private Double price;

    public Car() {
    }

    public Car(String model, double price) {
        this.model = model;
        this.price = price;
    }
    
    

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
}
