/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;



import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.User;

/**
 *
 * @author Nourhan
 */
public class UserUtil {
    
    
    public JSONObject ConverttUserToJson(User user)
    {
        JSONObject userJson = new JSONObject();
        try {
            int id=user.getId();
            String name =user.getName();
            String username=user.getUsername();
            String image =user.getUserImage();
            String pushNotificationID =user.getPushNotificationId();
            String phone =user.getPhone();
            String email=user.getEmail();
            
            String facebookKey=user.getFacebookKey();
            
            userJson.put("id", id);
            userJson.put("name", name);
            userJson.put("username", username);
            userJson.put("image", image);
            userJson.put("pushNotificationID",pushNotificationID );
            userJson.put("phone", phone);
            userJson.put("email", email);
            userJson.put("dateOfBirth", user.getDateOfBirth());
            userJson.put("facebookKey", facebookKey);
            userJson.put("password",user.getPassword() );
            userJson.put("rank",user.getRank() );
            userJson.put("gender",user.getGender());
            
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        
        return userJson;
    }
}
