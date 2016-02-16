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
        
        //create User
        User user = new User("John", "Doe");
        userImpl.create(user);
        assertNotNull(user);
        
        //retrieve User
        user = userImpl.retrieve(new User(user.getUserId()));
        assertNotNull(user);
        System.out.print(user.getUserId()+" "+user.getFirstName()+" "+user.getLastName()+" -> ");
        
        //retrieve User that does not exist
        User user2 = new User(-1);
        user2 =userImpl.retrieve(user2);
        assertNull(user2);
        
        //update User
        user.setFirstName("Jane");
        userImpl.update(user);
        user = userImpl.retrieve(user);
        System.out.println(user.getUserId()+" "+user.getFirstName()+" "+user.getLastName());
        
        
        
        //delete User
        userImpl.delete(user);
        user = userImpl.retrieve(user);
        assertNull(user);
        
    }
    
}
