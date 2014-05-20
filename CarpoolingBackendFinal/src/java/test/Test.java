/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import pojo.User;
import util.NewEventUtil;
import util.ApplicatipnUtil;

/**
 *
 * @author Nourhan
 */
public class Test {
    public static void main (String args[])
    {
//        Set<User> users = new HashSet(0);
//        NewEventUtil newEventUtil = new NewEventUtil();
//        users =newEventUtil.getUsersExistInCircle(1);
//        for(Iterator it = users.iterator(); it.hasNext();)
//        {
//            User u = (User)it.next();
//            System.out.println(u);
//        }
        
//        String s= new ApplicatipnUtil().jsonException("hhhhhhhhhhhhh");
        int x= new ApplicatipnUtil().numOfAttendUsers(2);
        System.out.println(x);
        
    }
}
