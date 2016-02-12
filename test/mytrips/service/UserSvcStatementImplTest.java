/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

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
public class UserSvcStatementImplTest {
    
    @Test
    public void testCRUD() throws Exception {
        
        UserSvcStatementImpl impl = new UserSvcStatementImpl();
        
        /* test create */
        User user = new User("John", "Doe");
        impl.create(user);
        assertNotNull(user);
        System.out.println("user_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
        
        /* test retrieve */
        /**/
        
        /* test update */
        /**/
        
        /* test delete */
        /**/
    }
    
}
