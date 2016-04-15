/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.business;

import mytrips.domain.User;
import mytrips.service.IUserSvc;

/**
 *
 * @author Darren
 */
public class UserMgr extends ManagerAbs {
    public User create(User user) throws Exception {
        IUserSvc userSvc = (IUserSvc)getService(IUserSvc.NAME);
        User userDb = userSvc.create(user);
        return userDb;
    }
    
    public User delete(User user) throws Exception {
        IUserSvc userSvc = (IUserSvc)getService(IUserSvc.NAME);
        User userDb = userSvc.delete(user);
        return userDb;
    }
    
    public User retrieve(User user) throws Exception {
        IUserSvc userSvc = (IUserSvc)getService(IUserSvc.NAME);
        User userDb = userSvc.retrieve(user);
        return userDb;
    }
}
