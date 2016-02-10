/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;
import mytrips.domain.User;
import java.sql.*;
/**
 *
 * @author Darren
 */
public class UserSvcStatementImpl implements IUserSvc{
    
    private final String CONN_STRING = "jdbc:mysql://localhost/MyTrips?user=root&password=";
    
    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(CONN_STRING);
    }
    
    @Override
    public User create(User user) throws Exception {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO user (first_name, last_name) VALUES ('"+user.getFirstName()+"', '"+user.getLastName()+"');";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }

        return user;
    }
}
