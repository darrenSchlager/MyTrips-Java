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
    
    public LoginSvcStatementImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class LoginSvcStatementImpl.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Login login = null;
        LoginSvcStatementImpl instance = new LoginSvcStatementImpl();
        Login expResult = null;
        Login result = instance.create(login);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
