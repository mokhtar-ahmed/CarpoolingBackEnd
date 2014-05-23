package util;

import PushNotificationUtil.MessageUtil;
import dao.CircleDAO;
import dao.CircleImp;
import dao.NotificationDAO;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import pojo.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nourhan
 */
public class NewEventUtil 
{
    public Set<User> getUsersExistInCircle(int id)
    {        Set<User> users = new HashSet(0);//cjeck it
        
        Circle circle = new CircleDAO().retrieveCircleById(id);
        System.out.println(circle);
        if(circle!=null)
        {
        Set existIn =circle.getExistIns();
        for (Iterator it = existIn.iterator(); it.hasNext();)
        {
            ExistIn ei = (ExistIn) it.next();
            if(ei!=null && !ei.getBolckStatue().equals("blocked"))
            {
                User user=ei.getUser();
                users.add(user);
            }
        }
        return users;
        }
        return null;
        
    }
    
    
    public void sendNotificationToUser(JoinEvent joinEvent , String message ,String statue)
    {
        NotificationDAO ndao = new NotificationDAO();    
        Notification n = new Notification(joinEvent.getUser(), joinEvent.getEvent(), new Date(),"unread",statue);
        ndao.addNotification(n);
        System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");                   
        String userId=joinEvent.getUser().getPushNotificationId();
        try {
            int out=MessageUtil.sendMessage( userId, message);
            System.out.println(out+"---------------------------------------------");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}