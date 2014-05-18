/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.sql.SQLException;
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
    
    //private static JoinEventDAO joinEventDAO ; 
    
    public JoinEventDAO()
    {
        
        session = HibernateUtil.getSessionFactory().openSession();
    }
    
//    
//    public static JoinEventDAO getInstance(){
//        
//        if(joinEventDAO == null){
//            joinEventDAO = new JoinEventDAO() ; 
//        }
//        return joinEventDAO ; 
//    }
    
    public JoinEvent retrieveJoinEvent (int userId, int eventId)
    {
        Criteria criteria = session.createCriteria(JoinEvent.class,"e")
                .add(Restrictions.eq("e.user.id",userId ))
                .add(Restrictions.eq("e.event.id",eventId));
        
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

    public  List<JoinEvent> retrieveAttendedEvent(int id)
    {
        Criteria criteria = session.createCriteria(JoinEvent.class)
                .add(Restrictions.eq("id.userId", id))
                .add(Restrictions.eq("userStatue.id", 4));
        List<JoinEvent> l = criteria.list();
        return l;
    }
    public boolean updateJoinEvent(JoinEvent jonEvent)
    {
        session.beginTransaction();
        session.saveOrUpdate(jonEvent);  
        session.getTransaction().commit();   
        return true;
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
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
}
