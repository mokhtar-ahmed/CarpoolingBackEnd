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
public class LocationDAO {

    static Session session;
    public LocationDAO()
    {
        session = HibernateUtil.getSessionFactory().openSession();
    }
    
    public Location retrieveLocationById(int id)
    {
        Criteria criteria = session.createCriteria(Location.class,"l")
                .add(Restrictions.eq("l.id",id));
        List l =criteria.list();
        if(l.size()>0)
        {
            return (Location)l.get(0);
        }
        else
        {
            return null;
        }
        
        
    }

    public List<Location> retrieveAllLocation()
    {
        Criteria criteria = session.createCriteria(Location.class);
        List l =criteria.list();
        
        return l;
    }
}
