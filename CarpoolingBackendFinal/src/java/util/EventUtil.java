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
import java.util.Date;
import java.util.HashSet;
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
    
    int id;
    Event myEvent = new Event();
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
            //location
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
                System.out.println(eventJson.getString("eventDate"));
                String d = eventJson.getString("eventDate");
                DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
                Date date = df.parse(d);
                myEvent.setEventDate(date);
            }
            //eventStatue
            if(!eventJson.isNull("eventStatue"))
            {
                String statue = eventJson.getString("eventStatue");
                myEvent.setEventStatue(statue);
            }
            if(!eventJson.isNull("eventToLocations"))
            {
                JSONArray eventToLocationsJson =eventJson.getJSONArray("eventToLocations");
                myEvent.setEventToLocations(convertEventToLocations(eventToLocationsJson));
            }
            
          return myEvent;
        } catch (JSONException ex) {
            System.out.println("Json");
            ex.printStackTrace();
            return null;
        } catch (ParseException ex) {
            System.out.println("dare");
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
                     System.out.println(e);
                     eventToLocations.add(e);
                 }
            }
            return eventToLocations;
             } catch (JSONException ex) {
                 ex.printStackTrace();
                 return null;
             }
    }
    public User convertUser(JSONObject userjson)
    {
        try {
            int userID = userjson.getInt("id");
            UserImp udao = new UserImp();
            User u=new User();
            u.setId(userID);
            User user = udao.retrieveUserById(u);
            return user;
        } catch (JSONException ex) {
            Logger.getLogger(EventUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Set<JoinEvent> convertJoinEvents (JSONArray joinEventJson)
    {
        try {
            JoinEvent j = new JoinEvent();
            JSONObject jJson;
            Set joinEvents = new HashSet(0);
            for(int i=0; i<joinEventJson.length();i++)
            {
                jJson=(JSONObject)joinEventJson.get(i);
                //JoinEventId
                JSONObject idJsonObj = jJson.getJSONObject("id");
                int eventId =idJsonObj.getInt("eventId");
                int usersId =idJsonObj.getInt("usersId");
                JoinEventId joinEventID=new JoinEventId(eventId, usersId);
                j.setId(joinEventID);
                //UserStatue
                JSONObject userStatueObj =jJson.getJSONObject("userStatue");
                int userStatueId =userStatueObj.getInt("id");
                String userStatue =userStatueObj.getString("statue");
                UserStatue u = new UserStatue(userStatue);
                u.setId(userStatueId);
                j.setUserStatue(u);
                joinEvents.add(j);
            }
            return joinEvents;
            } catch (JSONException ex) {
                Logger.getLogger(EventUtil.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            
            
    }
}
