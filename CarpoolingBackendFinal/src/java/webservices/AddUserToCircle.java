/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import dao.CircleImp;
import dao.ExistInImp;
import dao.UserImp;
import java.util.List;
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
@Path("/addUserToCircle")
public class AddUserToCircle {

    @POST
   
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/addUser")
    public String addUserToCirclee(@FormParam(value = "circleId") String circleId,@FormParam(value = "userIds") String userIds) {
        try {
//          
            System.out.println("ENTEREEEEEEEEEEEEEEEEEEEEEEEEEEEEED");
            JSONObject circleJS = new JSONObject(circleId);
            Circle c = new Circle();
            c.setId(circleJS.getInt("circleId"));
            System.out.println("^^^^^^^^^^^^^^^^^^"+circleJS.getInt("circleId"));
            
            CircleImp circleImp=new CircleImp();
            c=circleImp.retrieveCircleById(c);
            JSONArray usersToAdd = new JSONArray(userIds);
            JSONArray circleUsers = new JSONArray();
            for(int i=0;i<usersToAdd.length();i++)
            {
                  
                  JSONObject temp = usersToAdd.getJSONObject(i);
                  User u = new User();
                  UserImp userImp=new UserImp();
                  System.out.println(temp.getInt("userId"));
                  u.setId(temp.getInt("userId"));
                  u = userImp.retrieveUserById(u);
                  ExistIn existIn = new  ExistIn(u, c, "NOT");
                  circleImp.addUserToCircle(existIn);
                   JSONObject temp2 = new JSONObject();
                   
                  temp2.put("userId",temp.getInt("userId"));
                  temp2.put("Name",u.getName());
                  temp2.put("image",u.getUserImage());
                  circleUsers.put(temp2); 
                  System.out.println( circleUsers.toString());
            }    
            
            ExistInImp existInImpObj = new  ExistInImp();
            List<ExistIn> usersList= existInImpObj.isCircleEmpty(circleJS.getInt("circleId"));
            User currentUser;
            JSONObject tempJs;
            JSONArray circleUsersList = new JSONArray();
            for(int i=0;i<usersList.size();i++)
            {
            ExistIn existinObj = usersList.get(i);
            currentUser = existinObj.getUser();
            tempJs = new JSONObject();        
            try {
                tempJs.put("userId", currentUser.getId());
                tempJs.put("Name", currentUser.getName());
                tempJs.put("Phone", currentUser.getPhone());
                if(currentUser.getUserImage()== null)
                {
                   tempJs.put("image", "image"); 
                }
                else
                {
                   tempJs.put("image", currentUser.getUserImage()); 
                }
                
                circleUsersList.put(tempJs);
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+ circleUsersList.toString());
                
            } catch (JSONException ex) {
                Logger.getLogger(RetrieveCircleUsers.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return circleUsersList.toString();
        
            
        } catch (JSONException ex) {
            
            Logger.getLogger(AddUserToCircle.class.getName()).log(Level.SEVERE, null, ex);
            return "Not Added";
        }
       
    }
}
