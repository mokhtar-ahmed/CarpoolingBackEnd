/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;


import dao.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.*;


/**
 *
 * @author Nourhan
 */
public class EventUtil {
    
    int id=0;
    Event myEvent = new Event();
    ArrayList<Integer> blockedUsersID = new ArrayList<Integer>();
    
    
    public Event comvertToEventObject(JSONObject eventJson)
    {
        try {
            //id
            if(!eventJson.isNull("idEvent"))
            {
                id =eventJson.getInt("idEvent");
                myEvent.setId(id);
            }
            //user
            if(!eventJson.isNull("user"))
            {
                JSONObject userjson=eventJson.getJSONObject("user");
                myEvent.setUser(convertUser(userjson));
            }
            //location //from
            if(!eventJson.isNull("location"))
            {
            JSONObject locationJson = eventJson.getJSONObject("location");
            myEvent.setLocation(convertLocation(locationJson));
            }
            //eventName
            if(!eventJson.isNull("eventName"))
            {
            String name = eventJson.getString("eventName");
            myEvent.setEventName(name);
            }
            //noOfSlots
            if(!eventJson.isNull("noOfSlots"))
            {
                int slots = eventJson.getInt("noOfSlots");
                myEvent.setNoOfSlots(slots);
            }
            //Date
            if(!eventJson.isNull("eventDate"))
            {
                String d = eventJson.getString("eventDate");
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
                Date date = df.parse(d);
                myEvent.setEventDate(date);
            }
            //eventStatue
            if(!eventJson.isNull("eventStatue"))
            {
                String statue = eventJson.getString("eventStatue");
                myEvent.setEventStatue(statue);
            }
            //to
            if(!eventJson.isNull("eventToLocations") && id!=0)
            {
                JSONArray eventToLocationsJson =eventJson.getJSONArray("eventToLocations");
                myEvent.setEventToLocations(convertEventToLocations(eventToLocationsJson));
            }
            //circles
            if(!eventJson.isNull("cirlclesId") && id!=0)
            {
                //myEvent=deleteOldJoinEvent(myEvent);
                JSONArray circlesId = eventJson.getJSONArray("cirlclesId");
                myEvent.setJoinEvents(convertJoinEvents(circlesId));
                
            }
            
          return myEvent;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
        
    }
    
    
    public Location convertLocation (JSONObject locationJson) 
    {
        
        try {
            Location location = new Location();
            LocationDAO ldao = new LocationDAO();
            if(!locationJson.isNull("idLocation"))
            {
                int locationID =locationJson.getInt("idLocation");
                location = ldao.retrieveLocationById(locationID);
            }
            return location;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public User convertUser(JSONObject userjson)
    {
        try {
            int userID = userjson.getInt("id");
            UserDAO udao = new UserDAO();
            User user = udao.retrieveUserById(userID);
            return user;
        } catch (JSONException ex) {
            Logger.getLogger(EventUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public Set<EventToLocation> convertEventToLocations(JSONArray eventToLocationsJson)
    {
        try {
            JSONObject eJson;
            Set eventToLocations =  new HashSet(0);
            for(int i =0; i<eventToLocationsJson.length();i++)
            {
                 eJson=(JSONObject)eventToLocationsJson.get(i);
                 //EventToLocationId
                 if(!eJson.isNull("toOrder") || !eJson.isNull("location"))
                 {
                     EventToLocation e = new EventToLocation();
                     int oorder =eJson.getInt("toOrder");
                     JSONObject loc = eJson.getJSONObject("location");
                     LocationDAO ldao = new LocationDAO();
                     Location ll = ldao.retrieveLocationById(loc.getInt("id"));
                     e.setToOrder(oorder);
                     e.setEvent(myEvent);
                     e.setLocation(ll);
                     
                     EventToLocationDao etld= new EventToLocationDao();
                     etld.addEventToLocation(e);
                     eventToLocations.add(e);
                 }
            }
            return eventToLocations;
             } catch (JSONException ex) {
                 ex.printStackTrace();
                 return null;
             }
    }
    
    public Set<JoinEvent> convertJoinEvents (JSONArray circlesID)
    {
        Set<JoinEvent> joinEventsSet = new HashSet(0);
        try {
            int circleID;
            JoinEventDAO jedao = new JoinEventDAO();
            for(int i=0;i<circlesID.length();i++)
            {
                circleID =circlesID.getInt(i);
                Circle circle = new CircleDAO().retrieveCircleById(circleID);
                Set existIn =circle.getExistIns();
                Object[] o = existIn.toArray();
                
                for(int j=0;j<existIn.size();j++)
                {
                    ExistIn ei = (ExistIn) o[j];
                    if(ei!=null || !ei.getBolckStatue().equals("blocked"))
                    {
                        int userId = ei.getUser().getId();
                        UserDAO udao = new UserDAO();
                        User user=udao.retrieveUserById(userId);
                        JoinEventId joinEventId = new JoinEventId(id, userId);  
                        JoinEvent joinEvent = new JoinEvent();  
                        joinEvent.setId(joinEventId);
                        
                        joinEvent.setEvent(myEvent);
                        joinEvent.setUser(user);
                        
                        UserStatueDAO usdao = new UserStatueDAO();
                        UserStatue userStatue=usdao.retrieveUserStatueById(1);
                        
                        joinEvent.setUserStatue(userStatue);   
                        
                       jedao.addJoinEvent(joinEvent);
                       
                       joinEventsSet.add(joinEvent);
                       
                        
                    }
                }  
            }
        
        } catch (JSONException ex) {
                ex.printStackTrace();
            }
        
        
        return joinEventsSet;
    }
    
    
    
//    public Event deleteOldJoinEvent(Event event)
//    {
//        EventDAO edao = new EventDAO();
//        JoinEventDAO joinEventDAO = new JoinEventDAO();
//        event=edao.retrieveEvent(event);
//        Set joinEvent  =event.getJoinEvents();
//        for (Iterator it = joinEvent.iterator(); it.hasNext();)
//        {
//         JoinEvent je = (JoinEvent)it.next();
//         boolean b =joinEventDAO.deleteJoinEvent(je);
//            System.out.println(b+"88888888888888888888888888888888888");
//        }
//        joinEvent.clear();
//        event.setJoinEvents(joinEvent);
//        edao.updateEvent(event);
//        return event;
//    }
}
