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
public class UserSvcPStatementImpl extends ServiceAbs implements IUserSvc {
    
    @Override
    public User create(User user) throws Exception {
        try {
            Connection connection = getConnection();
            String sql = "INSERT INTO user (first_name, last_name) VALUES (?, ?);";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setString(1, user.getFirstName());
            pstatement.setString(2, user.getLastName());
            pstatement.executeUpdate();
            
            /* get the user_id that was just created */
            sql = "SELECT last_insert_id() as user_id;";
            pstatement = connection.prepareStatement(sql);
            ResultSet rs = pstatement.executeQuery();
            if(rs.first()) {
                user.setUserId(rs.getInt("user_id"));
            }
            /**/
            
            pstatement.close();
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
            String sql = "SELECT * FROM user WHERE user_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, user.getUserId());
            ResultSet rs = pstatement.executeQuery();
            if(rs.first()) {
                user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"));
                user.setLogin(new LoginSvcPStatementImpl().retrieve(new Login(user.getUserId())));
                user.setTrips(new TripSvcPStatementImpl().retrieveByUserId(new Trip(-1, user.getUserId())));
            }
            else {
                user = null;
            }
            pstatement.close();
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
            String sql = "UPDATE user SET first_name=?, last_name=? WHERE user_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setString(1, user.getFirstName());
            pstatement.setString(2, user.getLastName());
            pstatement.setInt(3, user.getUserId());
            pstatement.executeUpdate();
            pstatement.close();
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
            new TripSvcPStatementImpl().deleteByUserId(new Trip(-1, user.getUserId()));
            new LoginSvcPStatementImpl().delete(new Login(user.getUserId()));
            String sql = "DELETE FROM user WHERE user_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, user.getUserId());
            pstatement.executeUpdate();
            pstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }

        return user;
    }
}
