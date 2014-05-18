/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import org.hibernate.Session;
import pojo.Phone;

/**
 *
 * @author Rehab
 */
public class PhoneImp implements PhoneInt {
    static Session session;
    public PhoneImp()
    {
         session= Controller.getSessionFactory().openSession();
    }
    static Session getSession()
    {
        return session;
    }
    @Override
    public void addPhone(Phone phone) {
//        Session session =Controller.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(phone);
        session.getTransaction().commit();
//        session.close();
    }

    @Override
    public void deletePhone(Phone phone) {
        session.beginTransaction();
        session.delete(phone);
        session.getTransaction().commit();
    }

}
