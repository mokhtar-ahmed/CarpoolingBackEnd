/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import pojo.*;

/**
 *
 * @author Nourhan
 */
public class CommentDAo {

    static Session session;
    public CommentDAo()
    {
        session = HibernateUtil.getSessionFactory().openSession();
    }
    public boolean addComment(Comment comment)
    {
         try{
            session.beginTransaction();
            session.persist(comment);  
            session.getTransaction().commit();
            return true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
    
    public Comment retrieveComment(Comment comment)
    {
        List l = session.createCriteria(Comment.class)
                .add( Example.create(comment).ignoreCase())
                .list();
           if(l.size()>0)
        {
            Comment c=(Comment) l.get(0);
            return c;
        }
        else
            return null;
    }
        
     public boolean deleteComment(Comment comment)
    {
         try{
            session.beginTransaction();
            session.delete(comment);  
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
