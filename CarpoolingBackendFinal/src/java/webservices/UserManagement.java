/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import dao.*;
import java.util.Date;
import java.util.Iterator;
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
import util.UserUtil;

/**
 *
 * @author Nourhan
 */
@Path("user")
public class UserManagement {
    /*public JSONObject blockUser(String input)
    {
        try {
            JSONObject joinEventJson = new JSONObject(input);
            if(!joinEventJson.isNull("user") && !joinEventJson.isNull("event"))
            {
                JSONObject user=joinEventJson.getJSONObject("user");
                int userId=user.getInt("id");
                JSONObject event=joinEventJson.getJSONObject("event");
                int eventId=event.getInt("id");
                
                JoinEventDAO jedao = new JoinEventDAO();
                UserStatueDAO usdao = new UserStatueDAO();
                
                JoinEvent joinEvent=jedao.retrieveJoinEvent(userId, eventId);
                UserStatue userStatue=usdao.retrieveUserStatueById(2);
                joinEvent.setUserStatue(userStatue);
                boolean b =jedao.updateJoinEvent(joinEvent);
                
                JSONObject blockUser = new JSONObject();
                JSONObject jsonOutput = new JSONObject();
                if(b)
                {
                    blockUser.put("blocked", true);
                    jsonOutput.put("HasError", false);
                    jsonOutput.put("HasWarning", false);
                    jsonOutput.put("FaultsMsg", "");
                    jsonOutput.put("ResponseValue",blockUser);
                }
                return jsonOutput;
            }
            return  null;
            } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public JSONObject userJoinEvent(String input)
    {}*/
    
    

@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/login")
public String logIn(String inpString) 
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
            Date   date = myEvent.getEventDate();
            
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
