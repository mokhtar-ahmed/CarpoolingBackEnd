/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;
import dao.*;
import java.text.ParseException;
import pojo.*;
/**
 *
 * @author Mo5a
 */
@Path(value = "/SendNotification")
public class SendNotificationService {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String sendNotification(JSONObject inputJson){
        
        JSONObject output = new JSONObject();
         output.put("message", "invalid input");
         
        String userId = (String)inputJson.get("userId");
        String eventId =(String)inputJson.get("eventId");
   
        if(userId != null && eventId != null){
            try {
                
                Notification notification = NotificationUtil.convertJsonToNotification(inputJson);
                new NotificationHome().sendNotification(notification);
                
                output.put("message", "Added Successfully");
                
            } catch (ParseException ex) {
                 ex.printStackTrace();
                 output.put("message", "Wrong date fromt");
            }
            
             output.put("message", "cann't add the notification");
        }
        
        return output.toString();
    }
    
}
