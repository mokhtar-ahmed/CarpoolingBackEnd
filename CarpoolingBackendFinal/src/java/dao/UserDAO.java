/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import static dao.UserDAO.session;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.*;

/**
 *
 * @author Nourhan
 */



public class UserDAO {
    static Session session;
   
    public UserDAO()
    {
        session = HibernateUtil.getSessionFactory().openSession();
    }
    public  User retrieveUserById(int id)
    {
        try{
        
            
            session.flush();
            Criteria criteria =session.createCriteria(User.class)
                .add(Restrictions.eq("id", id));
            List list =criteria.list();
            User user =(User)list.get(0);
            
           // session.evict(user);
            return user;

        }catch(Exception ex){
          ex.printStackTrace();
        }
        
        return null;
    }
	
    public User retrieveUserbyUsernamePass(String name,String pass)
    {
        User user = null;
       // session.flush();
        Criteria criteria =session.createCriteria(User.class)
                .add(Restrictions.eq("username", name))
                .add(Restrictions.eq("password", pass));
        List list =criteria.list();
        if(list.size()>0)
        {
            user =(User)list.get(0);
        }
        
        return user;
    }
}
