/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import static dao.EventDAO.session;
import org.hibernate.Session;
import pojo.*;

/**
 *
 * @author Nourhan
 */
public class EventToLocationDao {
     static Session session;
    public EventToLocationDao()
    {
        session = HibernateUtil.getSessionFactory().openSession();
    }
    
     public boolean addEventToLocation(EventToLocation event)
    {
        try{
            session.beginTransaction();
            session.persist(event);  
            session.getTransaction().commit();
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
}
