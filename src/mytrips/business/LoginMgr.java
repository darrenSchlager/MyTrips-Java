/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.business;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
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
    
    public boolean socketAuthenticate(String credentials) throws Exception {
        boolean isAuthenticated = false;
        Socket socket = new Socket(InetAddress.getLocalHost(), 8000);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        oos.writeObject(credentials);
        isAuthenticated = (boolean)ois.readObject();
        oos.close();
        ois.close();
        socket.close();
        return isAuthenticated;
    }
    
    public Login create(Login login) throws Exception {
        ILoginSvc loginSvc = (ILoginSvc)getService(ILoginSvc.NAME);
        Login loginDb = loginSvc.create(login);
        return loginDb;
    }
    
    public Login retrieve(Login login) throws Exception {
        ILoginSvc loginSvc = (ILoginSvc)getService(ILoginSvc.NAME);
        Login loginDb = loginSvc.retrieve(login);
        return loginDb;
    }
    
    public boolean usernameExists(Login login) throws Exception {
        ILoginSvc loginSvc = (ILoginSvc)getService(ILoginSvc.NAME);
        return loginSvc.containsUsername(login);
    }
    
}
