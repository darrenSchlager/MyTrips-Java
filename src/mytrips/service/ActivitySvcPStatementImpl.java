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
public class ActivitySvcPStatementImpl extends ServiceAbs implements IActivitySvc {
    
    @Override
    public Activity create(Activity activity) throws Exception {
        try {
            Connection connection = getConnection();
            String sql = "INSERT INTO activity (activity_name, date_time, description, trip_location_id) VALUES (?, STR_TO_DATE(?, '%m-%d-%Y %l:%i %p'), ?, ?);";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setString(1, activity.getActivityName());
            pstatement.setString(2, activity.getDate()+" "+activity.getTime());
            pstatement.setString(3, activity.getDescription());
            pstatement.setInt(4, activity.getTripLocationId());
            pstatement.executeUpdate();
            
            /* get the activity_id that was just created */
            sql = "SELECT last_insert_id() as activity_id;";
            pstatement = connection.prepareStatement(sql);
            ResultSet rs = pstatement.executeQuery();
            if(rs.first()) {
                activity.setActivityId(rs.getInt("activity_id"));
            }
            /**/
            
            pstatement.close();
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
            String sql = "SELECT activity_id, activity_name, date_format(date_time, '%c-%e-%Y') as date, date_format(date_time, '%l:%i %p') as time, description, trip_location_id FROM activity WHERE activity_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, activity.getActivityId());
            ResultSet rs = pstatement.executeQuery();
            if(rs.first()) {
                activity = new Activity(rs.getInt("activity_id"), rs.getString("activity_name"), rs.getString("date"), rs.getString("time"), rs.getString("description"), rs.getInt("trip_location_id"));
            }
            else {
                activity = null;
            }
            pstatement.close();
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
            String sql = "SELECT activity_id, activity_name, date_format(date_time, '%c-%e-%Y') as date, date_format(date_time, '%l:%i %p') as time, description, trip_location_id FROM activity WHERE trip_location_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, activity.getTripLocationId());
            ResultSet rs = pstatement.executeQuery();
            while(rs.next()) {
                activities.add(new Activity(rs.getInt("activity_id"), rs.getString("activity_name"), rs.getString("date"), rs.getString("time"), rs.getString("description"), rs.getInt("trip_location_id")));
            }
            pstatement.close();
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
            String sql = "UPDATE activity SET activity_name=?, date_time=STR_TO_DATE(?, '%m-%d-%Y %l:%i %p'), description=? WHERE activity_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setString(1, activity.getActivityName());
            pstatement.setString(2, activity.getDate()+" "+activity.getTime());
            pstatement.setString(3, activity.getDescription());
            pstatement.setInt(4, activity.getActivityId());
            pstatement.executeUpdate();
            pstatement.close();
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
            String sql = "DELETE FROM activity WHERE activity_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, activity.getActivityId());
            pstatement.executeUpdate();
            pstatement.close();
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
            activities = retrieveByTripLocationId(activity);
            String sql = "DELETE FROM activity WHERE trip_location_id=?;";
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, activity.getTripLocationId());
            pstatement.executeUpdate();
            pstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        }
        
        return activities;
    }
}
