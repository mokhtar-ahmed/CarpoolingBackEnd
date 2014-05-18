/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import dao.UserImp;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.User;

/**
 *
 * @author eg
 */
@Path("/login")
public class login {
    @POST
    @Path("/login")
    public String Reg(@FormParam(value = "userData") String userData) throws JSONException {
        
        System.out.println("tttttttttttttttttttttttttttttttttttttttttt");
        JSONObject userDataJs = new JSONObject(userData);
        String username = userDataJs.getString("username");
        String password = userDataJs.getString("password");
        User user = new User();
        User retrievedUser = new User();
        user.setUsername(username);
        user.setPassword(password);
        UserImp userDao = new UserImp();
        retrievedUser = userDao.retrieveUserByUserNameAndPassword(user);
        if(retrievedUser == null)
        {
            return "NotExist";
        }
        else
        {
            JSONObject userPhone = new JSONObject();
            userPhone.put("phone", retrievedUser.getPhone());
            return userPhone.toString();
        }
    }
    
}
