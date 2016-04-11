/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.business;

import mytrips.domain.Trip;
import mytrips.service.ILoginSvc;
import mytrips.service.ITripSvc;

/**
 *
 * @author Darren
 */
public class TripMgr extends ManagerAbs {
    
    public Trip create(Trip trip) throws Exception {
        ITripSvc tripSvc = (ITripSvc)getService(ITripSvc.NAME);
        Trip tripDb = tripSvc.create(trip);
        return tripDb;
    }
    
}
