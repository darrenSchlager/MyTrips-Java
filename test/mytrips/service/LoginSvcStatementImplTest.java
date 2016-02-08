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
    public void testCreate() throws Exception {
        LoginSvcStatementImpl impl = new LoginSvcStatementImpl();
        Login login = new Login("ds", "pwrd");
        impl.create(login);
        assertNotNull(login);
    }
    
}
