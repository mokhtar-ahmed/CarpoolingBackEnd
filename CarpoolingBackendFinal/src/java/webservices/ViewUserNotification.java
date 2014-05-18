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
import org.codehaus.jettison.json.JSONObject;


/**
 *
 * @author Mo5a
 */
@Path(value = "/ViewUserNotifications")
public class ViewUserNotification {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getUserNotification(JSONObject input){
      
        JSONObject output = new JSONObject();
        
       return output;      
   }
}
