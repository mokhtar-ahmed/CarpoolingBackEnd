/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import dao.PhoneImp;
import dao.UserImp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.Phone;
import pojo.User;

/**
 *
 * @author Rehab
 */
@Path("addPhone")
public class AddPhoneNo {
    
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject addPhonee(@FormParam(value = "phone")JSONObject phone)
    {
        JSONObject status=new JSONObject();
        try {
            User u=new User();
            u.setId(phone.getInt("userId"));
            String pp=phone.getString("Phone");
            UserImp ui=new UserImp();
            u=ui.retrieveUserById(u);
            if(u!=null){
                if(u.getPhone().equals(pp)||ui.ifExist(pp))
                {
                    status.put("added", "false");
                    status.put("reason", "phone already exists in db");
                    return status;
                }
            Phone p=new Phone(phone.getString("Phone"), u);
            PhoneImp pi=new PhoneImp();
            pi.addPhone(p);
            
            status.put("added", "true");
            return status;
            }
            else 
            {
                status.put("added", "false");
                status.put("reason", "user doesn't exist in DB");
                return status;
            }
         
        } catch (JSONException ex) {
            Logger.getLogger(AddPhoneNo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
