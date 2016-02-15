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
        testUser();
        testLogin();

    }
    
    private void testUser() throws Exception {
        
        UserSvcStatementImpl userImpl = new UserSvcStatementImpl();
        
        /* test create*/
        User user = new User("John", "Doe");
        userImpl.create(user);
        assertNotNull(user);
        System.out.println("Created\nuser_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
        
        /* test retrieve */
        user = new User(user.getUserId());
        user = userImpl.retrieve(user);
        assertNotNull(user);
        System.out.println("Retrieved\nuser_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
        
        /* test update */
        user = new User(user.getUserId(), "Jane", "Doe");
        user = userImpl.update(user);
        assertNotNull(user);
        System.out.println("Updated to\nuser_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
        
        /* confirm update */
        user = new User(user.getUserId());
        user = userImpl.retrieve(user);
        assertNotNull(user);
        System.out.println("Retrieved\nuser_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
        
        /* test delete */
        user = userImpl.delete(user);
        assertNotNull(user);
        System.out.println("Deleted\nuser_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
        
        /* confirm delete */
        user = new User(user.getUserId());
        user = userImpl.retrieve(user);
        assertNull(user);
        System.out.println("Retrieved\n"+user+"\n");
        /**/
    }
    
    private void testLogin() throws Exception {
        LoginSvcStatementImpl loginImpl = new LoginSvcStatementImpl();
        UserSvcStatementImpl userImpl = new UserSvcStatementImpl();
        
        /* create User for test*/
        User user = new User("John", "Doe");
        userImpl.create(user);
        assertNotNull(user);
        System.out.println("Created\nuser_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
        
        /* test create */
        Login login = new Login(user.getUserId(), "jdoe", "pwrd");
        loginImpl.create(login);
        assertNotNull(login);
        System.out.println("Created\nuser_id: "+login.getUserId()+"\nusername: "+login.getUsername()+"\npassword: "+login.getPassword()+"\n");
        /**/
        
        /* test retrieve */
        login = new Login("jdoe", "pwrd");
        login = loginImpl.retrieve(login);
        assertNotNull(login);
        System.out.println("Retrieved\nuser_id: "+login.getUserId()+"\nusername: "+login.getUsername()+"\npassword: "+login.getPassword()+"\n");
        /**/
        
        /* test update */
        login = new Login(login.getUserId(), "jd", "1234");
        login = loginImpl.update(login);
        assertNotNull(login);
        System.out.println("Updated to\nuser_id: "+login.getUserId()+"\nusername: "+login.getUsername()+"\npassword: "+login.getPassword()+"\n");
        /**/
        
        /* confirm update */
        login = new Login("jd", "1234");
        login = loginImpl.retrieve(login);
        assertNotNull(login);
        System.out.println("Retrieved\nuser_id: "+login.getUserId()+"\nusername: "+login.getUsername()+"\npassword: "+login.getPassword()+"\n");
        /**/
        
        /* test delete */
        login = loginImpl.delete(login);
        assertNotNull(login);
        System.out.println("Deleted\nuser_id: "+login.getUserId()+"\nusername: "+login.getUsername()+"\npassword: "+login.getPassword()+"\n");
        /**/
        
        /* confirm delete */
        login = new Login("jdoe", "pwrd");
        login = loginImpl.retrieve(login);
        assertNull(login);
        System.out.println("Retrieved\n"+login+"\n");
        /**/
        
        /* delete User, test over*/
        user = userImpl.delete(user);
        assertNotNull(user);
        System.out.println("Deleted\nuser_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
    }
    
}
