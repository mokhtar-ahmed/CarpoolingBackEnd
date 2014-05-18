/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import pojo.Event;

/**
 *
 * @author Nourhan
 */
public class EventDAO {

    static Session session;

    public EventDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Event retrieveEvent2(Event event) {
        List l = session.createCriteria(Event.class)
                .add(Example.create(event).ignoreCase())
                .list();

//        Example eventExample = Example.create(event)
//                .ignoreCase().enableLike(MatchMode.ANYWHERE);
//        List l= session.createCriteria(Event.class)
//                .add(eventExample).list();
        if (l.size() > 0) {
            Event e = (Event) l.get(0);
            return e;
        } else {
            return null;
        }
    }

    public Event retrieveEvent(Event event) {
       // Example eventExample = Example.create(event);
        //List l= session.createCriteria(Event.class)
        //      .add(eventExample).list();

        Criteria criteria = session.createCriteria(Event.class)
                .add(Restrictions.eq("id", event.getId()));
        List l = criteria.list();

        if (l.size() > 0) {
            Event e = (Event) l.get(0);

//            session.evict(e);
            return e;
        } else {
            return null;
        }
    }

    public List<Event> retrieveAllEvents(Event event) {
//        Example eventExample = Example.create(event);
//        List<Event> l= session.createCriteria(Event.class)
//                .add(eventExample).list();
        Criteria criteria = session.createCriteria(Event.class)
                .add(Restrictions.eq("eventName", event.getEventName()));
        List<Event> l = criteria.list();
//        
        if (l.size() > 0) {
            return l;
        } else {
            return null;
        }

    }

        public List<Event> retrieveUserEvents(int id)
    {
        Criteria criteria = session.createCriteria(Event.class)
                .add(Restrictions.eq("user.id" ,id));
        List<Event> l = criteria.list();
        return l;
    }


    public boolean updateEvent(Event event) {
        try {
            session.beginTransaction();
            session.saveOrUpdate(event);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            System.out.println("------------- my exception --------------------");
            ex.printStackTrace();
            return false;
        }

    }

    public boolean addEvent(Event event) {
        try {
            session.beginTransaction();
            session.persist(event);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
