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
@Path("/deletePhone")
public class DeletePhoneNo {
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject deletePhonee(@FormParam(value = "phone")JSONObject phone)
    {
        try {
            User u=new User();
            u.setId(phone.getInt("userId"));
            UserImp ui=new UserImp();
            u=ui.retrieveUserById(u);
            Phone p=new Phone(phone.getString("idPhone"), u);
            PhoneImp pi=new PhoneImp();
            pi.deletePhone(p);
            
            JSONObject status=new JSONObject();
            status.put("deleted", "true");
            return status;
            
        } catch (JSONException ex) {
            Logger.getLogger(AddPhoneNo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
