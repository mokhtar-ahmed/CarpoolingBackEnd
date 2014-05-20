/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import dao.EventToLocationDAO;
import dao.JoinEventDAO;
import dao.LocationDAO;
import dao.UserDAO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.Event;
import pojo.EventToLocation;
import pojo.EventToLocationId;
import pojo.JoinEvent;
import pojo.Location;
import pojo.User;

/**
 *
 * @author Nourhan
 */
public class ConvertFromJsonToJavaUpdate {
    Event myEvent = new Event();
    public Event jsonToJava(JSONObject eventJson,Event myEvent)
    {
        this.myEvent=myEvent;
       try {
            //id
            if(!eventJson.isNull("idEvent"))
            {
                int id =eventJson.getInt("idEvent");
                myEvent.setId(id);
            }
            //location //from
            if(!eventJson.isNull("location"))
            {
                JSONObject locationJson = eventJson.getJSONObject("location");
                myEvent.setLocation(convertLocation(locationJson));
            }
            //user
            if(!eventJson.isNull("user"))
            {
                JSONObject userjson=eventJson.getJSONObject("user");
                myEvent.setUser(convertUser(userjson));
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
                boolean b = checkNumberOfSlots(slots,myEvent.getId());
                if(b)
                    myEvent.setNoOfSlots(slots);
                
                // handly el case de wasaly l l driver en el slots 2a2al mn l nas l 3mla join menf3sh
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
            if(!eventJson.isNull("eventToLocations"))
            {
                JSONArray eventToLocationsJson =eventJson.getJSONArray("eventToLocations");
                myEvent.setEventToLocations(convertEventToLocations(eventToLocationsJson));
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
            ex.printStackTrace();
            return null;
        }
    }
    
    public Set<EventToLocation> convertEventToLocations(JSONArray eventToLocationsJson)
    {
        Set eventToLocations =  new HashSet(0);
        try {
            JSONObject eJson;
            EventToLocationDAO etld;
            for(int i =0; i<eventToLocationsJson.length();i++)
            {
                etld= new EventToLocationDAO();
                eJson=(JSONObject)eventToLocationsJson.get(i);
                 //EventToLocationId
                if(!eJson.isNull("toOrder") || !eJson.isNull("location"))
                {
                    EventToLocation e = new EventToLocation();
                    int oorder =eJson.getInt("toOrder");
                    JSONObject loc = eJson.getJSONObject("location");
                    int locationID =loc.getInt("id");
                    LocationDAO ldao = new LocationDAO();
                    Location ll = ldao.retrieveLocationById(locationID);
                    e.setToOrder(oorder);
                    e.setEvent(myEvent);
                    e.setLocation(ll);
                    e.setId(new EventToLocationId(myEvent.getId(), locationID));
                    
                    
                    EventToLocation eventLocation=etld.retrieveEventToLocation(e);
                    
                    if(eventLocation==null)
                    {
                        etld.addEventToLocation(e);
                        eventToLocations.add(e);
                    }
                    else
                    {
                        etld.deleteEventToLocation(eventLocation);
                        etld.addEventToLocation(e);
                        eventToLocations.add(e);
                    }
                 }
            }
            return eventToLocations;
             } catch (JSONException ex) {
                 ex.printStackTrace();
                 return null;
             }
    }

    private boolean checkNumberOfSlots(int slots , int eventId) 
    {
        int num = new ApplicatipnUtil().numOfAttendUsers(eventId);
        if(slots >=num)
            return true;
        else
            return false;
    }

}
