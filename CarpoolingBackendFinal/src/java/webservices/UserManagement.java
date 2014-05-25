/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import PushNotificationUtil.MessageUtil;
import PushNotificationUtil.Messages;
import dao.*;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import pojo.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import util.ApplicatipnUtil;
import util.NewEventUtil;
import webservicesInterfaces.UserManagementInt;

/**
 *
 * @author Nourhan
 */
@Path("/user")
public class UserManagement implements UserManagementInt{

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/block")
    public String blockUser(String input) {
        try {
            CircleDAO cdao = new CircleDAO();
            JSONObject myInput = new JSONObject(input);
            
            int circleId =myInput.getInt("circleId");
            
            int userId  =myInput.getInt("userId");
            Circle circle = new CircleDAO().retrieveCircleById(circleId);
            System.out.println(circle);
            User user = new UserDAO().retrieveUserById(userId);
            System.out.println(user);
            if(circle!=null && user!=null)
            {
                System.out.println("1");
            ExistInId existInId = new ExistInId(userId, circleId);
            ExistIn existIn = cdao.retrieveExistIn(existInId);
            if(existIn!=null)
            {System.out.println("2");
                existIn.setBolckStatue("true");
                cdao.updateExistIn(existIn);
                
                JSONObject jsonOutput = new JSONObject();
                JSONObject blocked = new JSONObject();
                blocked.put("blocked", "true");
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",blocked);
                
                return jsonOutput.toString();
            }
            else
                return new ApplicatipnUtil().jsonException("this user not exsist in that circle");
            }
            else
                return new ApplicatipnUtil().jsonException("this circle or user is not found");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("you have json Exception ");
        }
        
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/unblock")
    public String unBlockUser(String input) {
        try {
            CircleDAO cdao = new CircleDAO();
            JSONObject myInput = new JSONObject(input);
            
            int circleId =myInput.getInt("circleId");
            int userId  =myInput.getInt("userId");
            Circle circle = new CircleDAO().retrieveCircleById(circleId);
            User user = new UserDAO().retrieveUserById(userId);
            if(circle!=null && user!=null)
            {
            ExistInId existInId = new ExistInId(userId, circleId);
            ExistIn existIn = cdao.retrieveExistIn(existInId);
            if(existIn!=null)
            {
                existIn.setBolckStatue("false");
                cdao.updateExistIn(existIn);
                
                JSONObject jsonOutput = new JSONObject();
                JSONObject blocked = new JSONObject();
                blocked.put("blocked", "false");
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                jsonOutput.put("ResponseValue",blocked);
                
                return jsonOutput.toString();
            }
            else
                return new ApplicatipnUtil().jsonException("this user not exsist in that circle");
        }
        else
            return new ApplicatipnUtil().jsonException("Circle or user not found");
        
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("you have json Exception ");
        }
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/joinvent")
    public String joinEvent(String input) {
        try {
            JoinEventDAO jedao = new JoinEventDAO();
            
            JSONObject myInput = new JSONObject(input);
            int eventId =myInput.getInt("eventId");
            int userId  =myInput.getInt("userId");
            Event event =new EventDAO().retrieveEvent(eventId);
            User user = new UserDAO().retrieveUserById(userId);
            if(event!=null && user!=null) 
            {
                if(!user.equals(event.getUser()))
                {
                    JoinEvent myJoinEvent=jedao.retrieveJoinEvent(userId, eventId);
                    //Event myEvent = myJoinEvent.getEvent();
                    if(myJoinEvent!= null)
                    {
                        JSONObject join = new JSONObject();
                        JSONObject jsonOutput = new JSONObject();
            
                        int avalibleSlots = event.getNoOfSlots() - new ApplicatipnUtil().numOfAttendUsers(eventId);
                        
                        if(avalibleSlots >=1)
                        {
                            UserStatueDAO usdao = new UserStatueDAO();
                            myJoinEvent.setUserStatue(usdao.retrieveUserStatueById(3));
                            //change user state in database
                            JoinEvent e = new JoinEvent();
                            e.setEvent(event);
                            e.setId(new JoinEventId(eventId, userId));
                            e.setUserStatue(usdao.retrieveUserStatueById(3));
                            e.setUser(user);                    
                            jedao.updateJoinEvent(e);
                            String message =myJoinEvent.getUser().getUsername()+" "+Messages.JOIN_EVENT+" "+event.getEventName();
                    
                            ///////////////////
                            NotificationDAO ndao = new NotificationDAO();    
                            Notification n = new Notification(myJoinEvent.getEvent().getUser(),
                                myJoinEvent.getEvent(), new Date(),"unread","join");
                            ndao.addNotification(n);
                            String userID=myJoinEvent.getUser().getPushNotificationId();
                            try {
                                int out=MessageUtil.sendMessage( userID, message);
                                System.out.println(out+"---------------------------------------------");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            //////////////////////////
                            jsonOutput.put("HasError", false);
                            jsonOutput.put("HasWarning", false);
                            jsonOutput.put("FaultsMsg", "");
                            join.put("join", "true");
                            jsonOutput.put("ResponseValue",join);
                            return  jsonOutput.toString();
                        }
                        else
                            return new ApplicatipnUtil().jsonException("no avilable slots");
                    }
                    else
                        return new ApplicatipnUtil().jsonException("this user not invited to event");
                }
                else
                    return new ApplicatipnUtil().jsonException("this user is the owner of event");
            }
            else
                return new ApplicatipnUtil().jsonException("user id or event id is not exsist");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("you have json Exception ");
        }
        
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/accept")
    public String AcceptRequest(String input) {
        JoinEventDAO joinEventDAO = new JoinEventDAO();
        try {
            JSONObject myInput = new JSONObject(input);
            int eventId =myInput.getInt("eventId");
            int userId  =myInput.getInt("userId");
            
            User user = new UserDAO().retrieveUserById(userId);
            Event event = new EventDAO().retrieveEvent(eventId);
            if(user!=null && event!=null)
            {
            JoinEvent myJoinEvent=joinEventDAO.retrieveJoinEvent2(userId, eventId);
            int solt =new ApplicatipnUtil().numOfAttendUsers(event.getNoOfSlots());
            if(myJoinEvent!= null && event.getNoOfSlots()- solt>0)
            {
                UserStatueDAO userStatueDAO = new UserStatueDAO();
                UserStatue userStatue= userStatueDAO.retrieveUserStatueById(4);
                myJoinEvent.setUserStatue(userStatue);
                JoinEvent e = new JoinEvent();
                    e.setEvent(event);
                    e.setId(new JoinEventId(eventId, userId));
                    e.setUserStatue(new UserStatueDAO().retrieveUserStatueById(4));
                    e.setUser(user);
                    
                    joinEventDAO.updateJoinEvent(e);
                
//                
                String message =Messages.ACCEPT_REQUEST+" "+myJoinEvent.getEvent().getEventName();
                new NewEventUtil().sendNotificationToUser(myJoinEvent, message, "accepted");
//                
                
                JSONObject jsonOutput = new JSONObject();
                JSONObject accept = new JSONObject();
                
                jsonOutput = new JSONObject();
                accept = new JSONObject();
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                accept.put("Accepted", "true");
                jsonOutput.put("ResponseValue",accept);
                
                return  jsonOutput.toString();
            }
            else
                return new ApplicatipnUtil().jsonException("this user not join or slots is complete");
        }
        else
            return new ApplicatipnUtil().jsonException("Event or user not found");
                
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("you have json Exception ");
            
        }
        
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/reject")
    public String RejectRequest(String input) {
        try {
            JSONObject myInput = new JSONObject(input);
            int eventId =myInput.getInt("eventId");
            int userId  =myInput.getInt("userId");
            JoinEventDAO joinEventDAO = new JoinEventDAO();
            JoinEvent myJoinEvent=joinEventDAO.retrieveJoinEvent2(userId, eventId);
            if(myJoinEvent!= null)
            {
                UserStatueDAO userStatueDAO = new UserStatueDAO();
                UserStatue userStatue =userStatueDAO.retrieveUserStatueById(5);
        
                myJoinEvent.setUserStatue(userStatue);
                joinEventDAO.updateJoinEvent(myJoinEvent);
                
                JSONObject jsonOutput = new JSONObject();
                JSONObject reject = new JSONObject();
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                reject.put("Rejecteded", "true");
                jsonOutput.put("ResponseValue",reject);
                
                return  jsonOutput.toString();
            }
            else
                return new ApplicatipnUtil().jsonException("this user not join");
            
        } catch (JSONException ex) {
            ex.printStackTrace();
               return new ApplicatipnUtil().jsonException("you have json Exception ");
        }
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/receveNotification")
    public String ReceveNotification(String input) {
        try {
            
            JSONObject myInput = new JSONObject(input);
            int notificationId  =myInput.getInt("notificationId");
            
            NotificationDAO notificationDAO = new NotificationDAO();
        
            Notification notification =notificationDAO.retrieveNotificationById(notificationId);
            if(notification!=null)
            {
                notification.setEventType("read");
                notificationDAO.updateNotification(notification);
        
                JSONObject jsonOutput = new JSONObject();
                JSONObject notific = new JSONObject();
                jsonOutput.put("HasError", false);
                jsonOutput.put("HasWarning", false);
                jsonOutput.put("FaultsMsg", "");
                notific.put("recieved", "true");
                jsonOutput.put("ResponseValue",notific);
        
                return jsonOutput.toString();
            }
            else
                return new ApplicatipnUtil().jsonException("notification id is wrong");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("you have json Exception ");
        }
    }    
}
