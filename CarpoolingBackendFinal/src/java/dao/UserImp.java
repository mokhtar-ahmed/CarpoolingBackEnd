/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import pojo.Phone;
import pojo.User;

/**
 *
 * @author Rehab
 */
public class UserImp implements UsersInt{
    static Session session;
    public UserImp()
    {
         session= Controller.getSessionFactory().openSession();
    }
    static Session getSession()
    {
        return session;
    }
    
    public List<User> getUser(Integer id){
        
          Query createQuery = session.createQuery("from User u where u.id = :user ").setString("user", id.toString());
          List<User> u = createQuery.list();
          return u;
    }
    
    @Override
    public void addUser(User user) {
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
    }

    @Override
    public User retrieveUserByUserName(User user) {
        List<User> x;
        String s="from User u where u.username =:username";
        Query q = session.createQuery(s).setString("username",user.getUsername()); 
        x=q.list();
//        session.beginTransaction();
//        session.getTransaction().commit();
        
        if(!x.isEmpty())
            return x.get(0);
        else return null;
    }

    @Override
    public User retrieveUserById(User user) {
        session.beginTransaction();
        List<User> x;
        String s="from User u where u.id =:uid";
        Query q = session.createQuery(s).setInteger("uid",user.getId()); 
        x=q.list();
        session.getTransaction().commit();
        
        if(!x.isEmpty())
            return x.get(0);
        else return null;
    }
    
    public void edit(User user){
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
    }
    
    public ArrayList<User> retrieveRegisteredUsers(ArrayList<String> contactList)
    {
        ArrayList<User> registeredFriends = new ArrayList<User>();
        for(int i=0;i<contactList.size();i++)
        {
            Criteria criteria = session.createCriteria(User.class);
            Criterion userPhone =  Restrictions.eq("phone",contactList.get(i));           
            criteria =  criteria.add(userPhone);
            User selectedUser = (User) criteria.uniqueResult();
            if(selectedUser != null)
            {
             registeredFriends.add(selectedUser);
             System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
             System.out.println("USERNAME"+ "     "+selectedUser.getName());
             System.out.println("USERPHONE" + "           "+selectedUser.getPhone());
            }
        }
        return registeredFriends;
        
    }

    @Override
    public User retrieveUserByPhone(String p) {
//        session.beginTransaction();
        List<User> x;
        String s="from User u where u.phone =:phone";
        Query q = session.createQuery(s).setString("phone",p); 
        x=q.list();
//        session.getTransaction().commit();
        
        if(!x.isEmpty())
            return x.get(0);
        else return null;
    }

    @Override
    public User retrieveUserBymail(String m) {
//        session.beginTransaction();
        List<User> x;
        String s="from User u where u.email =:uid";
        Query q = session.createQuery(s).setString("uid",m); 
        x=q.list();
//        session.getTransaction().commit();
        
        if(!x.isEmpty())
            return x.get(0);
        else return null;
    }

    @Override
    public boolean ifExist(String phone) {
        List<Phone> x;
        String s="from Phone p where p.phone =:ph";
        Query q = session.createQuery(s).setString("ph",phone); 
        x=q.list();
        if(!x.isEmpty())
            return true;
        else return false;
    }
    
    public User retrieveUserByUserNameAndPassword(User user) {
        Criteria criteria = session.createCriteria(User.class);
        Criterion username =  Restrictions.eq("username",user.getUsername());  
        Criterion password =  Restrictions.eq("password",user.getPassword()); 
        criteria =  criteria.add(username);
        criteria =  criteria.add(password);
        User selectedUser = (User) criteria.uniqueResult();
        return selectedUser;
    }

}
