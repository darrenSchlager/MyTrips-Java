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
public class LoginSvcStatementImpl implements ILoginSvc {
    
    private final String CONN_STRING = "jdbc:mysql://localhost/MyTrips?user=root&password=";
    
    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(CONN_STRING);
    }
    
    @Override
    public Login create(Login login) throws Exception {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO login (user_id, username, password) VALUES ('"+login.getUserId()+"', '"+login.getUsername()+"', '"+login.getPassword()+"');";
            statement.executeUpdate(sql);
            
            /* get the user_id that was just created 
            sql = "SELECT last_insert_id() as user_id;";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.first()) {
                login.setUserId(rs.getInt("user_id"));
            }
            */
            
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
            String sql = "SELECT * FROM login WHERE username='"+login.getUsername()+"' AND password='"+login.getPassword()+"';";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.first()) {
                login = new Login(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
            }
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return login;
    }
    
}
