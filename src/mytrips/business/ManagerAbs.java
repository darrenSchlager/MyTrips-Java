/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.business;

import mytrips.service.*;

/**
 *
 * @author Darren
 */
public abstract class ManagerAbs {
    
    private Factory factory = new Factory();
    
    protected IService getService(String name) throws Exception {
        return factory.getService(name);
    }
    
}
