/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mytest;


import dao.LocationDAO;
//import dao.UserDAO;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import webservices.CommentManagement;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.*;

/**
 *
 * @author Nourhan
 */
public class TestServices {
     
    //retrieve Event Test
//    public static void main(String args[])
//      {
//        try {
//            EventManagement event = new EventManagement();
//            Event e = new Event();
//            e.setId(1);
//            JSONObject js = new JSONObject();
//            js.put("idEvent", 1);
//            JSONObject j =event.retrieveEvent(js.toString());
//            System.out.println(j);
//        } catch (JSONException ex) {
//            Logger.getLogger(TestServices.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        }
        
    //retrieve all users's Events test
//      public static void main(String args[])
//      {
//        try {
//            EventManagement event = new EventManagement();
//            Event e = new Event();
//            e.setId(1);
//            JSONObject js = new JSONObject();
//            js.put("userId", 1);
//            JSONArray j =event.retrieveAllUserEvents(js.toString());
//            if(j!=null)
//                System.out.print(j);
//            
//        } catch (JSONException ex) {
//              Logger.getLogger(TestServices.class.getName()).log(Level.SEVERE, null, ex);
//          }
//        
//    }
    
    
    
//    public static void main(String args[])
//    {
//        try {
//            EventManagement event = new EventManagement();
//            JSONObject j = new JSONObject();
//            j.put("eventId", 1);
//            j.put("userId", 2);
//            
//            String input =j.toString();
//            boolean b=event.joinEvent(input);
//            
//            System.err.println(b);
//        } catch (JSONException ex) {
//            Logger.getLogger(TestServices.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//    }
    
    //delete Event Test
//      public static void main(String args[]){
//          try {
//              
//              JSONObject delete = new JSONObject();
//              delete.put("idEvent", 1);
//              EventManagement e = new EventManagement();
//              JSONObject j =e.markAsDeletedEvent(delete.toString());
//             
//              System.out.println(j.toString());
//              
//          } catch (JSONException ex) {
//              Logger.getLogger(TestServices.class.getName()).log(Level.SEVERE, null, ex);
//          }
//          
//      }
    
    
    /*public static void main(String args[])
    {
        try {
            CommentManagement c = new CommentManagement();
            JSONObject j = new JSONObject();
            j.put("idEvent", 1);
            
            JSONObject output =c.retrieveEventComments(j.toString());
            System.out.println(output.toString());
            
            
        } catch (JSONException ex) {
            Logger.getLogger(TestServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }*/
    
    
    //update Event Test
//    public static void main(String args[])
//    {
//        JSONObject j = new JSONObject();
//        try {
//            
//            j.put("idEvent",1);
//            
//            j.put("eventName", "iti");
//            
//            j.put("noOfSlots", 2);
//            
//            j.put("eventStatue", "ok");
//            
//            String d ="02/27/2015";
//            j.put("eventDate", d);
//            
//            LocationDAO ldao = new LocationDAO();
//            Location l = ldao.retrieveLocationById(1);
//            int locationId=l.getId();
//            String address = l.getAddress();
//            JSONObject loc = new JSONObject();
//            loc.put("idLocation", locationId);
//            loc.put("address", address);
//            j.put("location", loc);
//            
//            JSONObject user = new JSONObject();
//            user.put("id", 1);
//            j.put("user", user);
//            
//            JSONArray eventToLocation = new JSONArray();
//            for(int i=0; i<3;i++)
//            {
//                System.out.println(i);
//                JSONObject eventLocationJson = new JSONObject();
//                eventLocationJson.put("toOrder", i+1);
//                JSONObject locJson = new JSONObject();
//                locJson.put("id", i+1);
//                eventLocationJson.put("location", locJson);
//                eventToLocation.put(i, eventLocationJson);
//            }
//            j.put("eventToLocations", eventToLocation);
//            EventManagement eventManagement = new EventManagement();
//            JSONObject out =eventManagement.updateEvent(j.toString());
//            System.out.println(out);
//            
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//    }
//    
    
    
    
}
