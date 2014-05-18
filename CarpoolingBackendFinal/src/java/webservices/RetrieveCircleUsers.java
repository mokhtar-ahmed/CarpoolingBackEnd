/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import dao.CircleImp;
import dao.ExistInImp;
import dao.UserImp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import pojo.Circle;
import pojo.ExistIn;
import pojo.User;

/**
 *
 * @author eg
 */
@Path("/retrieveCircleUsers")
public class RetrieveCircleUsers {

    
    @POST
    @Path("/retrieve")
    public String Reg(@FormParam(value = "circleId") String circleId) {
        JSONObject circleIdJSObj;
        int selectedCircleId = 0;
        try {
            circleIdJSObj = new JSONObject(circleId);
            selectedCircleId = circleIdJSObj.getInt("circleId");
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+ selectedCircleId);
        } catch (JSONException ex) {
           ex.printStackTrace();
        }
        

        ExistInImp  existInImp = new  ExistInImp();
        List<ExistIn> usersList= existInImp.isCircleEmpty(selectedCircleId);
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
         
        
    }
}

