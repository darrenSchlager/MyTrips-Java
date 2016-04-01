/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import java.sql.*;
import java.util.ArrayList;
import mytrips.domain.*;

/**
 *
 * @author Darren
 */
public class TripSvcPStatementImpl extends ServiceAbs implements ITripSvc {
    
    @Override
    public Trip create(Trip trip) throws Exception {
        try {
            Connection connection = getConnection();
            String sql = "INSERT INTO trip (trip_name, start_date, end_date, user_id) VALUES (?, STR_TO_DATE(?,'%m-%d-%Y'), STR_TO_DATE(?,'%m-%d-%Y'), ?);";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setString(1, trip.getTripName());
            pstatement.setString(2, trip.getStartDate());
            pstatement.setString(3, trip.getEndDate());
            pstatement.setInt(4, trip.getUserId());
            pstatement.executeUpdate();
        
            /* get the trip_id that was just created */
            sql = "SELECT last_insert_id() as trip_id";
            pstatement = connection.prepareStatement(sql);
            ResultSet rs = pstatement.executeQuery();
            if(rs.first()) {
                trip.setTripId(rs.getInt("trip_id"));
            }
            /**/
                
            pstatement.close();
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
            String sql = "SELECT trip_id, trip_name, date_format(start_date, '%c-%e-%Y') as start_date, date_format(end_date, '%c-%e-%Y') as end_date, user_id FROM trip WHERE trip_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, trip.getTripId());
            ResultSet rs = pstatement.executeQuery();
            if(rs.first()) {
                trip = new Trip(rs.getInt("trip_id"), rs.getString("trip_name"), rs.getString("start_date"), rs.getString("end_date"), rs.getInt("user_id"));
                trip.setLocations(new LocationSvcPStatementImpl().retrieveByTripId(new Location(-1, trip.getTripId())));
            }
            else {
                trip = null;
            }
            pstatement.close();
            connection.close();
        } catch(Exception e)
        {
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
            String sql = "SELECT trip_id, trip_name, date_format(start_date, '%c-%e-%Y') as start_date, date_format(end_date, '%c-%e-%Y') as end_date, user_id FROM trip WHERE user_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, trip.getUserId());
            ResultSet rs = pstatement.executeQuery();
            while(rs.next()) {
                Trip t = new Trip(rs.getInt("trip_id"), rs.getString("trip_name"), rs.getString("start_date"), rs.getString("end_date"), rs.getInt("user_id"));
                t.setLocations(new LocationSvcPStatementImpl().retrieveByTripId(new Location(-1, t.getTripId())));
                trips.add(t);
            }
            pstatement.close();
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
            String sql = "UPDATE trip SET trip_name=?, start_date=STR_TO_DATE(?,'%m-%d-%Y'), end_date=STR_TO_DATE(?,'%m-%d-%Y') WHERE trip_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setString(1, trip.getTripName());
            pstatement.setString(2, trip.getStartDate());
            pstatement.setString(3, trip.getEndDate());
            pstatement.setInt(4, trip.getTripId());
            pstatement.executeUpdate();
            pstatement.close();
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
            new LocationSvcPStatementImpl().deleteByTripId(new Location(-1, trip.getTripId()));
            String sql = "DELETE FROM trip where trip_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, trip.getTripId());
            pstatement.executeUpdate();
            pstatement.close();
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
                new LocationSvcPStatementImpl().deleteByTripId(new Location(-1, t.getTripId()));
            }
            String sql = "DELETE FROM trip where user_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, trip.getUserId());
            pstatement.executeUpdate();
            pstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return trips;
    }
}
