/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import PushNotificationUtil.MessageUtil;
import PushNotificationUtil.Messages;
import dao.*;
import java.util.Date;
import java.util.Iterator;
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
import util.ApplicatipnUtil;
import util.NewEventUtil;
import util.UserUtil;
import webservicesInterfaces.UserManagementInt;

/**
 *
 * @author Nourhan
 */
@Path("user")
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
            ExistInId existInId = new ExistInId(userId, circleId);
            ExistIn existIn = cdao.retrieveExistIn(existInId);
            if(existIn!=null)
            {
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
            JoinEvent myJoinEvent=jedao.retrieveJoinEvent(userId, eventId);
            Event myEvent = myJoinEvent.getEvent();
            if(myJoinEvent!= null)
            {
                JSONObject join = new JSONObject();
                JSONObject jsonOutput = new JSONObject();
            
                int avalibleSlots = myEvent.getNoOfSlots() - new ApplicatipnUtil().numOfAttendUsers(eventId);
                
                if(avalibleSlots >=1)
                {
                    UserStatueDAO usdao = new UserStatueDAO();
                    myJoinEvent.setUserStatue(usdao.retrieveUserStatueById(3));
                    //change user state in database
                    jedao.updateJoinEvent(myJoinEvent);
                    String message =myJoinEvent.getUser().getUsername()+" "+Messages.JOIN_EVENT+" "+myEvent.getEventName();
                    
                    new NewEventUtil().sendNotificationToUser(myJoinEvent, message, "join");
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
                return new ApplicatipnUtil().jsonException("this user not invited");
            
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
            JoinEvent myJoinEvent=joinEventDAO.retrieveJoinEvent2(userId, eventId);
            int solt =new ApplicatipnUtil().numOfAttendUsers(myJoinEvent.getEvent().getNoOfSlots());
            System.out.println(solt);
            System.out.println(myJoinEvent.getEvent().getNoOfSlots());
            if(myJoinEvent!= null && myJoinEvent.getEvent().getNoOfSlots()- solt>0)
            {
                UserStatueDAO userStatueDAO = new UserStatueDAO();
                UserStatue userStatue= userStatueDAO.retrieveUserStatueById(4);
                myJoinEvent.setUserStatue(userStatue);
                JoinEvent je = new JoinEvent(myJoinEvent.getId(), myJoinEvent.getUser(), myJoinEvent.getEvent(), userStatue);
                joinEventDAO.updateJoinEvent(je);
                
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
            
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("you have json Exception ");
        }
        
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/newLogin")
    public String newLogIn(String inpString) 
    {

        System.out.println("enter to login");

        JSONObject userJson= new JSONObject();
        JSONObject output = new JSONObject();

        try {

            JSONObject login = new JSONObject(inpString);

            String username = login.getString("username");
            String password = login.getString("password");

            UserDAO udao = new UserDAO();

            User user =udao.retrieveUserbyUsernamePass(username, password);

            if(user != null){

                UserUtil userUtil = new UserUtil();
                userJson=userUtil.ConverttUserToJson(user);

                output.put("HasError", "false");
                output.put("HasWarning", "false");
                output.put("FaultsMsg", "success");
                output.put("ResponseValue",userJson);

                JSONArray events = new JSONArray();
                JSONArray circles = new JSONArray();

                Iterator it1 =  user.getCircles().iterator();

                Iterator it  =  user.getJoinEvents().iterator();


            while(it.hasNext()){

                JoinEvent jEvent =  (JoinEvent)it.next();
                Event myEvent = jEvent.getEvent();

               JSONObject eventJsonObj = new JSONObject();

                int id = myEvent.getId();
                String name = myEvent.getEventName();
                Date date = myEvent.getEventDate();

                eventJsonObj.put("idEvent", id);
                eventJsonObj.put("eventName", name);
                eventJsonObj.put("eventDate", date.toString());  
                eventJsonObj.put("userStatue", myEvent.getEventStatue());

                events.put(eventJsonObj);

                System.out.println(events.toString());

           }
           while(it1.hasNext()){

                Circle c = (Circle)it1.next();

                JSONObject circledata = new JSONObject();

                String name =  c.getCircleName();
                int id = c.getId();

                circledata.put("circleName",name);
                circledata.put("circleId", id);  
                circles.put(circledata);

                System.out.println(circledata.toString());

           }

           output.put("circles", circles);
           output.put("events" , events);

            }else {

                output.put("HasError", "false");
                output.put("HasWarning", "false");
                output.put("FaultsMsg", "not registered user");
                output.put("ResponseValue",userJson);

            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        System.out.println(output.toString());

        return output.toString();
    }

}
