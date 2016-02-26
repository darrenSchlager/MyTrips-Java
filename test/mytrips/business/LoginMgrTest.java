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
        System.out.println(user.getUserId()+" "+user.getFirstName()+" "+user.getLastName());
        login = user.getLogin();
        System.out.println(login.getUserId()+" "+login.getUsername()+" "+login.getPassword());
        ArrayList<Trip> trips = user.getTrips();
        for(Trip t : trips) {
            System.out.println(t.getTripId()+" "+t.getTripName()+" "+t.getStartDate()+" "+t.getEndDate()+" "+t.getUserId());
            ArrayList<Location> locations = t.getLocations();
            for(Location l : locations) {
                System.out.println(l.getTripLocationId()+" "+l.getCity()+" "+l.getStateCountry()+" "+l.getArrive()+" "+l.getTripId()+" "+l.getLocationId());
                ArrayList<Activity> activities = l.getActivities();
                for(Activity a : activities) {
                    System.out.println(a.getActivityId()+" "+a.getActivityName()+" "+a.getDate()+" "+a.getTime()+" "+a.getDescription());
                }
            }
        }
    }
    
}
