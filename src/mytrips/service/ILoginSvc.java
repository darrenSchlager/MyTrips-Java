/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;
import mytrips.domain.*;

/**
 *
 * @author Darren
 */
public interface ILoginSvc {
    public Login create(Login login) throws Exception;
}
