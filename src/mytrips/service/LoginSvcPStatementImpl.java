/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;
import mytrips.domain.Login;
import java.sql.*; //JDBC API (interface); the implementation is in the driver (Project Properties -> Libraries -> Add Library -> MySql JDBC Driver)

/**
 *
 * @author Darren
 */
public class LoginSvcPStatementImpl extends ServiceAbs implements ILoginSvc {
    
    @Override
    public Login create(Login login) throws Exception {
        try {
            Connection connection = getConnection();
            String sql = "INSERT INTO login (user_id, username, password) VALUES (?, ?, ?);";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, login.getUserId());
            pstatement.setString(2, login.getUsername());
            pstatement.setString(3, login.getPassword());
            pstatement.executeUpdate();
            pstatement.close();
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
            String sql;
            PreparedStatement pstatement;
            if(login.getUserId()>0) {
                sql = "SELECT * FROM login WHERE user_id=?;";
                pstatement = connection.prepareStatement(sql);
                pstatement.setInt(1, login.getUserId());
            }
            else {
                sql = "SELECT * FROM login WHERE username=? AND password=?;";
                pstatement = connection.prepareStatement(sql);
                pstatement.setString(1, login.getUsername());
                pstatement.setString(2, login.getPassword());
            }
            ResultSet rs = pstatement.executeQuery();
            if(rs.first()) {
                login = new Login(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
            }
            else {
                login = null;
            }
            pstatement.close();
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
            String sql = "UPDATE login SET username=?, password=? WHERE user_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setString(1, login.getUsername());
            pstatement.setString(2, login.getPassword());
            pstatement.setInt(3, login.getUserId());
            pstatement.executeUpdate();
            pstatement.close();
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
            String sql = "DELETE FROM login WHERE user_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, login.getUserId());
            pstatement.executeUpdate();
            pstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }

        return login;
    }
    
}
