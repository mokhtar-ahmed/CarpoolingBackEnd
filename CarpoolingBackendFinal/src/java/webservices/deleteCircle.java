/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import dao.CircleImp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.Circle;
import pojo.User;

/**
 *
 * @author Rehab
 */
@Path("/deleteCircle")
public class deleteCircle {
    @POST
    @Path("/delete")
    public String deleteCirclee(@FormParam(value = "circle")String circle)
    {
        try {
            JSONObject o=new JSONObject(circle);
            Circle c = new Circle();
            c.setId(o.getInt("circleId"));
            CircleImp circleimp = new CircleImp();
            c=circleimp.retrieveCircleById(c);
            if(c==null)
            {
                JSONObject l=new JSONObject();
                l.put("result", "circle doesn't exist");
                return l.toString();
            }
            circleimp.emptyCircle(c);
            circleimp.deleteCircle(c);
            JSONObject status=new JSONObject();
            status.put("deleted", "true");
            
            return status.toString();
        } catch (JSONException ex) {
            Logger.getLogger(deleteCircle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
