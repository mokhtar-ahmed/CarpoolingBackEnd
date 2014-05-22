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
@Path("/addUserToCircle")
public class AddUserToCircle {

    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addUser")
    public String addUserToCirclee(@FormParam(value = "exist") String exist) {
        
        try {
            JSONObject p=new JSONObject(exist);
            User u = new User();
            u.setId(p.getInt("userId"));
            UserImp userImp=new UserImp();
            u = userImp.retrieveUserById(u);
            Circle c=new Circle();
            c.setId(p.getInt("circleId"));
            CircleImp circleImp=new CircleImp();
            c=circleImp.retrieveCircleById(c);
            ExistIn existIn = new ExistIn(u, c, "0");
            
            if(circleImp.retrieveExistInUser(existIn)!=null)
            {
                JSONObject q=new JSONObject();
                q.put("result","user is already exist in this circle");
                return q.toString();
            }
//            ExistInId id = new ExistInId(u.getId(), c.getId());
//            existIn.setId(id);
            circleImp.addUserToCircle(existIn);
            
            JSONObject status=new JSONObject();
            status.put("added", "true");
            return status.toString();
            
        } catch (JSONException ex) {
            Logger.getLogger(AddUserToCircle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
