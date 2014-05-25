/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mytest;

import dao.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.*;
import webservices.EventManagement;

/**
 *
 * @author Nourhan
 */
public class TestDao {
    
//        public static void main(String args[])
//    {
//        Event e = new Event();
//        //e.setEventName("iti");
//        e.setId(1);
//        EventDAO edao = new EventDAO();
////        e = edao.retrieveEvent(e);
//        System.out.println(e);
//        e.setEventDate(new Date(2015-1900, 11, 12));
//        e.setNoOfSlots(0);
//        JoinEvent j=(JoinEvent) e.getJoinEvents().iterator().next();
//        UserStatueDAO usd = new UserStatueDAO();
//        UserStatue us=usd.retrieveUserStatueById(3);
//        j.setUserStatue(us);    
//        e.getJoinEvents().add(e);
//        boolean b=edao.updateEvent(e);
//        System.out.println(b);
//    }
    
//    
//    public static void main(String args[])
//    {
//        Event e = new Event();
//        e.setEventName("iti");
//        EventDAO edao = new EventDAO();
//        List l = edao.retrieveAllEvents(e);
//        for(int i=0; i<l.size();i++)
//        System.out.println(l.get(i));
//    }    
        
       public static void main(String args[])
       { JSONObject j = new JSONObject();
        try {    
            j.put("idEvent",1);
            
            j.put("eventName", "Arab Mall");
            
            j.put("noOfSlots", 1);
            
            j.put("eventStatue", "updated");
            
            String d ="08/05/2015 00:02:00.0";
            j.put("eventDate", d);
            
            LocationDAO ldao = new LocationDAO();
            Location l = ldao.retrieveLocationById(1);
            int locationId=l.getId();
            String address = l.getAddress();
            JSONObject loc = new JSONObject();
            loc.put("idLocation", locationId);
            loc.put("address", address);
            j.put("location", loc);
            
            JSONObject user = new JSONObject();
            user.put("id", 1);
            j.put("user", user);
            
            JSONArray eventToLocation = new JSONArray();
            for(int i=0; i<3;i++)
            {
                System.out.println(i);
                JSONObject eventLocationJson = new JSONObject();
                eventLocationJson.put("toOrder", i+1);
                JSONObject locJson = new JSONObject();
                locJson.put("id", i+1);
                eventLocationJson.put("location", locJson);
                eventToLocation.put(i, eventLocationJson);
            }
            j.put("eventToLocations", eventToLocation);
            
            EventManagement eventManagement = new EventManagement();
            String out =eventManagement.updateEvent(j.toString());
            System.out.println(out);
            
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
       }
}
