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
public class UserStatueDAO {
    
    static Session session;
    public UserStatueDAO()
    {
        session = HibernateUtil.getSessionFactory().openSession();
    }
    public UserStatue retrieveUserStatueById(int id)
    {
        try
        {
        Criteria criteria =session.createCriteria(UserStatue.class)
                .add(Restrictions.eq("id", id));
            List list =criteria.list();
        UserStatue userStatue =(UserStatue)list.get(0);
        return userStatue;
        }catch(Exception ex)
        {
            return null;
        }
    }
    
    
    
}
