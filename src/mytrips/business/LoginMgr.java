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
    
}
