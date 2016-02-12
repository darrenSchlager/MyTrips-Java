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
        
        /* test create */
        Login login = new Login(3, "jdoe", "pwrd");
        impl.create(login);
        assertNotNull(login);
        System.out.println("user_id: "+login.getUserId()+"\nusername: "+login.getUsername()+"\npassword: "+login.getPassword()+"\n");
        /**/
        
        /* test retrieve */
        login = new Login("jdoe", "pwrd");
        login = impl.retrieve(login);
        assertNotNull(login);
        System.out.println("user_id: "+login.getUserId()+"\nusername: "+login.getUsername()+"\npassword: "+login.getPassword()+"\n");
        /**/
        
        /* test update */
        
        /**/
        
        /* test delete */
        
        /**/
    }
    
}
