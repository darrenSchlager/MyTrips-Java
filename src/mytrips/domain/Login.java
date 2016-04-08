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
public class Login {
    private int userId;
    private String username;
    private String password;
    
    public Login(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
    
    public Login(String username, String password) {
        this.userId = -1;
        this.username = username;
        this.password = password;
    }
    
    public Login(int userId) {
        this.userId = userId;
        this.username = "";
        this.password = "";
    }
    
    public boolean isNotEmpty() {
        return username!=null && !username.equals("") && password!=null && !password.equals("") ;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this==obj) {
            return true;
        }
        if(obj.getClass()!=Login.class) {
            return false;
        }
       /* this also works
        if(!(obj instanceof Login)) {
            return false;
        }
        */
        Login l = (Login) obj;
        return username.equals(l.username) && password.equals(l.password);

    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
    
    @Override
    public String toString() {
        return "   Login :::: "+userId+" "+username+" "+password;
    }
    
}
