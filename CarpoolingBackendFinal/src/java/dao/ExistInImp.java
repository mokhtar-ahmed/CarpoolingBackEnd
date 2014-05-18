/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import static dao.JoinEventDAO.session;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import pojo.Circle;
import pojo.ExistIn;
import pojo.User;

/**
 *
 * @author eg
 */
public class ExistInImp {
    static Session session;
    public  ExistInImp()
    {
         session= Controller.getSessionFactory().openSession();
    }
    static Session getSession()
    {
        return session;
    }
    
    public List retrieveCircleUsers(Circle circle)
    {
         Criteria criteria = session.createCriteria(ExistIn.class);
         Criterion userCircle =  Restrictions.eq("circle",circle);           
         criteria =  criteria.add(userCircle);
         List result =  criteria.list();
         return result;  
    }
    
    public List isCircleEmpty(int circleId)
    {
         Criteria criteria = session.createCriteria(ExistIn.class);
         Criterion userCircle =  Restrictions.eq("id.circleId",circleId);           
         criteria =  criteria.add(userCircle);
         List resultList =  criteria.list();
         return resultList;
         
    }
    
    public void deleteExistIn(ExistIn existInObj)
    {
        session.beginTransaction();
        session.delete(existInObj);  
        session.getTransaction().commit(); 
    }
}
