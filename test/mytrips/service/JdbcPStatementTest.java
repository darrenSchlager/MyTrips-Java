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
public class JdbcPStatementTest {
    
    @Test
    public void testStatementServices() throws Exception {
        IUserSvc userImpl = new UserSvcPStatementImpl();
        ILoginSvc loginImpl = new LoginSvcPStatementImpl();
        /*todo*/TripSvcStatementImpl tripImpl = new TripSvcStatementImpl();
        /*todo*/LocationSvcStatementImpl locationImpl = new LocationSvcStatementImpl();
        /*todo*/ActivitySvcStatementImpl activityImpl = new ActivitySvcStatementImpl();
        
        //create User
        User user = new User("John", "Doe");
        userImpl.create(user);
        assertNotNull(user);
        
        //retrieve User
        user = userImpl.retrieve(new User(user.getUserId()));
        assertNotNull(user);
        assertNull(user.getLogin());
        System.out.println(user.toString());
        
        //retrieve User that does not exist
        User user2 = new User(0);
        user2 =userImpl.retrieve(user2);
        assertNull(user2);
        
        //update User
        user.setFirstName("Jane");
        userImpl.update(user);
        user = userImpl.retrieve(user);
        assertNotNull(user);
        System.out.println(user.toString()+"\n");
        
        //create Login
        Login login = new Login(user.getUserId(), "jd", "pwrd");
        loginImpl.create(login);
        assertNotNull(login);
        
        //retrieve Login
        login = loginImpl.retrieve(new Login("jd", "pwrd"));
        assertNotNull(login);
        
        //retrieve Login
        login = loginImpl.retrieve(new Login(login.getUserId()));
        assertNotNull(login);
        System.out.println(login.toString());
        
        //retrieve Login that does not exist
        Login login2 = new Login("", "");
        login2 = loginImpl.retrieve(login2);
        assertNull(login2);
        
        //update Login
        login.setUsername("jdoe");
        login.setPassword("1234");
        loginImpl.update(login);
        login = loginImpl.retrieve(login);
        assertNotNull(login);
        System.out.println(login.toString());
        
        //retrieve User
        user = userImpl.retrieve(new User(user.getUserId()));
        assertNotNull(user);
        assertNotNull(user.getLogin());
        System.out.println("\n\\\\\\");
        System.out.println(user.toString());
        System.out.println("///\n");
        
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
            System.out.println(t.toString());
        }
        
        //update Trip
        trip2.setTripName("Alaska");
        trip2.setStartDate("8-10-2017");
        trip2.setEndDate("8-24-2017");
        tripImpl.update(trip2);
        trip2 = tripImpl.retrieveByTripId(trip2);
        assertNotNull(trip2);
        System.out.println(trip2.toString());
        
        //retrieve User
        user = userImpl.retrieve(new User(user.getUserId()));
        assertNotNull(user);
        assertNotNull(user.getLogin());
        assertFalse(user.getTrips().isEmpty());
        System.out.println("\n\\\\\\");
        System.out.println(user.toString());
        System.out.println("///\n");
        
        //create Location
        Location location = new Location("7-10-2016", "7-17-2016", trip.getTripId(), "Wailea", "Hawaii");
        location = locationImpl.create(location);
        assertNotNull(location);        
        
        //create Location
        Location location2 = new Location("7-18-2016", "7-20-2016", trip.getTripId(), "Papeete", "French Polynesia");
        location2 = locationImpl.create(location2);
        assertNotNull(location2);   
        
        //create Location
        Location location3 = new Location("8-5-2017", "8-19-2017", trip2.getTripId(), "Junea", "Alask");
        location3 = locationImpl.create(location3);
        assertNotNull(location3); 
        
        //retrieve Location by trip_location_id
        location = locationImpl.retrieveByTripLocationId(new Location(location.getTripLocationId(), -1));
        assertNotNull(location);
        
        //retrieve Location by trip_id
        ArrayList<Location> locations = locationImpl.retrieveByTripId(new Location(-1, trip.getTripId()));
        assertFalse(locations.isEmpty());
        for(Location l : locations) {
            System.out.println(l.toString());
        }
        
        //retrieve Location by trip_id
        locations = locationImpl.retrieveByTripId(new Location(-1, trip2.getTripId()));
        assertFalse(locations.isEmpty());
        for(Location l : locations) {
            System.out.println(l.toString());
        }
        
        //update Location
        location3.setArrive("8-10-2017");
        location3.setDepart("8-24-2017");
        location3.setCity("Juneau");
        location3.setStateCountry("Alaska");
        locationImpl.update(location3);
        location3 = locationImpl.retrieveByTripLocationId(new Location(location3.getTripLocationId(), -1));
        assertNotNull(location3);
        System.out.println(location3.toString());
        
        //retrieve User
        user = userImpl.retrieve(new User(user.getUserId()));
        assertNotNull(user);
        assertNotNull(user.getLogin());
        assertFalse(user.getTrips().isEmpty());
        System.out.println("\n\\\\\\");
        System.out.println(user.toString());
        System.out.println("///\n");
        
        //create Activity
        Activity activity = new Activity("Volcano Hike", "7-12-2016", "10:10 AM", "Hike the Haleakala crater", location.getTripLocationId());
        activity = activityImpl.create(activity);
        assertNotNull(activity);
        
        //create Activity
        Activity activity2 = new Activity("Jeep Rental", "7-14-2016", "2:35 PM", "On Lanai", location.getTripLocationId());
        activity2 = activityImpl.create(activity2);
        assertNotNull(activity2);
        
        //create Activity
        Activity activity3 = new Activity("Helicopter Tour", "7-20-2016", "11:00 AM", "Heli tour of Bora Bora", location2.getTripLocationId());
        activity3 = activityImpl.create(activity3);
        assertNotNull(activity3);
        
        //create Activity
        Activity activity4 = new Activity("Tour", "8-12-2017", "12:30 PM", "Alaska tour", location3.getTripLocationId());
        activity4 = activityImpl.create(activity4);
        assertNotNull(activity4);
        
        //retrieve Activity by activity_id
        activity = activityImpl.retrieveByActivityId(new Activity(activity.getActivityId(), -1));
        assertNotNull(activity);
        
        //retrieve Activity by trip_location
        ArrayList<Activity> activities = activityImpl.retrieveByTripLocationId(new Activity(-1, location.getTripLocationId()));
        assertFalse(activities.isEmpty());
        for(Activity a : activities) {
            System.out.println(a.toString());
        }
        
        //retrieve Activity by trip_location
        activities = activityImpl.retrieveByTripLocationId(new Activity(-1, location2.getTripLocationId()));
        assertFalse(activities.isEmpty());
        for(Activity a : activities) {
            System.out.println(a.toString());
        }  
        
        //retrieve Activity by trip_location
        activities = activityImpl.retrieveByTripLocationId(new Activity(-1, location3.getTripLocationId()));
        assertFalse(activities.isEmpty());
        for(Activity a : activities) {
            System.out.println(a.toString());
        } 
        
        //update Activity
        activity4.setActivityName("Boat Tour");
        activity4.setDate("8-14-2017");
        activity4.setTime("12:15 PM");
        activity4.setDescription("Alaska Coast Tour");
        activityImpl.update(activity4);
        activity4 = activityImpl.retrieveByActivityId(new Activity(activity4.getActivityId(), -1));
        assertNotNull(activity4);
        System.out.println(activity4.toString());
        
        //retrieve User
        user = userImpl.retrieve(new User(user.getUserId()));
        assertNotNull(user);
        assertNotNull(user.getLogin());
        assertFalse(user.getTrips().isEmpty());
        System.out.println("\n\\\\\\");
        System.out.println(user.toString());
        System.out.println("///\n");
        
        //delete User
        userImpl.delete(user);
        user = userImpl.retrieve(user);
        assertNull(user);
        
        /* unnecessary, userImpl.delete(user) calls these*/
//        //delete Activity
//        activityImpl.deleteByActivityId(activity);
//        activity = activityImpl.retrieveByActivityId(activity);
//        assertNull(activity);
//        
//        //delete Activity
//        activityImpl.deleteByActivityId(activity2);
//        activity2 = activityImpl.retrieveByActivityId(activity2);
//        assertNull(activity2);
//        
//        //delete Activity
//        activityImpl.deleteByActivityId(activity3);
//        activity3 = activityImpl.retrieveByActivityId(activity3);
//        assertNull(activity3);
//        
//        //delete Activity
//        activityImpl.deleteByActivityId(activity4);
//        activity4 = activityImpl.retrieveByActivityId(activity4);
//        assertNull(activity4);
//        
//        //delete Location
//        locationImpl.deleteByTripLocationId(location);
//        location = locationImpl.retrieveByTripLocationId(location);
//        assertNull(location);
//        
//        //delete Location
//        locationImpl.deleteByTripLocationId(location2);
//        location2 = locationImpl.retrieveByTripLocationId(location2);
//        assertNull(location2);
//        
//        //delete Location
//        locationImpl.deleteByTripLocationId(location3);
//        location3 = locationImpl.retrieveByTripLocationId(location3);
//        assertNull(location3);
//        
//        //delete Trip
//        tripImpl.deleteByTripId(trip);
//        trip = tripImpl.retrieveByTripId(trip);
//        assertNull(trip);
//        
//        //delete Trip
//        tripImpl.deleteByTripId(trip2);
//        trip2 = tripImpl.retrieveByTripId(trip2);
//        assertNull(trip2);
//        
//        //delete Login
//        loginImpl.delete(login);
//        login = loginImpl.retrieve(login);
//        assertNull(login);
        /**/
        
    }
    
}
