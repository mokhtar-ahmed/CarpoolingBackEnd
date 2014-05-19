/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;


import dao.CircleImp;
import dao.UserDAO;
import dao.UserImp;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
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
@Path("/viewcircle")
public class viewAllCircles {
    
    @POST
    @Path("/view")
    @Produces(MediaType.TEXT_PLAIN)
    public String viewCircles(@FormParam(value = "userId")String userId)//JSONObject user)
    {
        System.out.println("ENTEREEEEEEEEEEEEEEEEEED");
               System.err.println("HELLLLLLLLLLLLLLLLLLLLLLLLLO");
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
            User user = new User();
            user.setId(userIdJSObj.getInt("userId"));
            selectedUser = userDao.retrieveUserById(user);
            if(selectedUser == null)
            {
               System.out.println("%%%%%%%%%%%%%%%%%%%%%%%5NULL");  
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
        return  userCirclesJSArray.toString() ;
    }
        @GET
    @Path("/view1")
    public String view()
    {
        
        return "true";
    }
    
    
}
