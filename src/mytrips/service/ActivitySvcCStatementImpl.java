/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import java.sql.*;
import java.util.ArrayList;
import mytrips.domain.Activity;

/**
 *
 * @author Darren
 */
public class ActivitySvcCStatementImpl extends ServiceAbs implements IActivitySvc{
    
    @Override
    public Activity create(Activity activity) throws Exception {
        try {
            Connection connection = getConnection();
            CallableStatement cstatement = connection.prepareCall("{CALL create_activity(?,?,?,?)}");
            cstatement.setString(1, activity.getActivityName());
            cstatement.setString(2, activity.getDate()+" "+activity.getTime());
            cstatement.setString(3, activity.getDescription());
            cstatement.setInt(4, activity.getTripLocationId());
            cstatement.executeUpdate();
            
            /* get the activity_id that was just created */
            cstatement = connection.prepareCall("{CALL last_insert_id()}");
            ResultSet rs = cstatement.executeQuery();
            if(rs.first()) {
                activity.setActivityId(rs.getInt("last_insert_id"));
            }
            /**/
            
            cstatement.close();
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
            CallableStatement cstatement = connection.prepareCall("{CALL retrieve_activity_by_activityid(?)}");
            cstatement.setInt(1, activity.getActivityId());
            ResultSet rs = cstatement.executeQuery();
            if(rs.first()) {
                activity = new Activity(rs.getInt("activity_id"), rs.getString("activity_name"), rs.getString("date"), rs.getString("time"), rs.getString("description"), rs.getInt("trip_location_id"));
            }
            else {
                activity = null;
            }
            cstatement.close();
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
            CallableStatement cstatement = connection.prepareCall("{CALL retrieve_activity_by_triplocationid(?)}");
            cstatement.setInt(1, activity.getTripLocationId());
            ResultSet rs = cstatement.executeQuery();
            while(rs.next()) {
                activities.add(new Activity(rs.getInt("activity_id"), rs.getString("activity_name"), rs.getString("date"), rs.getString("time"), rs.getString("description"), rs.getInt("trip_location_id")));
            }
            cstatement.close();
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
            CallableStatement cstatement = connection.prepareCall("{CALL update_activity(?,?,?,?)}");
            cstatement.setString(1, activity.getActivityName());
            cstatement.setString(2, activity.getDate()+" "+activity.getTime());
            cstatement.setString(3, activity.getDescription());
            cstatement.setInt(4, activity.getActivityId());
            cstatement.executeUpdate();
            cstatement.close();
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
            CallableStatement cstatement = connection.prepareCall("{CALL delete_activity_by_activityid(?)}");
            cstatement.setInt(1, activity.getActivityId());
            cstatement.executeUpdate();
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        } 
        
        return activity;
    }
    
    @Override
    public ArrayList<Activity> deleteByTripLocationId(Activity activity) throws Exception {
        ArrayList<Activity> activities;
        try {
            Connection connection = getConnection();
            activities = retrieveByTripLocationId(activity);
            CallableStatement cstatement = connection.prepareCall("{CALL delete_activity_by_triplocationid(?)}");
            cstatement.setInt(1, activity.getTripLocationId());
            cstatement.executeUpdate();
            cstatement.close();
            connection.close();
        } catch(Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            throw e;
        } 
        
        return activities;
    }
    
}
