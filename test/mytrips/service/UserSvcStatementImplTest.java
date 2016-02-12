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
        System.out.println("Created\nuser_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
        
        /* test retrieve */
        user = new User(user.getUserId());
        user = impl.retrieve(user);
        assertNotNull(user);
        System.out.println("Retrieved\nuser_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
        
        /* test update */
        user = new User(user.getUserId(), "Jane", "Doe");
        user = impl.update(user);
        assertNotNull(user);
        System.out.println("Updated to\nuser_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
        
        /* confirm update */
        user = new User(user.getUserId());
        user = impl.retrieve(user);
        assertNotNull(user);
        System.out.println("Retrieved\nuser_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
        
        /* test delete */
        user = impl.delete(user);
        assertNotNull(user);
        System.out.println("Deleted\nuser_id: "+user.getUserId()+"\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /**/
        
        /* confirm delete */
        user = new User(user.getUserId());
        user = impl.retrieve(user);
        assertNull(user);
        System.out.println("Retrieved\n"+user+"\n");
        /**/
        
        
        
        /* create user in preparation for LoginSvcStatementImplTest*/
        user = new User("John", "Doe");
        impl.create(user);
        assertNotNull(user);
        System.out.println("In preparation for LoginSvcStatementImplTest, Created\nuser_id: "+user.getUserId()+" (hard code this user_id in LoginSvcStatementImplTest before running that test)\nfirst_name: "+user.getFirstName()+"\nlast_name: "+user.getLastName()+"\n");
        /* delete user */
//        User user = impl.delete(new User(4));
//        assertNotNull(user);
//        System.out.println("Deleted\nuser_id: "+user.getUserId()+"\n");
        /**/
        
    }
    
}
