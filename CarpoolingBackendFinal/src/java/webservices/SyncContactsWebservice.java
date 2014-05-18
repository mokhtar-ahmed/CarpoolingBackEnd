package webservices;

import dao.UserImp;
import pojo.User;
import java.util.ArrayList;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/syncContacts")
public class SyncContactsWebservice {

    @POST
    @Path("/sync")
    @Produces(MediaType.TEXT_PLAIN) 
    public String syncContacts(@FormParam(value="contactList")String contactList) {
        
        System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
        JSONObject userIdJSObj;
        JSONArray userContactListJsArray,registeredFriendsJsArray ;
        ArrayList<User> registeredFriendsList;
        
        ArrayList<String>userContactList;
        UserImp userDao = new UserImp();
       
        try {
            
            userContactListJsArray = new JSONArray(contactList);
            registeredFriendsJsArray = new JSONArray();
            userContactList = new ArrayList<String>();
            registeredFriendsList = new ArrayList<User>();
            //Convert from JsonArray to ArrayList<String>
            for (int i = 0; i < userContactListJsArray.length(); i++) {
            userContactList.add(((String)userContactListJsArray.get(i)));
            }
            registeredFriendsList = userDao.retrieveRegisteredUsers(userContactList);
            System.out.println("Size of List Reg"+registeredFriendsList.size());
            
            //Convert from ArrayList<Users> to JsonArray
            for (int i = 0; i < registeredFriendsList.size(); i++) {
            User tempUser = registeredFriendsList.get(i);
            JSONObject jsObj = new JSONObject();
            jsObj.put("name",tempUser.getName());
            jsObj.put("phone",tempUser.getPhone());
            jsObj.put("id",tempUser.getId());
            jsObj.put("image","EMPTY");
            System.out.println("RRRRR *******    "+jsObj);
            registeredFriendsJsArray.put(jsObj);
            }
            
         
            
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null ;
        }
        
        System.out.println(registeredFriendsJsArray.toString());
        return  registeredFriendsJsArray.toString() ;
        
    }
  

    
}
