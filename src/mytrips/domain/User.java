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
public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private Login login;
    private ArrayList<Trip> trips;
    
    public User(int userId, String firstName, String lastName)
    {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        trips = new ArrayList();
        login = null;
    }
    
    public User(String firstName, String lastName)
    {
        this.userId = -1;
        this.firstName = firstName;
        this.lastName = lastName;
        trips = new ArrayList();
        login = null;
    }
    
    public User(int userId)
    {
        this.userId = userId;
        this.firstName = "";
        this.lastName = "";
        trips = new ArrayList();
        login = null;
    }
    
    public boolean isNotEmpty() {
        return firstName!=null && !firstName.equals("") && lastName!=null && !lastName.equals("") ;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    /**
     * @return the login
     */
    public Login getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(Login login) {
        this.login = login;
    }
    
    /**
     * @return the trips
     */
    public ArrayList<Trip> getTrips() {
        return trips;
    }

    /**
     * @param trips the trips to set
     */
    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }
    
    @Override
    public String toString() {
        String str = "    User :: "+userId+" "+firstName+" "+lastName;
        if(login!=null) {
            str += "\n"+login.toString();
        }
        for(Trip t : trips) {
            str += "\n"+t.toString();
            for(Location l : t.getLocations()) {
                str += "\n"+l.toString();
                for(Activity a : l.getActivities()) {
                    str += "\n"+a.toString();
                }
            }
        }
        return str;
    }
    
}
