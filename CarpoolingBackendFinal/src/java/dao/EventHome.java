
package dao;

import java.util.*;
import org.hibernate.Query;
import org.hibernate.Session;
import pojo.*;

public class EventHome {


    Session session; 
    public EventHome(){
    
        session = new HibernateUtil().getSessionFactory().openSession();
        
    }
    
    public Event getEventById(int eventId) {
        return (Event) session.get(Event.class, eventId);
    }
    public List<Event> searchEventByDriver(User user){
        
          Query createQuery = session.createQuery("from Event v where v.user = :user ").setEntity("user", user);
          List<Event> u = createQuery.list();
          return u;
    }
    public List<Event> searchEventByFromLocation(Location location){
        
          Query createQuery = session.createQuery("from Event v where v.location = :location ").setEntity("location", location);
          List<Event> u = createQuery.list();
          return u;
    }
      public List<Event> searchEventByToLocations(Location location){
        
          Query createQuery = session.createQuery("select event from EventToLocation ev inner join ev.event where ev.location = :location ").setEntity("location", location);
          List<Event> u = createQuery.list();
          return u ;
    }
    
}
