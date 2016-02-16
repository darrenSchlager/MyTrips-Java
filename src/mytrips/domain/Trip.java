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
public class Trip {
    private int tripId;
    private String tripName;
    private String startDate;
    private String endDate;
    private int userId;
    
    public Trip(int tripId, String tripName, String startDate, String endDate, int userId) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
    }
    
    public Trip(String tripName, String startDate, String endDate, int userId) {
        this.tripId = -1;
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
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
     * @return the tripName
     */
    public String getTripName() {
        return tripName;
    }

    /**
     * @param tripName the tripName to set
     */
    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
}
