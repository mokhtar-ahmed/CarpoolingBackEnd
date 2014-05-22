/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import pojo.Phone;
import pojo.User;

/**
 *
 * @author Rehab
 */
public interface UsersInt {
    public void addUser(User user);
    public User retrieveUserByUserName(User user);
    public User retrieveUserById(User user);
    public void edit(User user);
    public User retrieveUserByPhone(String p);
    public User retrieveUserBymail(String m);
    public boolean ifExist(String phone); 
}
