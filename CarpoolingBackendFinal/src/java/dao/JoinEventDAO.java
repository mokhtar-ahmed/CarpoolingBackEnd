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
    static Session session;
    public JoinEventDAO()
    {
        session = HibernateUtil.getSessionFactory().openSession();
    }
    
    
    public JoinEvent retrieveJoinEvent (int userId, int eventId)
    {
        Criteria criteria = session.createCriteria(JoinEvent.class,"e")
                .add(Restrictions.eq("e.users.id",userId ))
                .add(Restrictions.eq("e.event.idEvent",eventId));
        
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
    public boolean updateJoinEvent(JoinEvent jonEvent)
    {
        session.beginTransaction();
        session.saveOrUpdate(jonEvent);  
        session.getTransaction().commit();   
        return true;
    }
    
}
