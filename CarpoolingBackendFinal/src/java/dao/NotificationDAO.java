/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import pojo.*;

/**
 *
 * @author Nourhan
 */
public class NotificationDAO {
        static Session session;
    
    public NotificationDAO()
    {
        session = HibernateUtil.getSessionFactory().openSession();
    }
    
    public boolean addNotification(Notification notification)
    {
        try{
            session.beginTransaction();
            session.persist(notification);  
            session.getTransaction().commit();
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
    
    public boolean updateNotification(Notification notification)
    {
        try{
            session.beginTransaction();
            session.saveOrUpdate(notification);  
            session.getTransaction().commit();
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
    
   public Notification retrieveNotification(User user , Event event)
   {
       Criteria criteria =session.createCriteria(Notification.class)
               .add(Restrictions.eq("user", user))
               .add(Restrictions.eq("event", event));
       
       List l = criteria.list();
       if(l.size()>0)
           return  (Notification)l.get(0);
       else
           return null;
   }
   
   public  Notification retrieveNotificationByExample(Notification notification)
   {
       List l = session.createCriteria(Notification.class)
               .add(Example.create(notification).ignoreCase())
               .list();
       if (l.size() > 0) {
            Notification myNotification = (Notification) l.get(0);
            return myNotification;
        } else {
            return null;
        }
   }
   
   public  Notification retrieveNotificationById(int id)
   {
       Criteria criteria =session.createCriteria(Notification.class)
               .add(Restrictions.eq("id", id));
       List l = criteria.list();
       if(l.size()>0)
           return  (Notification)l.get(0);
       else
           return null;
   }
    
}
