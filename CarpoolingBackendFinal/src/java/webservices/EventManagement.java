/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import PushNotificationUtil.Messages;
import dao.*;
import pojo.*;
import util.*;
import java.util.Iterator;
import java.util.List;
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

/**
 *
 * @author Nourhan
 */
@Path("/event")
public class EventManagement implements webservicesInterfaces.EventManagementInt{
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addEvent")  
    public String addEvent(String input) {
        Event myEvent;
        EventDAO eventDAO = new EventDAO();
        ConvertFromJsonToJava convertFromJsonToJava = new ConvertFromJsonToJava();
        JoinEventDAO joinEventDAO= new JoinEventDAO();
        try {
            JSONObject event = new JSONObject(input);
            myEvent = convertFromJsonToJava.jsonToJavaAdd(event);
             if(myEvent!=null)
             {
                 if(eventDAO.retrieveEvent2(myEvent)==null)
                 {
                     boolean b =eventDAO.addEvent(myEvent);
                        if(b)
                        {
                            // add creator in joinEvent table    
                            UserStatue userStatue = new UserStatueDAO().retrieveUserStatueById(6);
                            JoinEvent jee = new JoinEvent(new JoinEventId(myEvent.getId(), myEvent.getUser().getId()),
                                    myEvent.getUser(),myEvent,userStatue);
                            joinEventDAO.addJoinEvent(jee);
                            
                            JSONObject add = new JSONObject();
                            JSONObject jsonOutput = new JSONObject();
                    
                            add.put("id", myEvent.getId());
                            jsonOutput.put("HasError", false);
                            jsonOutput.put("HasWarning", false);
                            jsonOutput.put("FaultsMsg", "");
                            jsonOutput.put("ResponseValue",add);
               
                            myEvent=eventDAO.retrieveEvent2(myEvent);
                            event.put("idEvent", myEvent.getId());
                            myEvent = convertFromJsonToJava.jsonToJavaAdd(event);
                            if(myEvent!=null){
                                System.out.println("nourhan  "+myEvent);
                            //eventDAO.updateEvent(myEvent);
                    
                            Set joinEvents =myEvent.getJoinEvents();
                            String message =myEvent.getUser().getName()+" "+Messages.CREATE_EVENT+" "+myEvent.getEventName();
                            String statue ="new";
                            for (Iterator it = joinEvents.iterator(); it.hasNext();) 
                            {
                                JoinEvent joinEvent = (JoinEvent)it.next();
                                new NewEventUtil().sendNotificationToUser(joinEvent, message, statue);
                            }
                            return  jsonOutput.toString();
                            }
                            else
                                return new ApplicatipnUtil().jsonException("Sorry, You have another event in that time");
                        }
                        else
                            return new ApplicatipnUtil().jsonException("you have a problim your event does not add");
                 }
                 else
                     return new ApplicatipnUtil().jsonException("your event is already exsist");
             }
             else
                 return new ApplicatipnUtil().jsonException("Sorry, You have another event in that time");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("you have json Exception ");
        }
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateEvent")
    public String updateEvent(String input) {
        try {
            EventDAO edao = new EventDAO();
            JSONObject event = new JSONObject(input);
            
            Event myEvent = edao.retrieveEvent(event.getInt("idEvent"));
            System.out.println(myEvent+"***1");
  
            if(myEvent!=null)
            {
                System.out.println(myEvent.getUser().getName());
                myEvent = new ConvertFromJsonToJavaUpdate().jsonToJava(event,myEvent);
                System.out.println(myEvent);
//                
                if(myEvent!=null)
                {    
                    boolean b =edao.updateEvent(myEvent);
                    System.out.println(b);
                    if(b)
                    {
                       // myEvent = edao.retrieveEvent(event.getInt("idEvent"));
                        Set joinEvents =myEvent.getJoinEvents();
                        System.out.println(joinEvents.size());
                        String message = ""+myEvent.getEventName()+" "+Messages.UPDATE_EVENT;
                        String statue ="update";
                        for (Iterator it = joinEvents.iterator(); it.hasNext();) 
                        {
                            JoinEvent joinEvent = (JoinEvent)it.next();
                            if(joinEvent.getUserStatue().getId()!=6)
                                new NewEventUtil().sendNotificationToUser(joinEvent,message,statue);
                        }
                        JSONObject updated = new JSONObject();
                        JSONObject jsonOutput = new JSONObject();
            
                        updated.put("updated", "true");
                        jsonOutput.put("HasError", false);
                        jsonOutput.put("HasWarning", false);
                        jsonOutput.put("FaultsMsg", "");
                        jsonOutput.put("ResponseValue",updated);
                
                        return jsonOutput.toString();
                    }
                    else
                        return new ApplicatipnUtil().jsonException("you event has not updated ");
                }
                else
                    return new ApplicatipnUtil().jsonException("Sorry,number of slots is less than accepted users");
            }
            else
                return new ApplicatipnUtil().jsonException("this event not found ");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("you have json Exception ");
        }
        //return "";
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/markAsDeletedEvent")
    public String markAsDeletedEvent(String input) {
        try {
            EventDAO edao = new EventDAO();
            JSONObject event = new JSONObject(input);
            
            int idEvent=event.getInt("idEvent");
            Event myEvent = edao.retrieveEvent(idEvent);
            
            JSONObject deleted = new JSONObject();
            JSONObject jsonOutput = new JSONObject();
            if(myEvent!=null)
            {
                myEvent.setEventStatue("deleted");
//                boolean b= edao.updateEvent(myEvent);
                 edao.updateEvent(myEvent);
                
                deleted.put("deleted", "true");
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",deleted);
                
                return jsonOutput.toString();
            }
            else
                return new ApplicatipnUtil().jsonException("you event not found ID is wrong");
        } catch (JSONException ex) {
            return new ApplicatipnUtil().jsonException("you have json Exception ");
        }
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/cancelEvent")
    public String cancelEvent(String input) {
        try {
            EventDAO edao = new EventDAO();
            JSONObject event = new JSONObject(input);
            int idEvent =event.getInt("idEvent");
            Event myEvent=edao.retrieveEvent(idEvent);
            
            JSONObject canceled = new JSONObject();
            JSONObject jsonOutput = new JSONObject();
            
            if(myEvent!=null)
            {
                myEvent.setEventStatue("canceled");
                boolean b= edao.updateEvent(myEvent);
                canceled.put("canceled", "true");
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",canceled);
                
                Set joinEvents =myEvent.getJoinEvents();
                String message=myEvent.getUser().getName()+" "+Messages.CANCEL_EVENT+" "+myEvent.getEventName();
                String statue="cancel";
                for (Iterator it = joinEvents.iterator(); it.hasNext();) 
                {
                    JoinEvent joinEvent = (JoinEvent)it.next();
                    new NewEventUtil().sendNotificationToUser(joinEvent, message,statue);
                }
                return jsonOutput.toString();
            }
            else
                return new ApplicatipnUtil().jsonException("you event not found ID is wrong");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("Json Exception");
        }
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getEvent")
    public String retrieveEvent(String input) {
        try {
            EventDAO edao = new EventDAO();
            JSONObject event = new JSONObject(input);
            int id =event.getInt("idEvent");
            Event myEvent = edao.retrieveEvent(id);
            JSONObject eventJson;// = new JSONObject();
            if(myEvent!=null)
            {
                eventJson =new ConvertFromJavaToJson().toEventJsonObj(myEvent);
                JSONObject jsonOutput = new JSONObject();
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",eventJson);
                
                return  jsonOutput.toString();
            }
            else
                return new ApplicatipnUtil().jsonException("you event not found ID is wrong");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("Json exception");
        }
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/retrieveAllUserEvents")
    public String retrieveAllUserEvents(String input) {
        try {
            EventDAO eventDAO = new EventDAO();            
            JoinEventDAO joinEventDAO= new JoinEventDAO();
            JSONObject myUser = new JSONObject(input);
            
            int userId= myUser.getInt("userId");
            User user = new UserDAO().retrieveUserById(userId);
            
            if(user!=null)
            {
            List events=eventDAO.retrieveUserEvents(userId);
            
            JSONArray eventJson = new JSONArray();
            JSONObject eventJsonObj;//=new JSONObject();
            int j=0;
            //creator
            for (Iterator it = events.iterator(); it.hasNext();)
            {
                Event myEvent = (Event)it.next();
                eventJsonObj = new JSONObject();
                String check = myEvent.getEventStatue();
                if(!check.equals("deleted") || !check.equals("canceled"))
                {
                    eventJsonObj.put("idEvent", myEvent.getId());
                    eventJsonObj.put("eventName", myEvent.getEventName());
                    eventJsonObj.put("userStatue", "Create");
                    eventJson.put(j,eventJsonObj);
                    j++;
                }
            }
            //Attend
            List<JoinEvent> l =joinEventDAO.retrieveAttendedEvent(userId);
            JSONObject evenJsonO = new JSONObject();
            for(Iterator it = l.iterator(); it.hasNext();)
            {
                JoinEvent joinEvent =(JoinEvent)it.next();
                Event event = eventDAO.retrieveEvent(joinEvent.getId().getEventId());
                
                evenJsonO.put("idEvent", event.getId());
                evenJsonO.put("eventName", event.getEventName());
                evenJsonO.put("userStatue", "Attend");
                
                eventJson.put(j,evenJsonO);
                j++;
            }
            
                JSONObject jsonOutput = new JSONObject();
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",eventJson);
                return jsonOutput.toString();
        }
            else
                return new ApplicatipnUtil().jsonException("Sorry this user not found");
            
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("Json exception");
        }
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/wantToJoinUsers")
    public String wantToJoinUsers(String input){
        try {
            JSONObject myUser = new JSONObject(input);
            int eventId= myUser.getInt("eventId");
            
            EventDAO edao = new EventDAO();
            Event event = edao.retrieveEvent(eventId);
            if(event!=null)
            {
            JoinEventDAO jedao = new JoinEventDAO();
            
            List<JoinEvent> joinEvents =jedao.retrieveWantToJoinUsers(eventId);
            JSONObject jsonOutput = new JSONObject();
            JSONArray joinEventJson = new JSONArray();
            
            int userId;
            
            JSONObject joinJsonObj;
            for (Iterator it = joinEvents.iterator(); it.hasNext();)
            {
                joinJsonObj = new JSONObject();
                
                JoinEvent joinEvent=(JoinEvent)it.next();
                userId=joinEvent.getUser().getId();
                String userUsername=joinEvent.getUser().getUsername();
                   
                joinJsonObj.put("userId",userId);
                joinJsonObj.put("username",userUsername);
                   
                joinEventJson.put(joinJsonObj);
            }
            
            jsonOutput.put("HasError", false);
            jsonOutput.put("HasWarning", false);
            jsonOutput.put("FaultsMsg", "");
            jsonOutput.put("ResponseValue",joinEventJson);
                
            return jsonOutput.toString();
            }
            else
                return new ApplicatipnUtil().jsonException("tihs event not found");
        
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("Json exception");
        }
    }
}