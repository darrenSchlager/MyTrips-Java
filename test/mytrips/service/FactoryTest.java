/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

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
public class FactoryTest {
    
    @Test
    public void testFactory() throws Exception {
        Factory factory = new Factory();
        
        IActivitySvc activitySvc = (IActivitySvc)factory.getService(IActivitySvc.NAME);
        assertNotNull(activitySvc);
        
        ILocationSvc locationSvc = (ILocationSvc)factory.getService(ILocationSvc.NAME);
        assertNotNull(locationSvc);
        
        ILoginSvc loginSvc = (ILoginSvc)factory.getService(ILoginSvc.NAME);
        assertNotNull(loginSvc);
        
        ITripSvc tripSvc = (ITripSvc)factory.getService(ITripSvc.NAME);
        assertNotNull(tripSvc);
        
        IUserSvc userSvc = (IUserSvc)factory.getService(IUserSvc.NAME);
        assertNotNull(userSvc);
    }
    
}
