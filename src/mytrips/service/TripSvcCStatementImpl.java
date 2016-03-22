/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import java.sql.*;
import java.util.ArrayList;
import mytrips.domain.Location;
import mytrips.domain.Trip;

/**
 *
 * @author Darren
 */
public class TripSvcCStatementImpl extends ServiceAbs implements ITripSvc {
    
    @Override
    public Trip create(Trip trip) throws Exception {
        try {
            Connection connection = getConnection();
            CallableStatement cstatement = connection.prepareCall("{CALL create_trip(?,?,?,?)}");
            cstatement.setString(1, trip.getTripName());
            cstatement.setString(2, trip.getStartDate());
            cstatement.setString(3, trip.getEndDate());
            cstatement.setInt(4, trip.getUserId());
            cstatement.executeUpdate();
            
            /* get the trip_id that was just created */
            cstatement = connection.prepareCall("{CALL last_insert_id()}");
            ResultSet rs = cstatement.executeQuery();
            if(rs.first()) {
                trip.setTripId(rs.getInt("last_insert_id"));
            }
            /**/
            
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return trip;
    }
    
    @Override
    public Trip retrieveByTripId(Trip trip) throws Exception {
        try {
            Connection connection = getConnection();
            CallableStatement cstatement = connection.prepareCall("{CALL retrieve_trip_by_tripid(?)}");
            cstatement.setInt(1, trip.getTripId());
            ResultSet rs = cstatement.executeQuery();
            if(rs.first()) {
                trip = new Trip(rs.getInt("trip_id"), rs.getString("trip_name"), rs.getString("start_date"), rs.getString("end_date"), rs.getInt("user_id"));
                trip.setLocations(new LocationSvcStatementImpl().retrieveByTripId(new Location(-1, trip.getTripId())));
            }
            else {
                trip = null;
            }
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return trip;
    }
    
    @Override
    public ArrayList<Trip> retrieveByUserId(Trip trip) throws Exception {
        ArrayList<Trip> trips = new ArrayList();
        try {
            Connection connection = getConnection();
            CallableStatement cstatement = connection.prepareCall("{CALL retrieve_trip_by_userid(?)}");
            cstatement.setInt(1, trip.getUserId());
            ResultSet rs = cstatement.executeQuery();
            while(rs.next()) {
                Trip t = new Trip(rs.getInt("trip_id"), rs.getString("trip_name"), rs.getString("start_date"), rs.getString("end_date"), rs.getInt("user_id"));
                t.setLocations(new LocationSvcStatementImpl().retrieveByTripId(new Location(-1, t.getTripId())));
                trips.add(t);
            }
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return trips;
    }
    
    @Override
    public Trip update(Trip trip) throws Exception {
        try {
            Connection connection = getConnection();
            CallableStatement cstatement = connection.prepareCall("{CALL update_trip(?,?,?,?)}");
            cstatement.setString(1, trip.getTripName());
            cstatement.setString(2, trip.getStartDate());
            cstatement.setString(3, trip.getEndDate());
            cstatement.setInt(4, trip.getTripId());
            cstatement.executeUpdate();
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return trip;
    }
    
    @Override
    public Trip deleteByTripId(Trip trip) throws Exception {
        try {
            Connection connection = getConnection();
            CallableStatement cstatement = connection.prepareCall("{CALL delete_trip_by_tripid(?)}");
            cstatement.setInt(1, trip.getTripId());
            cstatement.executeUpdate();
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return trip;
    }
    
    @Override
    public ArrayList<Trip> deleteByUserId(Trip trip) throws Exception {
        ArrayList<Trip> trips = new ArrayList();
        try {
            Connection connection = getConnection();
            trips = retrieveByUserId(trip);
            for(Trip t : trips) {
                new LocationSvcStatementImpl().deleteByTripId(new Location(-1, t.getTripId()));
            }
            CallableStatement cstatement = connection.prepareCall("{CALL delete_trip_by_userid(?)}");
            cstatement.setInt(1, trip.getUserId());
            cstatement.executeUpdate();
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return trips;
    }
    
}
