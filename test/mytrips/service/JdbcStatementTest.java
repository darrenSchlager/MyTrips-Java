/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import mytrips.domain.Login;
import mytrips.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Darren
 */
public class JdbcStatementTest {
    
    @Test
    public void testStatementServices() throws Exception {
        UserSvcStatementImpl userImpl = new UserSvcStatementImpl();
        LoginSvcStatementImpl loginImpl = new LoginSvcStatementImpl();
        
        //create User
        User user = new User("John", "Doe");
        userImpl.create(user);
        assertNotNull(user);
        
        //retrieve User
        user = userImpl.retrieve(new User(user.getUserId()));
        assertNotNull(user);
        assertNull(user.getLogin());
        System.out.print(user.getUserId()+" "+user.getFirstName()+" "+user.getLastName()+" -> ");
        
        //retrieve User that does not exist
        User user2 = new User(0);
        user2 =userImpl.retrieve(user2);
        assertNull(user2);
        
        //update User
        user.setFirstName("Jane");
        userImpl.update(user);
        user = userImpl.retrieve(user);
        System.out.println(user.getUserId()+" "+user.getFirstName()+" "+user.getLastName());
        
        //create Login
        Login login = new Login(user.getUserId(), "jd", "pwrd");
        loginImpl.create(login);
        assertNotNull(login);
        
        //retrieve Login
        login = loginImpl.retrieve(new Login("jd", "pwrd"));
        assertNotNull(login);
        System.out.print(login.getUserId()+" "+login.getUsername()+" "+login.getPassword()+" = ");
        
        //retrieve Login
        login = loginImpl.retrieve(new Login(login.getUserId()));
        assertNotNull(login);
        System.out.print(login.getUserId()+" "+login.getUsername()+" "+login.getPassword()+" -> ");
        
        //retrieve Login that does not exist
        Login login2 = new Login("", "");
        login2 = loginImpl.retrieve(login2);
        assertNull(login2);
        
        //update Login
        login.setUsername("jdoe");
        loginImpl.update(login);
        login = loginImpl.retrieve(login);
        System.out.println(login.getUserId()+" "+login.getUsername()+" "+login.getPassword());
        
        //retrieve User
        user = userImpl.retrieve(new User(user.getUserId()));
        assertNotNull(user);
        assertNotNull(user.getLogin());
        System.out.println(user.getUserId()+" "+user.getFirstName()+" "+user.getLastName()+" "+user.getLogin().getUsername()+" "+user.getLogin().getPassword());
        
        //delete Login
        loginImpl.delete(login);
        login = loginImpl.retrieve(login);
        assertNull(login);
        
        //delete User
        userImpl.delete(user);
        user = userImpl.retrieve(user);
        assertNull(user);
        
    }
    
}
