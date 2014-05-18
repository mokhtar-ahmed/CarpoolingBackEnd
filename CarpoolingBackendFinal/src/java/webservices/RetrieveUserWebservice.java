package webservices;

import dao.UserImp;
import pojo.User;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/editprofileservice")
public class RetrieveUserWebservice {

    @POST
    @Path("/edit")
    @Produces(MediaType.TEXT_PLAIN) 
    public String syncContacts(@FormParam(value="userToLoginJS")String userToLoginJS) {
        
        System.out.println("ENTEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEED");
        JSONObject retrievedUser = null;
        JSONObject userIdJSObj = null;
        try {
            userIdJSObj = new JSONObject(userToLoginJS);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        
        UserImp userDao = new UserImp();
       
        try {
            
           User u=new User();
           u.setId(userIdJSObj.getInt("userId"));
           u=userDao.retrieveUserById(u);
           
           retrievedUser = new JSONObject();
           retrievedUser.put("userId", u.getId());
           RetrieveImg retrieveImgObj = new RetrieveImg();
           String imgRetrievedString = retrieveImgObj.retreiveImgg(retrievedUser.toString());
           System.out.println("ImageString "+imgRetrievedString);
           retrievedUser.put("name",u.getName());
          retrievedUser.put("imageString",imgRetrievedString);
           
            System.out.println("NAME "+u.getName());
             System.out.println("Date "+u.getDateOfBirth());
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null ;
        }  
        return  retrievedUser.toString();
        
    }
  

    
}
