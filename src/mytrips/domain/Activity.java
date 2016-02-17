/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.domain;

/**
 *
 * @author Darren
 */
public class Activity {
    private int activityId;
    private String activityName;
    private String date;
    private String time;
    private String description;
    private int tripLocationId;

    public Activity(int activityId, String activityName, String date, String time, String description, int tripLocationId) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.date = date;
        this.time = time;
        this.description = description;
        this.tripLocationId = tripLocationId;
    }
    
    public Activity(String activityName, String date, String time, String description, int tripLocationId) {
        activityId = -1;
        this.activityName = activityName;
        this.date = date;
        this.time = time;
        this.description = description;
        this.tripLocationId = tripLocationId;
    }
    
    public Activity(int activityId, int tripLocationId) {
        this.activityId = activityId;
        activityName = "";
        date = "";
        time = "";
        description = "";
        this.tripLocationId = tripLocationId;
    }

    /**
     * @return the activityId
     */
    public int getActivityId() {
        return activityId;
    }

    /**
     * @param activityId the activityId to set
     */
    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    /**
     * @return the activityName
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * @param activityName the activityName to set
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the tripLocationId
     */
    public int getTripLocationId() {
        return tripLocationId;
    }

    /**
     * @param tripLocationId the tripLocationId to set
     */
    public void setTripLocationId(int tripLocationId) {
        this.tripLocationId = tripLocationId;
    }
    
}
