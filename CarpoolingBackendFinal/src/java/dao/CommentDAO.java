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
public class CommentDAO {

    static Session session;
    public CommentDAO()
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
        catch(RuntimeException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean deleteComment(Comment comment)
    {
         try{
            session.beginTransaction();
            session.delete(comment);  
            session.getTransaction().commit();
            return true;
        }
        catch(RuntimeException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
    //oneComment
    public Comment retrieveCommentbyExample(Comment comment)
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
    public Comment retrieveComment(int id)
    {
        Criteria criteria = session.createCriteria(Comment.class)
                .add(Restrictions.eq("id" ,id));
        List l = criteria.list();
        
        if(l.size()>0)
        {
            Comment c=(Comment) l.get(0);
            return c;
        }
        else
            return null;
    }
}
