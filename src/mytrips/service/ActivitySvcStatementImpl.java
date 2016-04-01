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
public class ActivitySvcStatementImpl extends ServiceAbs implements IActivitySvc {
    
    @Override
    public Activity create(Activity activity) throws Exception {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO activity (activity_name, date_time, description, trip_location_id) VALUES ('"+activity.getActivityName()+"', STR_TO_DATE('"+activity.getDate()+" "+activity.getTime()+"', '%m-%d-%Y %l:%i %p'), '"+activity.getDescription()+"', "+activity.getTripLocationId()+");";
            statement.executeUpdate(sql);
            
            /* get the activity_id that was just created */
            sql = "SELECT last_insert_id() as activity_id;";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.first()) {
                activity.setActivityId(rs.getInt("activity_id"));
            }
            /**/
            
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return activity;
    }
    
    @Override
    public Activity retrieveByActivityId(Activity activity) throws Exception {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT activity_id, activity_name, date_format(date_time, '%c-%e-%Y') as date, date_format(date_time, '%l:%i %p') as time, description, trip_location_id FROM activity WHERE activity_id="+activity.getActivityId()+";";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.first()) {
                activity = new Activity(rs.getInt("activity_id"), rs.getString("activity_name"), rs.getString("date"), rs.getString("time"), rs.getString("description"), rs.getInt("trip_location_id"));
            }
            else {
                activity = null;
            }
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return activity;
    }
    
    @Override
    public ArrayList<Activity> retrieveByTripLocationId(Activity activity) throws Exception {
        ArrayList<Activity> activities = new ArrayList();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT activity_id, activity_name, date_format(date_time, '%c-%e-%Y') as date, date_format(date_time, '%l:%i %p') as time, description, trip_location_id FROM activity WHERE trip_location_id="+activity.getTripLocationId()+";";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                activities.add(new Activity(rs.getInt("activity_id"), rs.getString("activity_name"), rs.getString("date"), rs.getString("time"), rs.getString("description"), rs.getInt("trip_location_id")));
            }
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return activities;
    }
    
    @Override
    public Activity update(Activity activity) throws Exception {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "UPDATE activity SET activity_name='"+activity.getActivityName()+"', date_time=STR_TO_DATE('"+activity.getDate()+" "+activity.getTime()+"', '%m-%d-%Y %l:%i %p'), description='"+activity.getDescription()+"' WHERE activity_id="+activity.getActivityId()+";";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return activity;
    }
    
    @Override
    public Activity deleteByActivityId(Activity activity) throws Exception {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM activity WHERE activity_id="+activity.getActivityId()+";";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return activity;
    }
    
    @Override
    public ArrayList<Activity> deleteByTripLocationId(Activity activity) throws Exception {
        ArrayList<Activity> activities = new ArrayList();
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            activities = retrieveByTripLocationId(activity);
            String sql = "DELETE FROM activity WHERE trip_location_id="+activity.getTripLocationId()+";";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return activities;
    }
}
