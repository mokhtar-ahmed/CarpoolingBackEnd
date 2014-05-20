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
public class EventToLocationDAO {

    static Session session;
    public EventToLocationDAO()
    {
        session = HibernateUtil.getSessionFactory().openSession();
    }
    
    //retrieveEventToLocation
    
    public EventToLocation retrieveEventToLocation(EventToLocation eventToLocation)
    {
        Criteria criteria = session.createCriteria(EventToLocation.class)
                .add(Restrictions.eq("id", eventToLocation.getId()));
        List l = criteria.list();
        
        if (l.size() > 0) {
            EventToLocation e = (EventToLocation) l.get(0);
            return e;
        } else {
            return null;
        }
    }
    // add TO (Location)
     public boolean addEventToLocation(EventToLocation event)
    {
        try{
            session.beginTransaction();
            session.persist(event);  
            session.getTransaction().commit();
            return true;
        }
        catch(RuntimeException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
     public boolean deleteEventToLocation(EventToLocation event)
    {
        try{
            session.beginTransaction();
            session.delete(event);  
            session.getTransaction().commit();
            return true;
        }
        catch(RuntimeException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
     
     //Update
     public boolean updateEventToLocation(EventToLocation event)
    {
        try{
            session.beginTransaction();
            session.saveOrUpdate(event);  
            session.getTransaction().commit();
            return true;
        }
        catch(RuntimeException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
}
