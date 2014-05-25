/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mytest;


import dao.EventDAO;
import dao.LocationDAO;
import dao.UserDAO;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import webservices.CommentManagement;
import webservices.EventManagement;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.*;
import webservices.LocationManagement;
import webservices.UserManagement;

/**
 *
 * @author Nourhan
 */
public class TestServices {
     
    //retrieve Event Test
//    public static void main(String args[])
//      {
//        try {
//            System.out.println("**************************************");
//            EventManagement event = new EventManagement();
////            Event e = new Event();
////            e.setId(11);
//            JSONObject js = new JSONObject();
//            System.out.println("********************2******************");
//            js.put("idEvent", 7);
//            String j =event.retrieveEvent(js.toString());
//            System.out.println(js);
//            System.out.println("*******************33333333333333*******************");
//            System.out.println(j);
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//        }
//        
    
    //retrieve all users's Events test
//      public static void main(String args[])
//      {
//        try {
//            EventManagement event = new EventManagement();
//            Event e = new Event();
//            e.setId(1);
//            JSONObject js = new JSONObject();
//            js.put("userId", 11);
//            System.out.println(js);
//            String j =event.retrieveAllUserEvents(js.toString());
//            if(j!=null)
//                System.out.print(j);
//            
//        } catch (JSONException ex) {
//              ex.printStackTrace();
//          }
//        
//    }
    
    //delete Event Test
//      public static void main(String args[]){
//          try {
//              
//              JSONObject delete = new JSONObject();
//              delete.put("idEvent", 15);
//              EventManagement e = new EventManagement();
//              System.out.println(delete.toString());
//              String j =e.markAsDeletedEvent(delete.toString());
//             
//              System.out.println(j.toString());
//              
//          } catch (JSONException ex) {
//              Logger.getLogger(TestServices.class.getName()).log(Level.SEVERE, null, ex);
//          }
//          
//      }
    
	
	
    //retrieve event comments
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
    public static void main(String args[])
    {
        JSONObject j = new JSONObject();
        try {    
            j.put("idEvent",1);
            
            j.put("eventName", "Arab Mall");
            
            j.put("noOfSlots", 4);
            
            j.put("eventStatue", "updated");
            
            String d ="25/01/2015 00:02:00.0";
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
            System.out.println(j.toString());
            EventManagement eventManagement = new EventManagement();
            String out =eventManagement.updateEvent(j.toString());
            System.out.println(out);
            
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
    

//add event Test ***************************************************************************
    
//    public static void main(String args[])
//    {
//        JSONObject j = new JSONObject();
//        try {
//            j.put("eventName", "iiiiiiiiti");
//            
//            j.put("noOfSlots", 5);
//            
//            j.put("eventStatue", "new");
//            
//            String d ="27/02/2025 20:22:00.0";
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
//            user.put("id", 2);
//            j.put("user", user);
//            
//            JSONArray eventToLocation = new JSONArray();
//            for(int i=0; i<5;i++)
//            {
//                JSONObject eventLocationJson = new JSONObject();
//                eventLocationJson.put("toOrder", i+1);
//                JSONObject locJson = new JSONObject();
//                locJson.put("id", i+1);
//                eventLocationJson.put("location", locJson);
//                eventToLocation.put(i, eventLocationJson);
//            }
//            j.put("eventToLocations", eventToLocation);
//            
//            JSONArray circlesid = new JSONArray();
//           
//            circlesid.put(0, 1);
//            circlesid.put(1, 2);
//            circlesid.put(2, 3);
//            circlesid.put(3, 5);
//            
//            j.put("cirlclesId", circlesid);
//            
//            JSONArray blocked = new JSONArray();
//            blocked.put(0,4);
//            blocked.put(1,5);
//            blocked.put(2,2);
//            
//            j.put("blockUsers", blocked);
//            
//            System.out.println(j.toString());
//            EventManagement eventManagement = new EventManagement();
//            String out =eventManagement.addEvent(j.toString());
//             System.out.println(out);
//            
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//    }
   
    //test add comment
//    public static void main(String args[])
//    {
//        JSONObject j = new JSONObject();
//        try {
//            j.put("eventName", "iti");
//            
//            j.put("noOfSlots", 4);
//            
//            j.put("eventStatue", "new");
//            
//            String d ="27/01/2015 00:02:00.0";
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
//            
//            JSONArray circlesid = new JSONArray();
//           
//            circlesid.put(0, 1);
//            //circlesid.put(1, 2);
//            //circlesid.put(2, 3);
//            
//            j.put("cirlclesId", circlesid);
//            
//            EventManagement eventManagement = new EventManagement();
//            String out =eventManagement.addEvent(j.toString());
//             System.out.println(out);
//            
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//    }

    
    //deleteComment
//    public static void main(String args[])
//    {
//        try {
//            JSONObject comment = new JSONObject();
//            comment.put("id", 5);
//            
//            CommentManagement cm= new CommentManagement();
//            String j=cm.deleteComment(comment.toString());
//            System.out.println(j);
//        } catch (JSONException ex) {
//            Logger.getLogger(TestServices.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
    

    //retrieveAllLocations
//    public static void main(String args[])
//    {
//        LocationManagement locationManagement = new LocationManagement();
//        
//        String input=locationManagement.retrieveAllLocation();
//            
//            System.out.println(input);
//    }
    
        //user
//        public static void main(String args[])
//        {
//            try {
//                
//                UserManagement u = new UserManagement();
//                JSONObject j = new JSONObject();
//                j.put("name", "noura");
//                j.put("password", "123");
//                
//                JSONObject user =u.logIn(j.toString());
//                System.out.println("++++++++++++++++++++++++++++++++");
//                System.out.println(user);
//                System.out.println("++++++++++++++++++++++++++++++++");
//                
//            } catch (JSONException ex) {
//                ex.printStackTrace();
//            }
//        }
//    

    
    //joine event
//    public static void main(String args[])
//    {
//        try {
//            UserManagement u = new UserManagement();
//            JSONObject j = new JSONObject();
//            j.put("eventId",2 );
//            j.put("userId", 1);
//            
//            String s =u.joinEvent(j.toString());
//            System.out.println(s);
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//    }
    
    
    
    
    //canceld Event Test
//      public static void main(String args[]){
//          try {
//              
//              JSONObject delete = new JSONObject();
//              delete.put("idEvent", 1);
//              EventManagement e = new EventManagement();
//              System.out.println(delete.toString());
//              String j =e.cancelEvent(delete.toString());
//              System.out.println(j.toString());
//              
//          } catch (JSONException ex) {
//              ex.printStackTrace();
//          }
//          
//      }
//    
    
    
    
    //accept request
//    public static void main(String args[])
//    {
//        try {
//            UserManagement u = new UserManagement();
//            JSONObject j = new JSONObject();
//            j.put("eventId",7);
//            j.put("userId", 70);
//            System.out.println(j);       
//            String s =u.AcceptRequest(j.toString());
//            System.out.println(s);
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//    }

    //reject
//    public static void main(String args[])
//    {
//        try {
//            UserManagement u = new UserManagement();
//            JSONObject j = new JSONObject();
//            j.put("eventId",7 );
//            j.put("userId", 6);
//            System.err.println(j);
//            String s =u.RejectRequest(j.toString());
//            System.out.println(s);
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//    }
    
    
    //receve Notification
//    public static void main(String args[])
//    {
//        try {
//            UserManagement u = new UserManagement();
//            JSONObject j = new JSONObject();
//            j.put("notificationId",96 );
//            System.out.println(j);
//            String s =u.ReceveNotification(j.toString());
//            System.out.println(s);
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//    }

//    public static void main(String args[])
//    {
//        try {     
//            EventManagement u = new EventManagement();
//            JSONObject j = new JSONObject();
//            j.put("eventId",88);
//            System.out.println(j.toString());
//             String s =u.wantToJoinUsers(j.toString());
//            System.out.println(s);
//            
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//        
//    }

//    public static void main(String args[])
//    {
//        try {             
//            UserManagement u = new UserManagement();
//            JSONObject j = new JSONObject();
//            
//            j.put("circleId",2);
//            j.put("userId",7);
//
//            System.out.println(j.toString());
//            String s =u.blockUser(j.toString());
//            System.out.println(s);
//
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//    }
}
