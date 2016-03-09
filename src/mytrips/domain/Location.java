/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.domain;

import java.util.ArrayList;

/**
 *
 * @author Darren
 */
public class Location {
    
    private int tripLocationId;
    private String arrive;
    private String depart;
    private int tripId;
    private int locationId;
    private String city;
    private String stateCountry;
    private ArrayList<Activity> activities;
    
    public Location(int tripLocationId, String arrive, String depart, int tripId, int locationId, String city, String stateCountry) {
        this.tripLocationId = tripLocationId;
        this.arrive = arrive;
        this.depart = depart;
        this.tripId = tripId;
        this.locationId = locationId;
        this.city = city;
        this.stateCountry = stateCountry;
        activities = new ArrayList();
    }
    
    public Location(int tripLocationId, String arrive, String depart, int tripId, int locationId) {
        this.tripLocationId = tripLocationId;
        this.arrive = arrive;
        this.depart = depart;
        this.tripId = tripId;
        this.locationId = locationId;
        city = "";
        stateCountry = "";
        activities = new ArrayList();
    }
    
    public Location(String arrive, String depart, int tripId, String city, String stateCountry) {
        tripLocationId = -1;
        this.arrive = arrive;
        this.depart = depart;
        this.tripId = tripId;
        locationId = -1;
        this.city = city;
        this.stateCountry = stateCountry;
        activities = new ArrayList();
    }
    
        public Location(int tripLocationId, int tripId) {
        this.tripLocationId = tripLocationId;
        arrive = "";
        depart = "";
        this.tripId = tripId;
        locationId = -1;
        city = "";
        stateCountry = "";
        activities = new ArrayList();
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

    /**
     * @return the arrive
     */
    public String getArrive() {
        return arrive;
    }

    /**
     * @param arrive the arrive to set
     */
    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    /**
     * @return the depart
     */
    public String getDepart() {
        return depart;
    }

    /**
     * @param depart the depart to set
     */
    public void setDepart(String depart) {
        this.depart = depart;
    }

    /**
     * @return the tripId
     */
    public int getTripId() {
        return tripId;
    }

    /**
     * @param tripId the tripId to set
     */
    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    /**
     * @return the locationId
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the stateCountry
     */
    public String getStateCountry() {
        return stateCountry;
    }

    /**
     * @param stateCountry the stateCountry to set
     */
    public void setStateCountry(String stateCountry) {
        this.stateCountry = stateCountry;
    }

    /**
     * @return the activities
     */
    public ArrayList<Activity> getActivities() {
        return activities;
    }

    /**
     * @param activities the activities to set
     */
    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }
    
    @Override
    public String toString() {
        return  "Location :::::: "+tripLocationId+" "+arrive+" "+depart+" "+tripId+" "+locationId+" "+city+" "+stateCountry;
    }
}
