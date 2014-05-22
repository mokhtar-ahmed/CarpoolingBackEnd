/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import dao.CircleImp;
import dao.UserImp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.Circle;
import pojo.User;

/**
 *
 * @author Rehab
 */
@Path("updateCircle")
public class UpdateCircle {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public JSONObject updateCirclee(@FormParam(value = "circle")JSONObject circle)
    {
        JSONObject status=new JSONObject();
        try {
            System.out.println(circle.toString());
            User owner=new User();
            
            owner.setId(circle.getInt("userId"));
            
            UserImp userimp=new UserImp();
            owner=userimp.retrieveUserById(owner);
            System.out.println(owner.getName());
            Circle circle1 = null;
            CircleImp c=new CircleImp();
            int x=circle.getInt("circleId");
            circle1=new Circle();
            circle1.setId(x);
            circle1=c.retrieveCircleById(circle1);
            circle1.setUser(owner);
            circle1.setCircleName(circle.getString("circleName"));
            
            
            CircleImp circleImp=new CircleImp();
            if(circleImp.retrieveUserCircleByName(circle.getInt("userId"), circle.getString("circleName"))!=null)
            {
                status.put("added", "false");
                status.put("reson", "there is circle with same name");
                return status;
            }
            //===========
            
            circleImp.editCircle(circle1);
//            circle1=circleImp.retrieveCircleByUserIdAndCircleName(circle1);
            /////////////////////////
            
            status.put("updated", "true");
            return status;
        } catch (JSONException ex) {
            Logger.getLogger(UpdateCircle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
