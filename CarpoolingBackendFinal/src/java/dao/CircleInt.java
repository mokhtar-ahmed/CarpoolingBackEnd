/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import pojo.Circle;
import pojo.ExistIn;
import pojo.User;

/**
 *
 * @author Rehab
 */
public interface CircleInt {
    public void addCircle(Circle circle);
    public void updateCircle(Circle circle,User user);
    public List<Circle> retrieveUserCircles(User user);//Users user);
    public void editCircle(Circle circle);
    public void deleteCircle(Circle circle);
    public void blockUserFromCircle(ExistIn ex);//exist in table
    public boolean isBlocked(ExistIn ex);//
    public void addUserToCircle(ExistIn e);//
    public void removeUserFromCircle(ExistIn e);//
    public Circle retrieveCircleByName(User user,Circle circle);//
    public void emptyCircle(Circle circle);
    public Circle retrieveCircleByUserIdAndCircleName(Circle circle);
    public List<User> retrieveCircleUsers(Circle circle);//
    public Circle retrieveCircleById(Circle circle);
    public ExistIn retrieveExistInUser(ExistIn ex);
    
}
