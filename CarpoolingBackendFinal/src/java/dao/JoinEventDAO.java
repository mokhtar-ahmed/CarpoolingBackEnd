/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.*;
/**
 *
 * @author Nourhan
 */
public class JoinEventDAO {
    
    private static Session session;
    public JoinEventDAO()
    {
        
        session = HibernateUtil.getSessionFactory().openSession();
    }
    
    //retrieveOne when sate = invited
   public JoinEvent retrieveJoinEvent (int userId, int eventId)
    {
        Criteria criteria = session.createCriteria(JoinEvent.class,"e")
                .add(Restrictions.eq("e.user.id",userId ))
                .add(Restrictions.eq("e.event.id",eventId))
                .add(Restrictions.eq("userStatue.id", 1));
        
        List l =criteria.list();
        if(l.size()>0)
        {
            return (JoinEvent)l.get(0);
        }
        else
        {
            return null;
        }
    }
   
   
   //retrieveOne when sate = join
   public JoinEvent retrieveJoinEvent2 (int userId, int eventId)
    {
        Criteria criteria = session.createCriteria(JoinEvent.class,"e")
                .add(Restrictions.eq("e.user.id",userId ))
                .add(Restrictions.eq("e.event.id",eventId))
                .add(Restrictions.eq("userStatue.id", 3));
        
        List l =criteria.list();
        if(l.size()>0)
        {
            return (JoinEvent)l.get(0);
        }
        else
        {
            return null;
        }
    }
    // retriev all events of user which he attended
    public  List<JoinEvent> retrieveAttendedEvent(int id)
    {
        Criteria criteria = session.createCriteria(JoinEvent.class)
                .add(Restrictions.eq("id.userId", id))
                .add(Restrictions.eq("userStatue.id", 4));
        List<JoinEvent> l = criteria.list();
        return l;
    }
    public List<JoinEvent> retrieveAcceptUsers(int eventId)
    {
        Criteria criteria = session.createCriteria(JoinEvent.class)
                .add(Restrictions.eq("event.id", eventId))
                .add(Restrictions.eq("userStatue.id", 4));
        List<JoinEvent> l = criteria.list();
        return l;
    }
    public boolean updateJoinEvent(JoinEvent jonEvent)
    {
        try{
            session.beginTransaction();
            session.saveOrUpdate(jonEvent);  
            session.getTransaction().commit();   
            return true;
        }catch(RuntimeException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean addJoinEvent(JoinEvent jonEvent)
    {
        try{
            session.beginTransaction();
            session.persist(jonEvent);  
            session.getTransaction().commit();
            return true;
        }
        catch(RuntimeException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean deleteJoinEvent(JoinEvent jonEvent)
    {
        try{
            session.beginTransaction();
            session.delete(jonEvent);  
            session.getTransaction().commit();
            return true;
        }
        catch(RuntimeException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
    public List<JoinEvent> retrieveWantToJoinUsers(int eventId)
    {
        Criteria criteria = session.createCriteria(JoinEvent.class)
                .add(Restrictions.eq("event.id", eventId))
                .add(Restrictions.eq("userStatue.id", 3));
        List<JoinEvent> l = criteria.list();
        return l;
    }
}
