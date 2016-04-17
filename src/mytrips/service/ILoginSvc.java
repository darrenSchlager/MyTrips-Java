/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;
import mytrips.domain.Login;

/**
 *
 * @author Darren
 */
public interface ILoginSvc extends IService {
    public final String NAME = "ILoginSvc";
    
    public Login create(Login login) throws Exception;
    public Login retrieve(Login login) throws Exception;
    public Login update(Login login) throws Exception;
    public Login delete(Login login) throws Exception;
    public boolean containsUsername(Login login) throws Exception;
}
