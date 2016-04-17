/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.business;

import mytrips.domain.*;
import mytrips.service.*;

/**
 *
 * @author Darren
 */
public class LoginMgr extends ManagerAbs{
    
    public User authenticate(Login login) throws Exception {
        User user = null;
        ILoginSvc loginSvc = (ILoginSvc)getService(ILoginSvc.NAME);
        Login dbLogin = loginSvc.retrieve(login);
        if(dbLogin != null)
        {
            if(dbLogin.equals(login)) {
                IUserSvc userSvc = (IUserSvc)getService(IUserSvc.NAME);
                user = userSvc.retrieve(new User(dbLogin.getUserId()));
            }
        }
        return user;
    }
    
    public Login create(Login login) throws Exception {
        ILoginSvc loginSvc = (ILoginSvc)getService(ILoginSvc.NAME);
        Login loginDb = loginSvc.create(login);
        return loginDb;
    }
    
    public boolean usernameExists(Login login) throws Exception {
        ILoginSvc loginSvc = (ILoginSvc)getService(ILoginSvc.NAME);
        return loginSvc.containsUsername(login);
    }
    
}
