/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import java.io.*;
import java.util.*;

/**
 *
 * @author Darren
 */
public class Factory {
    public IService getService(String serviceName) throws Exception {
        String implName = getImplName(serviceName);
        Class c = Class.forName(implName);  //loading the byte code for the Impl
        return (IService)c.newInstance();   //instantiate an object
    }
    
    private String getImplName(String serviceName) throws Exception {
        FileInputStream fis = new FileInputStream("FactoryProperties.txt");
        Properties props = new Properties();
        props.load(fis);
        return props.getProperty(serviceName);
    }
}
