/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;
import mytrips.domain.*;
import java.sql.*;

/**
 *
 * @author Darren
 */
public class LoginSvcStatementImpl implements ILoginSvc {
    
    private final String CONN_STRING = "jdbc:mysql://localhost/MyTrips?user=root&password=";
    
    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(CONN_STRING);
    }
    
    public Login create(Login login) throws Exception {
        try {
            Connection connection = getConnection();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }

        return login;
    }
    
}
