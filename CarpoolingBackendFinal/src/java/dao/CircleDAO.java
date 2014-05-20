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
public class CircleDAO {
    static Session session;
    
    public CircleDAO()
    {
        session = HibernateUtil.getSessionFactory().openSession();
    }
    
    //retrieveCircleById
    public Circle retrieveCircleById(int id)
    {
        Criteria criteria = session.createCriteria(Circle.class)
                .add(Restrictions.eq("id", id));
        List l= criteria.list();
        if(l.size()>0)
        {
            Circle c=(Circle) l.get(0);
            return c;
        }
        else{
            return null;
        }
    }
    public ExistIn retrieveExistIn(ExistInId existInId)
    {
        Criteria criteria= session.createCriteria(ExistIn.class)
                .add(Restrictions.eq("id", existInId));
        
        List l = criteria.list();
        
        if(l.size()>0)
        {
            ExistIn existIn=(ExistIn) l.get(0);
            return existIn;
        }
        else{
            return null;
        }
    }
    
    public boolean updateExistIn(ExistIn existIn)
    {
        try{
            session.beginTransaction();
            session.saveOrUpdate(existIn);  
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
