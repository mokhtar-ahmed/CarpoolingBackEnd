/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.*;

/**
 *
 * @author Mo5a
 */
public class NotificationHome {
    
    private Session session ;
    
    public NotificationHome(){
    
       session = HibernateUtil.getSessionFactory().openSession();
    }
    
    public void sendNotification(Notification notification){
        
        session.getTransaction().begin();
        session.saveOrUpdate(notification);
        session.getTransaction().commit();
    
    }
    public  List<Notification>getEventNotifications(Event event){
 
         Query createQuery = session.createQuery("from Notification n where n.event = :event ").setEntity("event", event);
         List<Notification> notificationList = createQuery.list();
         return notificationList;
    }
 
    public  List<Notification>getUserNotificationsOnEvent(User user , Event event){
 
         Query createQuery = session.createQuery("from Notification n where n.users = :user and n.event = :event ").setEntity("user", user).setEntity("event", event);
         List<Notification> notificationList = createQuery.list();
         return notificationList;
    }
     
       
    public List<Notification>viewUserNotification(User user){
         Query createQuery = session.createQuery("from Notification n where n.users = :user ").setEntity("user", user);
         List<Notification> notificationList = createQuery.list();
         return notificationList;
    
    }
    
    public List<Notification>getUserNotificationsByDate(User user , Date date){
         Query createQuery = session.createQuery("from Notification n where n.users = :user and n.notificationDate = :date ").setEntity("user", user).setEntity("date", date);
         List<Notification> notificationList = createQuery.list();
         return notificationList;
    
    }
    
       public List<Notification>getEventNotificationsByDate(Event event , Date date){
         Query createQuery = session.createQuery("from Notification n where n.event = :user and n.notificationDate = :date ").setEntity("event", event).setEntity("date", date);
         List<Notification> notificationList = createQuery.list();
         return notificationList;
    
    }
       
}
