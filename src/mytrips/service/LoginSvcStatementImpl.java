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
public class LoginSvcStatementImpl extends ServiceAbs implements ILoginSvc {
    
    @Override
    public Login create(Login login) throws Exception {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO login (user_id, username, password) VALUES ('"+login.getUserId()+"', '"+login.getUsername()+"', '"+login.getPassword()+"');";
            statement.executeUpdate(sql);
            statement.close();
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
            Statement statement = connection.createStatement();
            String sql;
            if(login.getUserId()>0) {
                sql = "SELECT * FROM login WHERE user_id='"+login.getUserId()+"';";
            }
            else {
                sql = "SELECT * FROM login WHERE username='"+login.getUsername()+"' AND password='"+login.getPassword()+"';";
            }
            ResultSet rs = statement.executeQuery(sql);
            if(rs.first()) {
                login = new Login(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
            }
            else {
                login = null;
            }
            statement.close();
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
            Statement statement = connection.createStatement();
            String sql = "UPDATE login SET username='"+login.getUsername()+"', password='"+login.getPassword()+"' WHERE user_id="+login.getUserId()+";";
            statement.executeUpdate(sql);
            statement.close();
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
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM login WHERE user_id="+login.getUserId()+";";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }

        return login;
    }
    
}
