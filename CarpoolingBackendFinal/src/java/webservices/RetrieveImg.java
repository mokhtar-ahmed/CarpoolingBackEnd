/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;


import dao.UserImp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.User;

/**
 *
 * @author Rehab
 */
@Path("/retrieveImg")
public class RetrieveImg {
    @POST
    @Path("/hat")
    public String retreiveImgg(@FormParam(value = "user")String user) {
        String path = null;
        try {
            JSONObject o;
            BufferedImage img ;
            byte[] imageBytes = null;
            try {
                o = new JSONObject(user);
                //=======check 
                UserImp q=new UserImp();
                User u11=new User();
                u11.setId(o.getInt("userId"));
                if(q.retrieveUserById(u11)==null)
                {
                    JSONObject a=new JSONObject();
                    a.put("response","id deosn't exist in db");
                    return a.toString();
                }
                //========
             path = "F://" + o.getInt("userId") + ".png";
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%"+ path);
            URL url = new File(path).toURI().toURL();
            } catch (JSONException ex) {
                Logger.getLogger(RetrieveImg.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = webservices.Base64.encodeFromFile(path);
            return s;
        } catch (IOException ex) {
            Logger.getLogger(SaveImg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "not eshta";
    }
    }
