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
public class Person extends BaseEntity<Long> {

    private String name;
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
