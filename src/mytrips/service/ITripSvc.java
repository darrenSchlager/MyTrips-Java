/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import java.util.ArrayList;
import mytrips.domain.Trip;

/**
 *
 * @author Darren
 */
public interface ITripSvc extends IService {
    public final String NAME = "ITripSvc";
    
    public Trip create(Trip trip) throws Exception;
    public Trip retrieveByTripId(Trip trip) throws Exception;
    public ArrayList<Trip> retrieveByUserId(Trip trip) throws Exception;
    public Trip update(Trip trip) throws Exception;
    public Trip deleteByTripId(Trip trip) throws Exception;
    public ArrayList<Trip> deleteByUserId(Trip trip) throws Exception;
}
