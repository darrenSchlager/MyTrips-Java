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
public class UserSvcStatementImpl extends ServiceAbs implements IUserSvc {
    
    @Override
    public User create(User user) throws Exception {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO user (first_name, last_name) VALUES ('"+user.getFirstName()+"', '"+user.getLastName()+"');";
            statement.executeUpdate(sql);
            
            /* get the user_id that was just created */
            sql = "SELECT last_insert_id() as user_id;";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.first()) {
                user.setUserId(rs.getInt("user_id"));
            }
            /**/
            
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }

        return user;
    }
    
    @Override
    public User retrieve(User user) throws Exception {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM user WHERE user_id='"+user.getUserId()+"';";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.first()) {
                user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"));
                user.setLogin(new LoginSvcStatementImpl().retrieve(new Login(user.getUserId())));
                user.setTrips(new TripSvcStatementImpl().retrieveByUserId(new Trip(-1, user.getUserId())));
            }
            else {
                user = null;
            }
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return user;
    }
    
    @Override
    public User update(User user) throws Exception {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "UPDATE user SET first_name='"+user.getFirstName()+"', last_name='"+user.getLastName()+"' WHERE user_id="+user.getUserId()+";";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }

        return user;
    }
    
    @Override
    public User delete(User user) throws Exception {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM user WHERE user_id="+user.getUserId()+";";
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
