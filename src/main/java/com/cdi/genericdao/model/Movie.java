/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdi.genericdao.model;

import javax.persistence.Entity;

/**
 *
 * @author rafael-pestano
 */
@Entity
public class Movie extends BaseEntity<Long>{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
