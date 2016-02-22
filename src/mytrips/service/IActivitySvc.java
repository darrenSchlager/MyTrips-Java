/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import java.util.ArrayList;
import mytrips.domain.Activity;

/**
 *
 * @author Darren
 */
public interface IActivitySvc extends IService {
    public Activity create(Activity activity) throws Exception;
    public Activity retrieveByActivityId(Activity activity) throws Exception;
    public ArrayList<Activity> retrieveByTripLocationId(Activity activity) throws Exception;
    public Activity update(Activity activity) throws Exception;
    public Activity deleteByActivityId(Activity activity) throws Exception;
    public ArrayList<Activity> deleteByTripLocationId(Activity activity) throws Exception;
}
