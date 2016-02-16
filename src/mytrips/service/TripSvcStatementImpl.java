/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import java.sql.*;
import java.util.ArrayList;
import mytrips.domain.Trip;

/**
 *
 * @author Darren
 */
public class TripSvcStatementImpl extends ServiceAbs implements ITripSvc {
    
    @Override
    public Trip create(Trip trip) throws Exception {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO trip (trip_name, start_date, end_date, user_id) VALUES ('"+trip.getTripName()+"', STR_TO_DATE('"+trip.getStartDate()+"','%m-%d-%Y'), STR_TO_DATE('"+trip.getEndDate()+"','%m-%d-%Y'), "+trip.getUserId()+");";
            statement.executeUpdate(sql);
        
            /* get the trip_id that was just created */
            sql = "SELECT last_insert_id() as trip_id";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.first()) {
                trip.setTripId(rs.getInt("trip_id"));
            }
            /**/
                
            statement.close();
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
            Statement statement = connection.createStatement();
            String sql = "SELECT trip_id, trip_name, date_format(start_date, '%c-%e-%Y') as start_date, date_format(end_date, '%c-%e-%Y') as end_date, user_id FROM trip WHERE trip_id="+trip.getTripId()+";";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.first()) {
                trip = new Trip(rs.getInt("trip_id"), rs.getString("trip_name"), rs.getString("start_date"), rs.getString("end_date"), rs.getInt("user_id"));
            }
            else {
                trip = null;
            }
            statement.close();
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
            Statement statement = connection.createStatement();
            String sql = "SELECT trip_id, trip_name, date_format(start_date, '%c-%e-%Y') as start_date, date_format(end_date, '%c-%e-%Y') as end_date, user_id FROM trip WHERE user_id="+trip.getUserId()+";";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                trips.add(new Trip(rs.getInt("trip_id"), rs.getString("trip_name"), rs.getString("start_date"), rs.getString("end_date"), rs.getInt("user_id")));
            }
            statement.close();
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
            Statement statement = connection.createStatement();
            String sql = "UPDATE trip SET trip_name='"+trip.getTripName()+"', start_date=STR_TO_DATE('"+trip.getStartDate()+"','%m-%d-%Y'), end_date=STR_TO_DATE('"+trip.getEndDate()+"','%m-%d-%Y') WHERE trip_id="+trip.getTripId()+";";
            statement.executeUpdate(sql);
            statement.close();
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
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM trip where trip_id="+trip.getTripId()+";";
            statement.executeUpdate(sql);
            statement.close();
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
            Statement statement = connection.createStatement();
            trips = retrieveByUserId(trip);
            String sql = "DELETE FROM trip where user_id="+trip.getUserId()+";";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return trips;
    }
}
