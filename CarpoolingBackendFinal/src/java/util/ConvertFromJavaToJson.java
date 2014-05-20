/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.Date;
import java.util.Set;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.*;

/**
 *
 * @author Nourhan
 */
public class ConvertFromJavaToJson {
    public JSONObject toEventJsonObj(Event myEvent)
    {
        try {
            JSONObject eventJson = new JSONObject();
            //id
            int id               = myEvent.getId();
            eventJson.put("idEvent", id);
            
            //user
            User user            = myEvent.getUser();
            JSONObject userJson = new JSONObject();
            userJson.put("id", user.getId());
            userJson.put("username", user.getUsername());
            eventJson.put("user", userJson);
            
            //from location
            Location location    = myEvent.getLocation();
            JSONObject locationJson = new JSONObject();
            locationJson.put("idLocation", location.getId());
            locationJson.put("address", location.getAddress());
            eventJson.put("location", locationJson);
            
            //name
            String name          = myEvent.getEventName();
            eventJson.put("eventName", name);
            
            //slots
            int slots            = myEvent.getNoOfSlots();
            eventJson.put("noOfSlots", slots);
            
            //date
            Date date            = myEvent.getEventDate();
            eventJson.put("eventDate", date);
            
            //status
            String eventStatue   = myEvent.getEventStatue();
            eventJson.put("eventStatue", eventStatue);
           
//            //notification
//            Set notifications    = myEvent.getNotifications();
//            JSONArray notificationJson = new JSONArray();
//            JSONObject n;
//            Object[] all = notifications.toArray();
//            for(int i =0;i<notifications.size() ;i++)
//            {
//                Notification notification=(Notification)all[i];
//                n = new JSONObject();
//                n.put("id", notification.getId());
//                n.put("userName", notification.getUser().getName());
//                notificationJson.put(i,n);
//            }
//            eventJson.put("notifications", notificationJson);
            //to
            Set eventToLocations = myEvent.getEventToLocations();
            JSONArray eventToLocationsJson = new JSONArray();
            JSONObject e;
            Object[]o=eventToLocations.toArray();
            for(int i =0;i<eventToLocations.size() ;i++)
            {
                EventToLocation eventTo=(EventToLocation)o[i];
                e = new JSONObject();
                e.put("id",eventTo.getLocation().getId());
                e.put("address",eventTo.getLocation().getAddress());
                eventToLocationsJson.put(i,e);
            }
            eventJson.put("eventToLocation", eventToLocationsJson);
            //joinevent
            Set joinEvents       = myEvent.getJoinEvents();
            JSONArray joinEventJson = new JSONArray();
            JSONObject j;
            Object[] allj = joinEvents.toArray();
            for(int i=0;i<joinEvents.size();i++)
            {
                JoinEvent je=(JoinEvent)allj[i];
                j= new JSONObject();
                //j.put( "eventName",je.getEvent().getEventName());
                j.put("userName", je.getUser().getName());
                j.put("userStatue",je.getUserStatue().getStatue());
                joinEventJson.put(i,j);
            }
            eventJson.put("joinEvent", joinEventJson);
            //comments
            Set comments         = myEvent.getComments();
            JSONArray commentJson = new JSONArray();
            JSONObject c ;
            Object[] allc = comments.toArray();
            for(int i=0;i<comments.size();i++)
            {
                Comment comment=(Comment)allc[i];
                c= new JSONObject();
                c.put("text",comment.getCommentText());
                c.put("user",comment.getUser().getName());
                //c.put("event",comment.getEvent().getEventName());
                commentJson.put(i,c);
            }
            eventJson.put("comments", commentJson);
            
            return eventJson;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
 
}
