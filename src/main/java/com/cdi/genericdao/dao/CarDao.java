/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdi.genericdao.dao;

import com.cdi.genericdao.model.Car;
import javax.ejb.Stateless;

/**
 *
 * @author rmpestano
 */
@Stateless
public class CarDao extends CrudDao<Car, Integer>{
    //put here specific car business
}
