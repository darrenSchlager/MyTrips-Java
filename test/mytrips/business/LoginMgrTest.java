/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.business;

import java.util.ArrayList;
import mytrips.domain.*;
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
public class LoginMgrTest {
    
    @Test
    public void testAuthenticate() throws Exception {
        LoginMgr loginMgr = new LoginMgr();
        Login login = new Login("ds", "bball");
        User user = loginMgr.authenticate(login);
        assertNotNull(user);
        login = user.getLogin();
        assertNotNull(login);
        System.out.println(user.toString());
    }
    
}
