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
public class LoginSvcServerTest {
    
    //the server must be running
    
    @Test
    public void testServer() {
        LoginSvcServer loginSvc = new LoginSvcServer();
        try {
            Login login = new Login("ds", "bball");
            assertTrue(loginSvc.authenticate(login));
            login = new Login("jdoe", "1234");
            assertFalse(loginSvc.authenticate(login));
        } catch (Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
        }
    }
    
}
