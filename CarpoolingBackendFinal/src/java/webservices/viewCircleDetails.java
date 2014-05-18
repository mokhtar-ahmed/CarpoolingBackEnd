/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import dao.CircleImp;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
@Path("/viewcircledata")
public class viewCircleDetails {

    @POST
    //@GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{view}")   
    public JSONObject viewcircle(@PathParam(value = "user") String circle) {
        try {
            System.out.println(circle);
            JSONObject o =new JSONObject();
            Circle c1=new Circle();
            c1.setId(o.getInt("circleId"));
            CircleImp circleimp = new CircleImp();
            Circle c = circleimp.retrieveCircleById(c1);

            JSONObject circleData = new JSONObject();
            circleData.put("circleName", c.getCircleName());
            circleData.put("circleId", c.getId());//{circleId","circleName"}

            List<User> s = circleimp.retrieveCircleUsers(c);
            JSONObject u=new JSONObject();
            
            for (int i = 0; i < s.size(); i++) {
                try {
                    String name = s.get(i).getUsername();
                    int cid = s.get(i).getId();
                    String mail = s.get(i).getEmail();

                    JSONObject user1 = new JSONObject();
                    user1.put("userId", cid);
                    user1.put("userName", name);
                    user1.put("mail", mail);
                    u.put("user"+ i, user1);
                } catch (JSONException ex) {
                    Logger.getLogger(viewCircleDetails.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            JSONObject circle1 = new JSONObject();
            circle1.put("circle", circleData);
            circle1.put("users", u);
            return circle1;
        } catch (JSONException ex) {
            Logger.getLogger(viewCircleDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
