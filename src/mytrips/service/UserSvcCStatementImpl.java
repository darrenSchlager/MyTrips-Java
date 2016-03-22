/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;
import java.sql.*;
import mytrips.domain.Login;
import mytrips.domain.Trip;
import mytrips.domain.User;

/**
 *
 * @author Darren
 */
public class UserSvcCStatementImpl extends ServiceAbs implements IUserSvc {
   
    @Override
    public User create(User user) throws Exception {
        try {
            Connection connection = getConnection();
            CallableStatement cstatement = connection.prepareCall("{CALL create_user(?,?)}");
            cstatement.setString(1, user.getFirstName());
            cstatement.setString(2, user.getLastName());
            cstatement.executeUpdate();
            
            /* get the user_id that was just created */
            cstatement = connection.prepareCall("{CALL last_insert_id()}");
            ResultSet rs = cstatement.executeQuery();
            if(rs.first()){
                user.setUserId(rs.getInt("last_insert_id"));
            }
            /**/
            
            cstatement.close();
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
            CallableStatement cstatement = connection.prepareCall("{CALL retrieve_user(?)}");
            cstatement.setInt(1, user.getUserId());
            ResultSet rs = cstatement.executeQuery();
            if(rs.first()) {
                user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"));
                user.setLogin(new LoginSvcStatementImpl().retrieve(new Login(user.getUserId())));
                user.setTrips(new TripSvcStatementImpl().retrieveByUserId(new Trip(-1, user.getUserId())));
            }
            else {
                user = null;
            }
            cstatement.close();
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
            CallableStatement cstatement = connection.prepareCall("{CALL update_user(?,?,?)}");
            cstatement.setString(1, user.getFirstName());
            cstatement.setString(2, user.getLastName());
            cstatement.setInt(3, user.getUserId());
            cstatement.executeUpdate();
            cstatement.close();
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
            new TripSvcStatementImpl().deleteByUserId(new Trip(-1, user.getUserId()));
            new LoginSvcStatementImpl().delete(new Login(user.getUserId()));
            CallableStatement cstatement = connection.prepareCall("{CALL delete_user(?)}");
            cstatement.setInt(1, user.getUserId());
            cstatement.executeUpdate();
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return user;
    }
}
