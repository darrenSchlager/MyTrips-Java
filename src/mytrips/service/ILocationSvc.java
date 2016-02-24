/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import java.util.ArrayList;
import mytrips.domain.Location;

/**
 *
 * @author Darren
 */
public interface ILocationSvc extends IService {
    public final String NAME = "ILocationSvc";
    
    public Location create(Location location) throws Exception; 
    public Location retrieveByTripLocationId(Location location) throws Exception;
    public ArrayList<Location> retrieveByTripId(Location location) throws Exception;
    public Location update(Location location) throws Exception;
    public Location deleteByTripLocationId(Location location) throws Exception;
    public ArrayList<Location> deleteByTripId(Location location) throws Exception;
}
