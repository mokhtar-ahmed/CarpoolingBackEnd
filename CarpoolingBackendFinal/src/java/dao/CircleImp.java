/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pojo.Circle;
import pojo.ExistIn;
import pojo.ExistInId;
import pojo.User;

/**
 *
 * @author Rehab
 */
public class CircleImp implements CircleInt{
    static Session session;
    public CircleImp()
    {
         session= Controller.getSessionFactory().openSession();
    }
    static Session getSession()
    {
        return session;
    }
    
    @Override
    public void addCircle(Circle circle) {
        session.beginTransaction();
        session.persist(circle);
        session.getTransaction().commit();
    }

    @Override
    public List<Circle> retrieveUserCircles(User user){
        /*Session session=Controller.sessionFactory.openSession();
        String str1 = "from User u, Circle c , ExistIn e where u.id=e.user.id and c.id=e.circle.id";
        
        Query q1 = session.createQuery(str1);//.setInteger("uid", user.getCircles().);
        List<Circle> list1 = q1.list();
        for (int i = 0; i < list1.size(); i++) {
            Circle c = list1.get(i);
            System.out.println("user "+ i + "= " + c.getCircleName());
        }
        return list1;*/
//        Session session=Controller.getSessionFactory().openSession();
        String str1 = "from Circle c where c.user.id =:uid";
        Query q1 = session.createQuery(str1).setInteger("uid", user.getId());
        List<Circle> list1 = q1.list();
        return list1;
    }

    @Override
    public void editCircle(Circle circle) {
        session.beginTransaction();
        session.merge(circle);
        session.getTransaction().commit();
    }

    @Override
    public void deleteCircle(Circle c) {
//        Circle c=retrieveCircleById(circle);
//        emptyCircle(circle);
        session.beginTransaction();
        session.delete(c);
        session.getTransaction().commit();
        
    }
    
    
    @Override
    public void emptyCircle(Circle circle) {
        session.beginTransaction();
        String s="delete from ExistIn e where e.circle.id =:cid ";
        Query q=session.createQuery(s).setInteger("cid", circle.getId());
        session.getTransaction().commit();
    }

    @Override
    public void blockUserFromCircle(ExistIn ex) {
        session.beginTransaction();
        ex.setBolckStatue("open");
        session.persist(ex);
        session.getTransaction().commit();
    }

    @Override
    public boolean isBlocked(ExistIn ex) {
        session.beginTransaction();
        if(ex.getBolckStatue().equals("open"))
        return true;
        return false;
    }

    @Override
    public void addUserToCircle(ExistIn e) {
        session.beginTransaction();
        session.persist(e);
        session.getTransaction().commit();
    }
    
    @Override
    public void updateCircle(Circle circle,User user) {
        //needed to be seen 
        session.beginTransaction();
        ExistIn ex=new ExistIn(user, circle, "open");
        session.saveOrUpdate(ex);
        session.getTransaction().commit();
    }

    @Override
    public void removeUserFromCircle(ExistIn e) {
        e=retrieveExistInUser(e);
        session.beginTransaction();
        session.delete(e);
        session.getTransaction().commit();
    }

    @Override
    public Circle retrieveCircleByName(User user, Circle circle) {
        return null;
        
    }

    @Override
    public List<User> retrieveCircleUsers( Circle circle) {
//        String str1 = "from Circle c , ExistIn e where c.idCircle=e.circle.idCircle";
        String str1="SELECT u FROM User u , ExistIn e , Circle c where e.circle.id = c.id and e.user.id = u.id and c.id =:cid";
        Query q1 = session.createQuery(str1).setInteger("cid", circle.getId());
        List<User> list1=q1.list();
//        List<ExistIn> list1 = q1.list();
//        List<Users> list2 = null;
//        for (int i = 0; i < list1.size(); i++) {
//            System.out.println(list1.get(i).getUsers().getName());
//            //list2.add(list1.get(i).getUsers());
//        }
        return list1;
    }

    @Override
    public Circle retrieveCircleById(Circle circle) {
        //Session session=Controller.getSessionFactory().openSession();
        List<Circle> x;
        String s="from Circle c where c.id =:id";
        Query q = session.createQuery(s).setInteger("id",circle.getId()); 
        x=q.list();
//        session.beginTransaction();
//        session.getTransaction().commit();
        //session.close();
        if(!x.isEmpty())
            return x.get(0);
        else return null;
    }

    @Override
    public ExistIn retrieveExistInUser(ExistIn e) {
        List<ExistIn> x;
        String s="from ExistIn e where e.user.id =:uid and e.circle.id =:cid";
        Query q = session.createQuery(s).setInteger("uid",e.getUser().getId()).setInteger("cid", e.getCircle().getId()); 
        x=q.list();
        session.beginTransaction();
        session.getTransaction().commit();
        //session.close();
        if(!x.isEmpty())
            return x.get(0);
        else return null;
    }

    @Override
    public Circle retrieveCircleByUserIdAndCircleName(Circle circle) {
//        List<Circle> x;
//        String s="from Circle c where c.circleName =:circleName and c.user.id =:id";
//        Query q = session.createQuery(s).setString("circleName", circle.getCircleName()).setInteger("id",circle.getUsers().getId()); 
//        x=q.list();
//        session.beginTransaction();
//        session.getTransaction().commit();
//        //session.close();
//        if(!x.isEmpty())
//            return x.get(0);
//        else return null;
        
        return circle;
    }

    @Override
    public Circle retrieveUserCircleByName(int userId, String name) {
        List<Circle> x;
        String s="from Circle c where c.user.id =:uid and c.circleName =:cName";
        Query q = session.createQuery(s).setInteger("uid",userId).setString("cName", name); 
        x=q.list();
//        session.beginTransaction();
//        session.getTransaction().commit();
        //session.close();
        if(!x.isEmpty())
            return x.get(0);
        else return null;
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
