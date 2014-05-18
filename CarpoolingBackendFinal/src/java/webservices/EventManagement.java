
package webservices;

import PushNotificationUtil.MessageUtil;
import PushNotificationUtil.Messages;
import dao.*;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.*;
import util.ConverObjectToJsonEvent;
import util.EventUtil;


/**
 *
 * @author Nourhan
 */
@Path("/event")
public class EventManagement {
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getEvent")
    public JSONObject retrieveEvent(JSONObject input)//finshed
    {
           JSONObject eventJson = new JSONObject();
           
           System.out.println("Enter to get event *********************************");
        try {
            
            Event myEvent = new Event();
            myEvent.setId(input.getInt("idEvent"));

            myEvent= new EventDAO().retrieveEvent(myEvent);
             
            if(myEvent!= null){
                
                ConverObjectToJsonEvent convert = new ConverObjectToJsonEvent();
                eventJson=convert.toEventJson(myEvent);
                  eventJson.put("Message", "success");
            }
            else
               eventJson.put("Message", "cann't retrieve the event details");
            
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        
            System.out.println(eventJson.toString());
        
            return  eventJson;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/retrieveAllUserEvents")
    public JSONArray retrieveAllUserEvents(String input)//finshed
    {
        try {
            
            
            JSONObject myUser = new JSONObject(input);
            
            int userId= myUser.getInt("userId");
            
            User user= new UserDAO().retrieveUserById(userId);
            Set events =user.getJoinEvents();
            
            Iterator it = events.iterator();
            
            JSONArray eventJson = new JSONArray();
            
        
            while(it.hasNext()){
                
                Event myEvent= ((JoinEvent)it.next()).getEvent();
                
                String check = myEvent.getEventStatue();
                
                if(!check.equals("deleted") || !check.equals("canceled"))
                {
                   JSONObject  eventJsonObj = new JSONObject();
                   
                    eventJsonObj.put("idEvent", myEvent.getId());
                    eventJsonObj.put("eventName", myEvent.getEventName());
                    eventJsonObj.put("eventDate", myEvent.getEventDate());
                    eventJsonObj.put("userStatue", "create");
                    eventJson.put(eventJsonObj);
                
                }
            }
            
            return eventJson;
        } catch (JSONException ex) {
            return null;
        }
    }
    
      @POST
      @Produces(MediaType.APPLICATION_JSON)
      @Consumes(MediaType.APPLICATION_JSON)
      @Path("/getUserJoinEvents")
      public String getAllUserJoinEvents(JSONObject input){ 
           
          JSONArray outputEvents = new JSONArray();
           
           try {
               
                int userId= input.getInt("userId");
                System.out.println("user id at getAllUserJoinEvents  = " +userId);
                
                User user = new UserDAO().retrieveUserById(userId);
                Iterator it =  user.getJoinEvents().iterator();
       
            while(it.hasNext()){

                Event ev = ((JoinEvent)it.next()).getEvent();

                System.out.println(ev.getEventName());

                JSONObject ob = new JSONObject();
                
                ob.put("id",ev.getId());
                ob.put("name",ev.getEventName());
                ob.put("date",ev.getEventDate().toString());
       
                outputEvents.put(ob);
             }
           
               } catch (JSONException ex) {
                        ex.printStackTrace();
               }
            return outputEvents.toString();
      }
       
      @POST
      @Produces(MediaType.APPLICATION_JSON)
      @Consumes(MediaType.APPLICATION_JSON)
      @Path("/markAsDeletedEvent")
      public JSONObject markAsDeletedEvent(String input) //Finshed
      {
        try {
            JSONObject event = new JSONObject(input);
            int idEvent=event.getInt("idEvent");//get from json
            Event myEvent = new Event();
            myEvent.setId(idEvent);
            EventDAO edao = new EventDAO();
            myEvent=edao.retrieveEvent(myEvent);//retrieve event
            myEvent.setEventStatue("deleted");
            boolean b= edao.updateEvent(myEvent);//updat it
            JSONObject deleted = new JSONObject();
            JSONObject jsonOutput = new JSONObject();
            if(b)
            {
                deleted.put("deleted", "true");
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",deleted);
                return jsonOutput;
            }else
            {
                deleted.put("deleted", "false");
                jsonOutput.put("HasError", true);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",deleted);
                return jsonOutput;
            }
        } catch (JSONException ex) {
            return null;
        }
      }
      @POST
      @Produces(MediaType.APPLICATION_JSON)
      @Consumes(MediaType.APPLICATION_JSON)
      @Path("/updateEvent")
      public JSONObject updateEvent(String input) //finshed
      {
        try {
            JSONObject event = new JSONObject(input);
            EventUtil eventUtil = new EventUtil();
            Event myEvent = eventUtil.comvertToEventObject(event);
            EventDAO edao = new EventDAO();
            boolean b =edao.updateEvent(myEvent);
            System.out.println(b);
            JSONObject updated = new JSONObject();
            JSONObject jsonOutput = new JSONObject();
            if(b)
            {
                updated.put("updated", true);
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",updated);
                
                
               Set joinEvent =myEvent.getJoinEvents();
               String driverName=myEvent.getUser().getName();
               String eventName=myEvent.getEventName();
               
               for (Iterator it = joinEvent.iterator(); it.hasNext();) 
               {
                   
                   JoinEvent je = (JoinEvent)it.next();
                   Notification n = new Notification(je.getUser(), je.getEvent(), new Date(),"send","updated");
                   NotificationDAO ndao = new NotificationDAO();
                   ndao.addNotification(n);
                   System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");                   
                   String userId=je.getUser().getPushNotificationId();
                   try {
                       int out=MessageUtil.sendMessage( userId, eventName+" "+Messages.UPDATE_EVENT);
                       System.out.println(out+"---------------------------------------------");
                   } catch (IOException ex) {
                       ex.printStackTrace();
                   }
                   
               }
                
                return jsonOutput;
            }else
            {
                updated.put("updated", false);
                jsonOutput.put("HasError", true);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",updated);
                return jsonOutput;
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
      }
      
      @POST
      @Produces(MediaType.APPLICATION_JSON)
      @Consumes(MediaType.APPLICATION_JSON)
      @Path("/addEvent")
      public JSONObject addEvent(String input)
      {
          Event myEvent;
        try {
            
            JSONObject event = new JSONObject(input);
            EventUtil util = new EventUtil();
            myEvent =util.comvertToEventObject(event);
            EventDAO edao = new EventDAO();
            boolean b =edao.addEvent(myEvent);
           
            UserStatue userStatue = new UserStatueDAO().retrieveUserStatueById(6);
            JoinEvent jee = new JoinEvent(new JoinEventId(myEvent.getId(), myEvent.getUser().getId()),
                                myEvent.getUser(),myEvent,userStatue);
            
            JoinEventDAO joinEventDAO= new JoinEventDAO();
            joinEventDAO.addJoinEvent(jee);
            
            
            JSONObject add = new JSONObject();
            JSONObject jsonOutput = new JSONObject();
            
           if(b)
           {
               
               myEvent=edao.retrieveEvent2(myEvent);
               JSONObject myEventJson= new JSONObject(input);
               myEventJson.put("idEvent", myEvent.getId());
               updateEvent(myEventJson.toString());
               
               add.put("id", myEvent.getId());
               jsonOutput.put("HasError", false);
               jsonOutput.put("HasWarning", false);
               jsonOutput.put("FaultsMsg", "");
               jsonOutput.put("ResponseValue",add);
               
               Set joinEvent =myEvent.getJoinEvents();
               String driverName=myEvent.getUser().getName();
               String eventName=myEvent.getEventName();
               for (Iterator it = joinEvent.iterator(); it.hasNext();) 
               {
                   JoinEvent je = (JoinEvent)it.next();
                   Notification n = new Notification(je.getUser(), je.getEvent(), new Date(),"send","new");
                   NotificationDAO ndao = new NotificationDAO();
                   ndao.addNotification(n);
                   String userId=je.getUser().getPushNotificationId();
                   try {
                       
                       int out=MessageUtil.sendMessage( userId,driverName+" "+Messages.CREATE_EVENT+" "+eventName);
                       System.out.println(out+"----------------6666-----------------------------");
                   } catch (IOException ex) {
                       ex.printStackTrace();
                   }
                   
               }
               
           }
           else
           {
               add.put("updated", false);
               jsonOutput.put("HasError", true);
               jsonOutput.put("HasWarning", false);
               jsonOutput.put("FaultsMsg", "");
               jsonOutput.put("ResponseValue",add);
                
           }
           return jsonOutput;
            
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
            
        }
        
      }
     
         
      public JSONObject cancelEvent(String input)
      {
          try {
            JSONObject event = new JSONObject(input);
            
            EventDAO edao = new EventDAO();
            Event myEvent=new Event();
            int idEvent =event.getInt("idEvent");
            myEvent.setId(idEvent);
            myEvent=edao.retrieveEvent(myEvent);
            myEvent.setEventStatue("canceled");
            boolean b= edao.updateEvent(myEvent);
             JSONObject canceled = new JSONObject();
            JSONObject jsonOutput = new JSONObject();
            if(b)
            {
                canceled.put("deleted", "true");
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",canceled);
                
               Set joinEvent =myEvent.getJoinEvents();
               String driverName=myEvent.getUser().getName();
               String eventName=myEvent.getEventName();
               
               for (Iterator it = joinEvent.iterator(); it.hasNext();) 
               {
                   JoinEvent je = (JoinEvent)it.next();
                   String userId=je.getUser().getPushNotificationId();
                   try {
                       int out=MessageUtil.sendMessage( userId,driverName+" "+Messages.CANCEL_EVENT+" "+eventName);
                       System.out.println(out+"---------------------------------------------");
                   } catch (IOException ex) {
                       ex.printStackTrace();
                   }
                   
               }
            }
            else
            {
                 canceled.put("deleted", "false");
                jsonOutput.put("HasError", true);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",canceled);
            }
            
            return jsonOutput;
          
          } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
          
      }
      
}
