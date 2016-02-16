/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

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
public class JdbcStatementTest {
    
    @Test
    public void testStatementServices() throws Exception {
        UserSvcStatementImpl userImpl = new UserSvcStatementImpl();
        LoginSvcStatementImpl loginImpl = new LoginSvcStatementImpl();
        TripSvcStatementImpl tripImpl = new TripSvcStatementImpl();
        
        //create User
        User user = new User("John", "Doe");
        userImpl.create(user);
        assertNotNull(user);
        
        //retrieve User
        user = userImpl.retrieve(new User(user.getUserId()));
        assertNotNull(user);
        assertNull(user.getLogin());
        System.out.print(user.getUserId()+" "+user.getFirstName()+" "+user.getLastName()+" -> ");
        
        //retrieve User that does not exist
        User user2 = new User(0);
        user2 =userImpl.retrieve(user2);
        assertNull(user2);
        
        //update User
        user.setFirstName("Jane");
        userImpl.update(user);
        user = userImpl.retrieve(user);
        System.out.println(user.getUserId()+" "+user.getFirstName()+" "+user.getLastName());
        
        //create Login
        Login login = new Login(user.getUserId(), "jd", "pwrd");
        loginImpl.create(login);
        assertNotNull(login);
        
        //retrieve Login
        login = loginImpl.retrieve(new Login("jd", "pwrd"));
        assertNotNull(login);
        System.out.print(login.getUserId()+" "+login.getUsername()+" "+login.getPassword()+" = ");
        
        //retrieve Login
        login = loginImpl.retrieve(new Login(login.getUserId()));
        assertNotNull(login);
        System.out.print(login.getUserId()+" "+login.getUsername()+" "+login.getPassword()+" -> ");
        
        //retrieve Login that does not exist
        Login login2 = new Login("", "");
        login2 = loginImpl.retrieve(login2);
        assertNull(login2);
        
        //update Login
        login.setUsername("jdoe");
        login.setPassword("1234");
        loginImpl.update(login);
        login = loginImpl.retrieve(login);
        System.out.println(login.getUserId()+" "+login.getUsername()+" "+login.getPassword());
        
        //retrieve User
        user = userImpl.retrieve(new User(user.getUserId()));
        assertNotNull(user);
        assertNotNull(user.getLogin());
        System.out.println("\n:: "+user.getUserId()+" "+user.getFirstName()+" "+user.getLastName());
        System.out.println(":: "+user.getLogin().getUserId()+" "+user.getLogin().getUsername()+" "+user.getLogin().getPassword()+"\n");
        
        //create Trip
        Trip trip = new Trip("Pacific Islands", "7-10-2016", "7-24-2016", user.getUserId());
        trip = tripImpl.create(trip);
        assertNotNull(trip);
        
        //create Trip
        Trip trip2 = new Trip("Australia", "6-5-2016", "6-19-2016", user.getUserId());
        trip2 = tripImpl.create(trip2);
        assertNotNull(trip2);
        
        //retrieve Trip by trip_id
        trip = tripImpl.retrieveByTripId(new Trip(trip.getTripId(), -1));
        assertNotNull(trip);
        
        //retrieve Trips by user_id
        ArrayList<Trip> trips = tripImpl.retrieveByUserId(new Trip(-1, user.getUserId()));
        assertFalse(trips.isEmpty());
        for(Trip t : trips) {
            System.out.println(t.getTripId()+" "+t.getTripName()+" "+t.getStartDate()+" "+t.getEndDate()+" "+t.getUserId());
        }
        
        //update Trip
        trip2.setTripName("Alaska");
        trip2.setStartDate("8-10-2017");
        trip2.setEndDate("8-24-2017");
        tripImpl.update(trip2);
        trip2 = tripImpl.retrieveByTripId(trip2);
        System.out.println(trip2.getTripId()+" "+trip2.getTripName()+" "+trip2.getStartDate()+" "+trip2.getEndDate()+" "+trip2.getUserId());
        
        //retrieve User
        user = userImpl.retrieve(new User(user.getUserId()));
        assertNotNull(user);
        assertNotNull(user.getLogin());
        assertFalse(user.getTrips().isEmpty());
        System.out.println("\n:: "+user.getUserId()+" "+user.getFirstName()+" "+user.getLastName());
        System.out.println(":: "+user.getLogin().getUserId()+" "+user.getLogin().getUsername()+" "+user.getLogin().getPassword());
        for(Trip t : user.getTrips()) {
            System.out.println(":: "+t.getTripId()+" "+t.getTripName()+" "+t.getStartDate()+" "+t.getEndDate()+" "+t.getUserId());
        }
        System.out.println();
        
        //delete Trip
        tripImpl.delete(trip);
        trip = tripImpl.retrieveByTripId(trip);
        assertNull(trip);
        
        //delete Trip
        tripImpl.delete(trip2);
        trip2 = tripImpl.retrieveByTripId(trip2);
        assertNull(trip2);
        
        //delete Login
        loginImpl.delete(login);
        login = loginImpl.retrieve(login);
        assertNull(login);
        
        //delete User
        userImpl.delete(user);
        user = userImpl.retrieve(user);
        assertNull(user);
        
    }
    
}
