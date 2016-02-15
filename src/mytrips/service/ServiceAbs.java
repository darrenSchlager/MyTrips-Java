/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Darren
 */
public abstract class ServiceAbs {
    
    protected final String CONN_STRING = "jdbc:mysql://localhost/MyTrips?user=root&password=";
    
    protected Connection getConnection() throws Exception {
        return DriverManager.getConnection(CONN_STRING);
    }
    
}
