/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;



import dao.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import pojo.User;



/**
 *
 * @author Rehab
 */
@Path("/retrieveAllCircles")
public class ViewAllCircles {
    @POST
    @Path("/retrieve")
    @Produces(MediaType.TEXT_PLAIN)
    public String viewCircles(@FormParam(value = "userId")String userId)
    {
        System.err.println("errrrroooooooooooooooooooooooor");
        System.out.println("ENTEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEED");
        JSONObject userIdJSObj,circleJSObj;
        JSONArray userCirclesJSArray;
        UserImp userDao = new UserImp();
        User selectedUser = new User();
        Circle circleObj = new Circle();
        Set<Circle> userCirclesSet;
        try {
            
            userIdJSObj = new JSONObject(userId);
            circleJSObj = new JSONObject();
            userCirclesJSArray = new JSONArray();
            System.out.println(userIdJSObj);
            User u=new User();
            u.setId(userIdJSObj.getInt("userId"));
            selectedUser = userDao.retrieveUserById(u);
            
            if(selectedUser==null)
            {
                JSONObject o=new JSONObject();
                o.put("result", "user not found");
                return o.toString();
            }
                
            userCirclesSet = selectedUser.getCircles();
            
            System.out.println("Size of Set = "+ "   "+userCirclesSet.size());
            Iterator<Circle> iterator = userCirclesSet.iterator();
             while (iterator.hasNext()) {
                circleObj = iterator.next();
                System.out.println(circleObj.getCircleName()+circleObj.getId());
                circleJSObj = new JSONObject();
                circleJSObj.put("circleName", circleObj.getCircleName().toString());
                circleJSObj.put("circleId", circleObj.getId().toString());
                circleJSObj.put("circleImage", "EMPTY");
                userCirclesJSArray.put(circleJSObj); }

     
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null ;
        }
        
        System.out.println("************************"+"        "+userCirclesJSArray.toString());
        return  userCirclesJSArray.toString();
    }
    
}
