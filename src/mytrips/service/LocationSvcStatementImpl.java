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
public class LocationSvcStatementImpl extends ServiceAbs implements ILocationSvc {
    
    @Override
    public Location create(Location location) throws Exception {
        try {
            if(new TripSvcStatementImpl().retrieveByTripId(new Trip(location.getTripId() ,-1)) != null) {
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                
                /* insert into the location table if it isnt there already */
                String sql = "SELECT * FROM location WHERE city='"+location.getCity()+"' and state_country='"+location.getStateCountry()+"';";
                ResultSet rs = statement.executeQuery(sql);
                if(rs.first()) {
                    location.setLocationId(rs.getInt("location_id"));
                }
                else {
                    sql = "INSERT INTO location (city, state_country) VALUES ('"+location.getCity()+"', '"+location.getStateCountry()+"');";
                    statement.executeUpdate(sql);
                    sql = "SELECT last_insert_id() as location_id";
                    ResultSet rs2 = statement.executeQuery(sql);
                    if(rs2.first()) {
                        location.setLocationId(rs2.getInt("location_id"));
                    }
                }
                /**/
                
                /* insert into the mapping table */
                sql = "INSERT INTO trip_location (arrive, depart, trip_id, location_id) VALUES (STR_TO_DATE('"+location.getArrive()+"','%m-%d-%Y'), STR_TO_DATE('"+location.getDepart()+"','%m-%d-%Y'), "+location.getTripId()+", "+location.getLocationId()+");";
                statement.executeUpdate(sql);
                sql = "SELECT last_insert_id() as trip_location_id";
                rs = statement.executeQuery(sql);
                if(rs.first()) {
                    location.setTripLocationId(rs.getInt("trip_location_id"));
                }
                /**/
                
                statement.close();
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
            Statement statement = connection.createStatement();
            
            /* select from the mapping table */
            String sql = "SELECT trip_location_id, date_format(arrive, '%c-%e-%Y') as arrive, date_format(depart, '%c-%e-%Y') as depart, trip_id, location_id FROM trip_location WHERE trip_location_id="+location.getTripLocationId()+";";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.first()) {
                location = new Location(rs.getInt("trip_location_id"), rs.getString("arrive"), rs.getString("depart"), rs.getInt("trip_id"), rs.getInt("location_id"));
                /* select from the Location table */
                sql = "SELECT * FROM location where location_id="+location.getLocationId()+";";
                ResultSet rs2 = statement.executeQuery(sql);
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
            
            statement.close();
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
            Statement statement = connection.createStatement();
            
            /* select from the mapping table */
            String sql = "SELECT trip_location_id, date_format(arrive, '%c-%e-%Y') as arrive, date_format(depart, '%c-%e-%Y') as depart, trip_id, location_id FROM trip_location WHERE trip_id="+location.getTripId()+";";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                locations.add(new Location(rs.getInt("trip_location_id"), rs.getString("arrive"), rs.getString("depart"), rs.getInt("trip_id"), rs.getInt("location_id")));
            }
            for(Location l : locations) {
                /* select from the Location table */
                sql = "SELECT * FROM location where location_id="+l.getLocationId()+";";
                rs = statement.executeQuery(sql);
                if(rs.first()) {
                    l.setCity(rs.getString("city"));
                    l.setStateCountry(rs.getString("state_country"));
                    l.setActivities(new ActivitySvcStatementImpl().retrieveByTripLocationId(new Activity(-1, l.getTripLocationId())));
                }
                /**/
            }
            /**/
            
            statement.close();
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
            Statement statement = connection.createStatement();
            int oldLocationId = location.getLocationId();
            
            /* insert into the location table if it isnt there already */
            String sql = "SELECT location_id FROM location WHERE city='"+location.getCity()+"' and state_country='"+location.getStateCountry()+"';";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.first()) {
                location.setLocationId(rs.getInt("location_id"));
            }
            else {
                sql = "INSERT INTO location (city, state_country) VALUES ('"+location.getCity()+"', '"+location.getStateCountry()+"');";
                statement.executeUpdate(sql);
                sql = "SELECT last_insert_id() as location_id";
                ResultSet rs2 = statement.executeQuery(sql);
                if(rs2.first()) {
                    location.setLocationId(rs2.getInt("location_id"));
                }
            }
            
            sql = "UPDATE trip_location SET arrive=STR_TO_DATE('"+location.getArrive()+"','%m-%d-%Y'), depart=STR_TO_DATE('"+location.getDepart()+"','%m-%d-%Y'), location_id="+location.getLocationId()+" WHERE trip_location_id ="+location.getTripLocationId()+";";
            statement.executeUpdate(sql);
            
            /* delete the location from the location table if location_id is not referenced in the mapping table */
            sql = "SELECT * FROM trip_location WHERE location_id="+oldLocationId+";";
            rs = statement.executeQuery(sql);
            if(rs.first()) {}
            else {
                sql = "DELETE FROM location WHERE location_id="+oldLocationId+";";
                statement.executeUpdate(sql);
            }
            /**/
            
            statement.close();
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
            Statement statement = connection.createStatement();
            
            new ActivitySvcStatementImpl().deleteByTripLocationId(new Activity(-1, location.getTripLocationId()));
            
            /* delete from the mapping table */
            String sql = "DELETE FROM trip_location WHERE trip_location_id="+location.getTripLocationId()+";";
            statement.executeUpdate(sql);
            /**/
            
            /* delete the location from the location table if location_id is not referenced in the mapping table */
            sql = "SELECT * FROM trip_location WHERE location_id="+location.getLocationId()+";";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.first()) {}
            else {
                sql = "DELETE FROM location WHERE location_id="+location.getLocationId()+";";
                statement.executeUpdate(sql);
            }
            /**/
            
            statement.close();
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
            Statement statement = connection.createStatement();
            locations = retrieveByTripId(location);
            for(Location l : locations) {
                new ActivitySvcStatementImpl().deleteByTripLocationId(new Activity(-1, l.getTripLocationId()));
            }
            
            /* delete from the mapping table */
            String sql = "DELETE FROM trip_location WHERE trip_id="+location.getTripId()+";";
            statement.executeUpdate(sql);
            /**/
            
            /* delete the location from the location table if location_id is not referenced in the mapping table */
            for(Location l : locations) {
                sql = "SELECT * FROM trip_location WHERE location_id="+l.getLocationId()+";";
                ResultSet rs = statement.executeQuery(sql);
                if(rs.first()) {}
                else {
                    sql = "DELETE FROM location WHERE location_id="+l.getLocationId()+";";
                    statement.executeUpdate(sql);
                }
            }

            /**/
            
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
            throw e;
        }
        
        return locations;
    }
}
