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
public class LocationSvcPStatementImpl extends ServiceAbs implements ILocationSvc {
    
    @Override
    public Location create(Location location) throws Exception {
        try {
            if(new TripSvcStatementImpl().retrieveByTripId(new Trip(location.getTripId() ,-1)) != null) {
                Connection connection = getConnection();
                PreparedStatement pstatement;
                
                /* insert into the location table if it isnt there already */
                String sql = "SELECT * FROM location WHERE city=? and state_country=?;";
                pstatement = connection.prepareStatement(sql);
                pstatement.setString(1, location.getCity());
                pstatement.setString(2, location.getStateCountry());
                ResultSet rs = pstatement.executeQuery();
                if(rs.first()) {
                    location.setLocationId(rs.getInt("location_id"));
                }
                else {
                    sql = "INSERT INTO location (city, state_country) VALUES (?, ?);";
                    pstatement = connection.prepareStatement(sql);
                    pstatement.setString(1, location.getCity());
                    pstatement.setString(2, location.getStateCountry());
                    pstatement.executeUpdate();
                    
                    sql = "SELECT last_insert_id() as location_id";
                    pstatement = connection.prepareStatement(sql);
                    ResultSet rs2 = pstatement.executeQuery();
                    if(rs2.first()) {
                        location.setLocationId(rs2.getInt("location_id"));
                    }
                }
                /**/
                
                /* insert into the mapping table */
                sql = "INSERT INTO trip_location (arrive, depart, trip_id, location_id) VALUES (STR_TO_DATE(?,'%m-%d-%Y'), STR_TO_DATE(?,'%m-%d-%Y'), ?, ?);";
                pstatement = connection.prepareStatement(sql);
                pstatement.setString(1, location.getArrive());
                pstatement.setString(2, location.getDepart());
                pstatement.setInt(3, location.getTripId());
                pstatement.setInt(4, location.getLocationId());
                pstatement.executeUpdate();
                sql = "SELECT last_insert_id() as trip_location_id";
                pstatement = connection.prepareStatement(sql);
                rs = pstatement.executeQuery();
                if(rs.first()) {
                    location.setTripLocationId(rs.getInt("trip_location_id"));
                }
                /**/
                
                pstatement.close();
                connection.close();
            }
            else {
                location = null;
            }
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return location;
    }
    
    @Override
    public Location retrieveByTripLocationId(Location location) throws Exception {
        try {
            Connection connection = getConnection();
            PreparedStatement pstatement;
            
            /* select from the mapping table */
            String sql = "SELECT trip_location_id, date_format(arrive, '%c-%e-%Y') as arrive, date_format(depart, '%c-%e-%Y') as depart, trip_id, location_id FROM trip_location WHERE trip_location_id=?;";
            pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, location.getTripLocationId());
            ResultSet rs = pstatement.executeQuery();
            if(rs.first()) {
                location = new Location(rs.getInt("trip_location_id"), rs.getString("arrive"), rs.getString("depart"), rs.getInt("trip_id"), rs.getInt("location_id"));
                /* select from the Location table */
                
                sql = "SELECT * FROM location where location_id=?;";
                pstatement = connection.prepareStatement(sql);
                pstatement.setInt(1, location.getLocationId());
                ResultSet rs2 = pstatement.executeQuery();
                if(rs2.first()) {
                    location.setCity(rs2.getString("city"));
                    location.setStateCountry(rs2.getString("state_country"));
                    location.setActivities(new ActivitySvcStatementImpl().retrieveByTripLocationId(new Activity(-1, location.getTripLocationId())));
                }
                /**/
            }
            else {
                location = null;
            }
            /**/
            
            pstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return location;
    }
    
    @Override
    public ArrayList<Location> retrieveByTripId(Location location) throws Exception {
        ArrayList<Location> locations = new ArrayList();
        try {
            Connection connection = getConnection();
            PreparedStatement pstatement;
            
            /* select from the mapping table */
            String sql = "SELECT trip_location_id, date_format(arrive, '%c-%e-%Y') as arrive, date_format(depart, '%c-%e-%Y') as depart, trip_id, location_id FROM trip_location WHERE trip_id=?;";
            pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, location.getTripId());
            ResultSet rs = pstatement.executeQuery();
            while(rs.next()) {
                locations.add(new Location(rs.getInt("trip_location_id"), rs.getString("arrive"), rs.getString("depart"), rs.getInt("trip_id"), rs.getInt("location_id")));
            }
            for(Location l : locations) {
                /* select from the Location table */
                sql = "SELECT * FROM location where location_id=?;";
                pstatement = connection.prepareStatement(sql);
                pstatement.setInt(1, l.getLocationId());
                rs = pstatement.executeQuery();
                if(rs.first()) {
                    l.setCity(rs.getString("city"));
                    l.setStateCountry(rs.getString("state_country"));
                    l.setActivities(new ActivitySvcStatementImpl().retrieveByTripLocationId(new Activity(-1, l.getTripLocationId())));
                }
                /**/
            }
            /**/
            
            pstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return locations;
    }
    
    @Override
    public Location update(Location location) throws Exception {
        try {
            Connection connection = getConnection();
            PreparedStatement pstatement;
            int oldLocationId = location.getLocationId();
            
            /* insert into the location table if it isnt there already */
            String sql = "SELECT location_id FROM location WHERE city=? and state_country=?;";
            pstatement = connection.prepareStatement(sql);
            pstatement.setString(1, location.getCity());
            pstatement.setString(2, location.getStateCountry());
            ResultSet rs = pstatement.executeQuery();
            if(rs.first()) {
                location.setLocationId(rs.getInt("location_id"));
            }
            else {
                sql = "INSERT INTO location (city, state_country) VALUES (?, ?);";
                pstatement = connection.prepareStatement(sql);
                pstatement.setString(1, location.getCity());
                pstatement.setString(2, location.getStateCountry());
                pstatement.executeUpdate();
                sql = "SELECT last_insert_id() as location_id";
                pstatement = connection.prepareStatement(sql);
                ResultSet rs2 = pstatement.executeQuery();
                if(rs2.first()) {
                    location.setLocationId(rs2.getInt("location_id"));
                }
            }
            
            sql = "UPDATE trip_location SET arrive=STR_TO_DATE(?,'%m-%d-%Y'), depart=STR_TO_DATE(?,'%m-%d-%Y'), location_id=? WHERE trip_location_id =?;";
            pstatement = connection.prepareStatement(sql);
            pstatement.setString(1, location.getArrive());
            pstatement.setString(2, location.getDepart());
            pstatement.setInt(3, location.getLocationId());
            pstatement.setInt(4, location.getTripLocationId());
            pstatement.executeUpdate();
            
            /* delete the location from the location table if location_id is not referenced in the mapping table */
            sql = "SELECT * FROM trip_location WHERE location_id=?;";
            pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, oldLocationId);
            rs = pstatement.executeQuery();
            if(rs.first()) {}
            else {
                sql = "DELETE FROM location WHERE location_id=?;";
                pstatement = connection.prepareStatement(sql);
                pstatement.setInt(1, oldLocationId);
                pstatement.executeUpdate();
            }
            /**/
            
            pstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return location;
    }
    
    @Override
    public Location deleteByTripLocationId(Location location) throws Exception {
        try {
            Connection connection = getConnection();
            PreparedStatement pstatement;
            
            new ActivitySvcStatementImpl().deleteByTripLocationId(new Activity(-1, location.getTripLocationId()));
            
            /* delete from the mapping table */
            String sql = "DELETE FROM trip_location WHERE trip_location_id=?;";
            pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, location.getTripLocationId());
            pstatement.executeUpdate();
            /**/
            
            /* delete the location from the location table if location_id is not referenced in the mapping table */
            sql = "SELECT * FROM trip_location WHERE location_id=?;";
            pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, location.getLocationId());
            ResultSet rs = pstatement.executeQuery();
            if(rs.first()) {}
            else {
                sql = "DELETE FROM location WHERE location_id=?;";
                pstatement = connection.prepareStatement(sql);
                pstatement.setInt(1, location.getLocationId());
                pstatement.executeUpdate();
            }
            /**/
            
            pstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return location;
    }
    
    @Override
    public ArrayList<Location> deleteByTripId(Location location) throws Exception {
        ArrayList<Location> locations = new ArrayList();
        try {
            Connection connection = getConnection();
            PreparedStatement pstatement;
            locations = retrieveByTripId(location);
            for(Location l : locations) {
                new ActivitySvcStatementImpl().deleteByTripLocationId(new Activity(-1, l.getTripLocationId()));
            }
            
            /* delete from the mapping table */
            String sql = "DELETE FROM trip_location WHERE trip_id=?;";
            pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, location.getTripId());
            pstatement.executeUpdate();
            /**/
            
            /* delete the location from the location table if location_id is not referenced in the mapping table */
            for(Location l : locations) {
                sql = "SELECT * FROM trip_location WHERE location_id=?;";
                pstatement = connection.prepareStatement(sql);
                pstatement.setInt(1, l.getLocationId());
                ResultSet rs = pstatement.executeQuery();
                if(rs.first()) {}
                else {
                    sql = "DELETE FROM location WHERE location_id=?;";
                    pstatement = connection.prepareStatement(sql);
                    pstatement.setInt(1, l.getLocationId());
                    pstatement.executeUpdate();
                }
            }

            /**/
            
            pstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return locations;
    }
}
