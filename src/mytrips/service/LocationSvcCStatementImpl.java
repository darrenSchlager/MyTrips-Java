/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import java.sql.*;
import java.util.ArrayList;
import mytrips.domain.Activity;
import mytrips.domain.Location;
import mytrips.domain.Trip;

/**
 *
 * @author Darren
 */
public class LocationSvcCStatementImpl extends ServiceAbs implements ILocationSvc {
    
    @Override
    public Location create(Location location) throws Exception {
        try {
            if(new TripSvcCStatementImpl().retrieveByTripId(new Trip(location.getTripId() ,-1)) != null) {
                Connection connection = getConnection();
                CallableStatement cstatement;
                
                /* insert into the location table if it isnt there already */
                cstatement = connection.prepareCall("{CALL retrieve_location_by_city_and_statecountry(?,?)}");
                cstatement.setString(1, location.getCity());
                cstatement.setString(2, location.getStateCountry());
                ResultSet rs = cstatement.executeQuery();
                if(rs.first()) {
                    location.setLocationId(rs.getInt("location_id"));
                }
                else {
                    cstatement = connection.prepareCall("{CALL create_location(?,?)}");
                    cstatement.setString(1, location.getCity());
                    cstatement.setString(2, location.getStateCountry());
                    cstatement.executeUpdate();
                    
                    cstatement = connection.prepareCall("{CALL last_insert_id()}");
                    ResultSet rs2 = cstatement.executeQuery();
                    if(rs2.first()) {
                        location.setLocationId(rs2.getInt("last_insert_id"));
                    }
                }
                /**/
                
                /* insert into the mapping table */
                cstatement = connection.prepareCall("{CALL create_triplocation(?,?,?,?)}");
                cstatement.setString(1, location.getArrive());
                cstatement.setString(2, location.getDepart());
                cstatement.setInt(3, location.getTripId());
                cstatement.setInt(4, location.getLocationId());
                cstatement.executeUpdate();
                cstatement = connection.prepareCall("{CALL last_insert_id()}");
                rs = cstatement.executeQuery();
                if(rs.first()) {
                    location.setTripLocationId(rs.getInt("last_insert_id"));
                }
                /**/
                
                cstatement.close();
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
            CallableStatement cstatement;
            
            /* select from the mapping table */
            cstatement = connection.prepareCall("{CALL retrieve_triplocation_by_triplocationid(?)}");
            cstatement.setInt(1, location.getTripLocationId());
            ResultSet rs = cstatement.executeQuery();
            if(rs.first()) {
                location = new Location(rs.getInt("trip_location_id"), rs.getString("arrive"), rs.getString("depart"), rs.getInt("trip_id"), rs.getInt("location_id"));
                /* select from the Location table */
                
                cstatement = connection.prepareCall("{CALL retrieve_location_by_locationid(?)}");
                cstatement.setInt(1, location.getLocationId());
                ResultSet rs2 = cstatement.executeQuery();
                if(rs2.first()) {
                    location.setCity(rs2.getString("city"));
                    location.setStateCountry(rs2.getString("state_country"));
                    location.setActivities(new ActivitySvcCStatementImpl().retrieveByTripLocationId(new Activity(-1, location.getTripLocationId())));
                }
                /**/
            }
            else {
                location = null;
            }
            /**/
            
            cstatement.close();
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
            CallableStatement cstatement;
            
            /* select from the mapping table */
            cstatement = connection.prepareCall("{CALL retrieve_triplocation_by_tripid(?)}");
            cstatement.setInt(1, location.getTripId());
            ResultSet rs = cstatement.executeQuery();
            while(rs.next()) {
                locations.add(new Location(rs.getInt("trip_location_id"), rs.getString("arrive"), rs.getString("depart"), rs.getInt("trip_id"), rs.getInt("location_id")));
            }
            for(Location l : locations) {
                /* select from the Location table */
                cstatement = connection.prepareCall("{CALL retrieve_location_by_locationid(?)}");
                cstatement.setInt(1, l.getLocationId());
                rs = cstatement.executeQuery();
                if(rs.first()) {
                    l.setCity(rs.getString("city"));
                    l.setStateCountry(rs.getString("state_country"));
                    l.setActivities(new ActivitySvcCStatementImpl().retrieveByTripLocationId(new Activity(-1, l.getTripLocationId())));
                }
                /**/
            }
            /**/
            
            cstatement.close();
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
            CallableStatement cstatement;
            int oldLocationId = location.getLocationId();
            
            /* insert into the location table if it isnt there already */
            cstatement = connection.prepareCall("{CALL retrieve_locationid_by_city_and_statecountry(?,?)}");
            cstatement.setString(1, location.getCity());
            cstatement.setString(2, location.getStateCountry());
            ResultSet rs = cstatement.executeQuery();
            if(rs.first()) {
                location.setLocationId(rs.getInt("location_id"));
            }
            else {
                cstatement = connection.prepareCall("{CALL create_location(?,?)}");
                cstatement.setString(1, location.getCity());
                cstatement.setString(2, location.getStateCountry());
                cstatement.executeUpdate();
                cstatement = connection.prepareCall("{CALL last_insert_id()}");
                ResultSet rs2 = cstatement.executeQuery();
                if(rs2.first()) {
                    location.setLocationId(rs2.getInt("last_insert_id"));
                }
            }
            
            cstatement = connection.prepareCall("{CALL update_triplocation(?,?,?,?)}");
            cstatement.setString(1, location.getArrive());
            cstatement.setString(2, location.getDepart());
            cstatement.setInt(3, location.getLocationId());
            cstatement.setInt(4, location.getTripLocationId());
            cstatement.executeUpdate();
            
            /* delete the location from the location table if location_id is not referenced in the mapping table */
            cstatement = connection.prepareCall("{CALL retrieve_triplocation_by_locationid(?)}");
            cstatement.setInt(1, oldLocationId);
            rs = cstatement.executeQuery();
            if(rs.first()) {}
            else {
                cstatement = connection.prepareCall("{CALL delete_location(?)}");
                cstatement.setInt(1, oldLocationId);
                cstatement.executeUpdate();
            }
            /**/
            
            cstatement.close();
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
            CallableStatement cstatement;
            
            new ActivitySvcCStatementImpl().deleteByTripLocationId(new Activity(-1, location.getTripLocationId()));
            
            /* delete from the mapping table */
            cstatement = connection.prepareCall("{CALL delete_triplocation_by_triplocationid(?)}");
            cstatement.setInt(1, location.getTripLocationId());
            cstatement.executeUpdate();
            /**/
            
            /* delete the location from the location table if location_id is not referenced in the mapping table */
            cstatement = connection.prepareCall("{CALL retrieve_triplocation_by_locationid(?)}");
            cstatement.setInt(1, location.getLocationId());
            ResultSet rs = cstatement.executeQuery();
            if(rs.first()) {}
            else {
                cstatement = connection.prepareCall("{CALL delete_location(?)}");
                cstatement.setInt(1, location.getLocationId());
                cstatement.executeUpdate();
            }
            /**/
            
            cstatement.close();
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
            CallableStatement cstatement;
            locations = retrieveByTripId(location);
            for(Location l : locations) {
                new ActivitySvcCStatementImpl().deleteByTripLocationId(new Activity(-1, l.getTripLocationId()));
            }
            
            /* delete from the mapping table */
            cstatement = connection.prepareCall("{CALL delete_triplocation_by_tripid(?)}");
            cstatement.setInt(1, location.getTripId());
            cstatement.executeUpdate();
            /**/
            
            /* delete the location from the location table if location_id is not referenced in the mapping table */
            for(Location l : locations) {
                cstatement = connection.prepareCall("{CALL retrieve_triplocation_by_locationid(?)}");
                cstatement.setInt(1, l.getLocationId());
                ResultSet rs = cstatement.executeQuery();
                if(rs.first()) {}
                else {
                    cstatement = connection.prepareCall("{CALL delete_location(?)}");
                    cstatement.setInt(1, l.getLocationId());
                    cstatement.executeUpdate();
                }
            }

            /**/
            
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return locations;
    }
    
}
