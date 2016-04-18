/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import mytrips.domain.Login;

/**
 *
 * @author Darren
 */
public class LoginSvcServer implements ILoginSvc {
    
    public boolean authenticate(Login login) {
        boolean isAuthenticated = false;
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 8000);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject(login.getUsername()+"/"+login.getPassword());
            isAuthenticated = (boolean)ois.readObject();
            oos.close();
            ois.close();
            socket.close();
        
        } catch (Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
        }
        return isAuthenticated;
    }
            
    @Override
    public Login create(Login login) throws Exception {
        return null;
    }
    
    @Override
    public Login retrieve(Login login) throws Exception {
        return null;
    }
    
    @Override
    public Login update(Login login) throws Exception {
        return null;
    }
    
    @Override
    public Login delete(Login login) throws Exception {
        return null;
    }
    
    @Override
    public boolean containsUsername(Login login) throws Exception {
        return false;
    }
}
