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
import pojo.ExistIn;
import pojo.ExistInId;
import pojo.User;

/**
 *
 * @author Rehab
 */
@Path("deleteUserFromCircle")
public class DeleteUserFromCircle {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteUser")
    public JSONObject deleteUserFromCirclee(@FormParam(value = "exist") JSONObject exist) {
        JSONObject status=new JSONObject();
        try {
            User u = new User();
            u.setId(exist.getInt("userId"));
            UserImp userImp=new UserImp();
            u = userImp.retrieveUserById(u);
            
            Circle c=new Circle();
            c.setId(exist.getInt("circleId"));
            CircleImp circleImp=new CircleImp();
            c=circleImp.retrieveCircleById(c);
            
            ExistIn existIn = new ExistIn(u, c, "0");
            ExistInId id = new ExistInId(u.getId(), c.getId());
            existIn.setId(id);
            
            if(circleImp.retrieveExistInUser(existIn)==null)
            {
                status.put("deleted", "false");
                status.put("reason","user doesn't exist in this circle");
                return status;
            }
            circleImp.removeUserFromCircle(existIn);
            status.put("deleted", "true");
            return status;
            
        } catch (JSONException ex) {
            Logger.getLogger(DeleteUserFromCircle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
