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
public class LoginSvcStatementImpl implements ILoginSvc{
    private final String CONN_STRING = "jdpc:mysql://localhost/MyTrips?username=root&password=";
    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(CONN_STRING);
    }
    
    public Login create(Login login) throws Exception {
        Connection connection = getConnection();
        
        connection.close();
        return login;
    }
    
}
