/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytrips.service;
import mytrips.domain.User;

/**
 *
 * @author Darren
 */
public interface IUserSvc {
    public User create(User user) throws Exception;
}
