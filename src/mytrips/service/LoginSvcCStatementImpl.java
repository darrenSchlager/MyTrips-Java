/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import java.sql.*;
import mytrips.domain.Login;

/**
 *
 * @author Darren
 */
public class LoginSvcCStatementImpl extends ServiceAbs implements ILoginSvc {
    
    @Override
    public Login create(Login login) throws Exception {
        try {
            Connection connection = getConnection();
            CallableStatement cstatement = connection.prepareCall("{CALL create_login(?,?,?)}");
            cstatement.setInt(1, login.getUserId());
            cstatement.setString(2, login.getUsername());
            cstatement.setString(3, login.getPassword());
            cstatement.executeUpdate();
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return login;
    }
    
    @Override
    public Login retrieve(Login login) throws Exception {
        try {
            Connection connection = getConnection();
            CallableStatement cstatement;
            if(login.getUserId()>0) {
                cstatement = connection.prepareCall("{CALL retrieve_login_by_userid(?)}");
                cstatement.setInt(1, login.getUserId());
            }
            else {
                cstatement = connection.prepareCall("{CALL retrieve_login_by_credentials(?,?)}");
                cstatement.setString(1, login.getUsername());
                cstatement.setString(2, login.getPassword());
            }
            ResultSet rs = cstatement.executeQuery();
            if(rs.first()) {
                login = new Login(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
            }
            else {
                login = null;
            }
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return login;
    }
    
    @Override
    public Login update(Login login) throws Exception {
        try {
            Connection connection = getConnection();
            CallableStatement cstatement = connection.prepareCall("{CALL update_login(?,?,?)}");
            cstatement.setString(1, login.getUsername());
            cstatement.setString(2, login.getPassword());
            cstatement.setInt(3, login.getUserId());
            cstatement.executeUpdate();
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return login;
    }
    
    @Override
    public Login delete(Login login) throws Exception {
        try {
            Connection connection = getConnection();
            CallableStatement cstatement = connection.prepareCall("{CALL delete_login(?)}");
            cstatement.setInt(1, login.getUserId());
            cstatement.executeUpdate();
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return login;
    }
    
        @Override
    public boolean containsUsername(Login login) throws Exception {
        boolean containsUsername = false;
        try {
            Connection connection = getConnection();
            CallableStatement cstatement = connection.prepareCall("{CALL retrieve_login_by_username(?)}");
            cstatement.setString(1, login.getUsername());
            ResultSet rs = cstatement.executeQuery();
            if(rs.first()) {
                containsUsername = true;
            }
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return containsUsername;
    }
    
}
