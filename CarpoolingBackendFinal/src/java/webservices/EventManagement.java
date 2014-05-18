/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;


import dao.*;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public JSONObject retrieveEvent(String input)//finshed
    {
        try {
            JSONObject event = new JSONObject(input);
            Event myEvent = new Event();
            myEvent.setId(event.getInt("idEvent"));
            EventDAO edao = new EventDAO();
            myEvent=edao.retrieveEvent(myEvent);
            JSONObject eventJson;
            if(myEvent!=null)
            {
                ConverObjectToJsonEvent convert = new ConverObjectToJsonEvent();
                eventJson=convert.toEventJson(myEvent);
                return  eventJson;
            }
            else
                return null;
        } catch (JSONException ex) {
            return null;
        }
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/retrieveAllUserEventss")
    public JSONArray retrieveAllUserEvents(String input)//finshed
    {
        try {
            JSONObject myUser = new JSONObject(input);
            int userId= myUser.getInt("userId");
            UserImp udao = new UserImp();
            User u=new User();
            u.setId(userId);
            User user=udao.retrieveUserById(u);
            Set events =user.getEvents();
            
            JSONArray eventJson = new JSONArray();
            JSONObject eventJsonObj=new JSONObject();
            Object[] all = events.toArray();
            int j=0;
            for(int i=0; i<events.size();i++)
            {
                Event myEvent=(Event)all[i];
                eventJsonObj = new JSONObject();
                String check = myEvent.getEventStatue();
                if(!check.equalsIgnoreCase("deleted") || !check.equalsIgnoreCase("canceled"))
                {
                    eventJsonObj.put("idEvent", myEvent.getId());
                    eventJsonObj.put("eventName", myEvent.getEventName());
                    eventJsonObj.put("eventDate", myEvent.getEventDate());
                    eventJson.put(j,eventJsonObj);
                    j++;
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
            JSONObject updated = new JSONObject();
            JSONObject jsonOutput = new JSONObject();
            if(b)
            {
                updated.put("updated", true);
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",updated);
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
        try {
            JSONObject event = new JSONObject(input);
            EventUtil util = new EventUtil();
            Event myEvent =util.comvertToEventObject(event);
            EventDAO edao = new EventDAO();
            boolean b =edao.addEvent(myEvent);
            JSONObject add = new JSONObject();
            JSONObject jsonOutput = new JSONObject();
           if(b)
           {
               myEvent=edao.retrieveEvent2(myEvent);
               System.err.println(myEvent.getId());
               JSONObject myEventJson= new JSONObject(input);
               myEventJson.put("idEvent", myEvent.getId());
               updateEvent(myEventJson.toString());
               
               add.put("updated", true);
               jsonOutput.put("HasError", false);
               jsonOutput.put("HasWarning", false);
               jsonOutput.put("FaultsMsg", "");
               jsonOutput.put("ResponseValue",add);
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
    
      /* @POST
      @Produces(MediaType.APPLICATION_JSON)
      @Consumes(MediaType.APPLICATION_JSON)
      @Path("/joinvent")
      public boolean joinEvent (String input)
      {
        try {
            JSONObject myInput = new JSONObject(input);
            int eventId =myInput.getInt("eventId");
            int userId  =myInput.getInt("userId");
            JoinEventDAO jedao = new JoinEventDAO();
            
            JoinEvent myJoinEvent=jedao.retrieveJoinEvent(userId, eventId);
            if(myJoinEvent.getUserStatue().getId()==1 || myJoinEvent.getEvent().getNoOfSlots()<1)
            {
                System.out.println("Sorrrrrrrrrrrrrrrrrrrrry");
                return false;
            }
            else
            {
                
                myJoinEvent.getUserStatue().setId(2);
                boolean bb=jedao.updateJoinEvent(myJoinEvent);
//                System.out.println(myJoinEvent.getUserStatue().getId());
//                System.out.println(bb);
                return true;
            }
        } catch (JSONException ex) {
            System.err.println("err ");
            return false;
        }
      }*/
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
