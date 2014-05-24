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
import java.util.Iterator;
import java.util.Set;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.*;

/**
 *
 * @author Nourhan
 */

public class ConvertFromJsonToJava {
    
    int id=0;
    Event myEvent = new Event();
    public Event jsonToJavaAdd(JSONObject eventJson)
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
            if(!eventJson.isNull("cirlclesId") && id!=0 )
            {            
                //myEvent=deleteOldJoinEvent(myEvent);
                JSONArray circlesId = eventJson.getJSONArray("cirlclesId");
                JSONArray blockUsers = new JSONArray();
                if(!eventJson.isNull("blockUsers"))
                    blockUsers = eventJson.getJSONArray("blockUsers");

                myEvent.setJoinEvents(convertJoinEvents(circlesId ,blockUsers));   
            }
            //////////////////// el goz2 bta3 nafs el time menf3sh e add event f nafs el wa2t
            if(new ApplicatipnUtil().checkIfDateExsit(myEvent.getEventDate(), myEvent.getUser().getId()) && id==0)
                return null;
            //////////////////////
          return myEvent;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private User convertUser(JSONObject userjson) 
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
        Set eventToLocations =  new HashSet(0);
        try {
            JSONObject eJson;
            for(int i =0; i<eventToLocationsJson.length();i++)
            {
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
                     EventToLocationDAO etld= new EventToLocationDAO();
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
      public Set<JoinEvent> convertJoinEvents (JSONArray circlesID , JSONArray blockUsers)
    {
        Set<JoinEvent> joinEventsSet = new HashSet(0);

        JoinEventDAO jedao = new JoinEventDAO();
        UserStatueDAO usdao = new UserStatueDAO();
        
        try {
            int circleID;
            for(int i=0;i<circlesID.length();i++)
            {
                circleID =circlesID.getInt(i);
                Circle c = new CircleDAO().retrieveCircleById(circleID);
                if(c!=null)
                {
                    Set<User> users = new NewEventUtil().getUsersExistInCircle(circleID);
                    if(users!=null)
                    {
                        for (Iterator it = users.iterator(); it.hasNext();)
                        {
                            User user =(User)it.next();
                            int blockUserID;
                            boolean blocked=false;
                            for(int j=0;j<blockUsers.length();j++)
                            {
                                blocked =false;
                                blockUserID=blockUsers.getInt(j);
                                if(user.getId()==blockUserID)
                                {
                                    blocked=true;
                                    break;
                                }
                            }
                            if(!blocked)
                            {
                                JoinEvent joinEvent = new JoinEvent();  
                                UserStatue userStatue=usdao.retrieveUserStatueById(1);
                                JoinEventId joinEventId = new JoinEventId(id, user.getId());  
                    
                                joinEvent.setId(joinEventId);
                                joinEvent.setEvent(myEvent);
                                joinEvent.setUser(user);
                                joinEvent.setUserStatue(userStatue);   
                    
                                jedao.addJoinEvent(joinEvent);// add in database
                                joinEventsSet.add(joinEvent); //add in set
                            }
                        }
                    }
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return joinEventsSet;
    }
}