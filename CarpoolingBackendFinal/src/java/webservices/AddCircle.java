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
import org.codehaus.jettison.json.JSONArray;
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
@Path("/addCircle")
public class AddCircle {
    
    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public String addCirclee(@FormParam(value = "circleDataObj")String circleDataObj)
    {
        try {
            JSONObject o1=new JSONObject(circleDataObj);
            System.out.println(circleDataObj.toString());
            User owner=new User();
            owner.setId(o1.getInt("userId"));
            UserImp userimp=new UserImp();
            owner=userimp.retrieveUserById(owner);
            System.out.println(owner.getName());
            Circle circle1 = null;
            circle1 = new Circle(owner, o1.getString("circleName"));
            CircleImp circleImp=new CircleImp();
            circleImp.addCircle(circle1);
            circle1=circleImp.retrieveCircleByUserIdAndCircleName(circle1);
            
//            JSONArray friends=o1.getJSONArray("friends");
//            for(int i=0;i<friends.length();i++)
//            {
//                System.out.println(friends.get(i));
//                User u =new User();
//                JSONObject o=friends.getJSONObject(i);
//                u.setId(o.getInt("userId"));
//                u=userimp.retrieveUserById(u);
//                ExistIn existIn=new ExistIn(u, circle1, "0");
//                ExistInId id=new ExistInId(u.getId(), circle1.getId());
//                existIn.setId(id);
//                circleImp.addUserToCircle(existIn);
//            }
            
            JSONObject status=new JSONObject();
            status.put("added", "true");
            return "true";
        } catch (JSONException ex) {
            Logger.getLogger(AddCircle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
