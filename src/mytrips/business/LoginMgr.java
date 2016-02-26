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
        ILoginSvc loginSvc = (ILoginSvc)factory.getService(ILoginSvc.NAME);
        Login dbLogin = loginSvc.retrieve(login);
        if(dbLogin != null)
        {
            IUserSvc userSvc = (IUserSvc)factory.getService(IUserSvc.NAME);
            user = new User(dbLogin.getUserId());
            user = userSvc.retrieve(user);
        }
        return user;
    }
    
}
