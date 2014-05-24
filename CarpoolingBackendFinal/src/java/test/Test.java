/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import dao.EventDAO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojo.Event;
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
        try {
            //        Set<User> users = new HashSet(0);
//        NewEventUtil newEventUtil = new NewEventUtil();
//        users =newEventUtil.getUsersExistInCircle(1);
//        for(Iterator it = users.iterator(); it.hasNext();)
//        {
//            User u = (User)it.next();
//            System.out.println(u);
//        }
        
//        String s= new ApplicatipnUtil().jsonException("hhhhhhhhhhhhh");
//        int x= new ApplicatipnUtil().numOfAttendUsers(2);
//        System.out.println(x);
//
            String da ="27/01/2015 00:08:00.8";
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
            
            Date date = df.parse(da);
            
            boolean b=new ApplicatipnUtil().checkIfDateExsit(date, 1);
            System.out.println(b);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
    }
}
