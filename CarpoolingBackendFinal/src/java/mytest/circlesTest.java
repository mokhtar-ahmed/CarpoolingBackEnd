/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mytest;

import dao.CircleImp;
import dao.UserImp;
import java.util.List;
import pojo.Circle;
import pojo.User;

/**
 *
 * @author Mo5a
 */
public class circlesTest {
    
    
    public static void main(String[] args){
    
          CircleImp circleimp = new CircleImp();
        User us1 = new UserImp().retrieveUserById(1);
        System.out.println(us1.getUsername());
        List<Circle> c=circleimp.retrieveUserCircles(us1);
        
        System.out.println(c.size());
    
    }
}
