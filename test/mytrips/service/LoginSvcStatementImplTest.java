/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import mytrips.domain.Login;
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
public class LoginSvcStatementImplTest {
    
    @Test
    public void testCRUD() throws Exception {
        
        LoginSvcStatementImpl impl = new LoginSvcStatementImpl();
        final int USER_ID = 4;
        
        /* test create */
        Login login = new Login(USER_ID, "jdoe", "pwrd");
        impl.create(login);
        assertNotNull(login);
        System.out.println("Created\nuser_id: "+login.getUserId()+"\nusername: "+login.getUsername()+"\npassword: "+login.getPassword()+"\n");
        /**/
        
        /* test retrieve */
        login = new Login("jdoe", "pwrd");
        login = impl.retrieve(login);
        assertNotNull(login);
        System.out.println("Retrieved\nuser_id: "+login.getUserId()+"\nusername: "+login.getUsername()+"\npassword: "+login.getPassword()+"\n");
        /**/
        
        /* test update */
        login = new Login(USER_ID, "jd", "1234");
        login = impl.update(login);
        assertNotNull(login);
        System.out.println("Updated to\nuser_id: "+login.getUserId()+"\nusername: "+login.getUsername()+"\npassword: "+login.getPassword()+"\n");
        /**/
        
        /* confirm update */
        login = new Login("jd", "1234");
        login = impl.retrieve(login);
        assertNotNull(login);
        System.out.println("Retrieved\nuser_id: "+login.getUserId()+"\nusername: "+login.getUsername()+"\npassword: "+login.getPassword()+"\n");
        /**/
        
        /* test delete */
        login = impl.delete(login);
        assertNotNull(login);
        System.out.println("Deleted\nuser_id: "+login.getUserId()+"\nusername: "+login.getUsername()+"\npassword: "+login.getPassword()+"\n");
        /**/
        
        /* confirm delete */
        login = new Login("jdoe", "pwrd");
        login = impl.retrieve(login);
        assertNull(login);
        System.out.println("Retrieved\n"+login+"\n");
        /**/
    }
    
}
