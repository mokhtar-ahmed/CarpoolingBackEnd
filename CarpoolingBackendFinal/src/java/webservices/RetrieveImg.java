/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.apache.commons.codec.binary.Base64;
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
        try {
            JSONObject o;
            BufferedImage img ;
            byte[] imageBytes = null;
            try {
                o = new JSONObject(user);
                String path = "F://" + o.getInt("userId") + ".png";
            URL url = new File(path).toURI().toURL();
            
            img = ImageIO.read(url);
            
            imageBytes = ((DataBufferByte) img.getData().getDataBuffer()).getData();
            
            } catch (JSONException ex) {
                Logger.getLogger(RetrieveImg.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(imageBytes);
            
            String s = encodeImage(imageBytes);

            return s;
        } catch (IOException ex) {
            Logger.getLogger(SaveImg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "not eshta";
    }

    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64String(imageByteArray);
       //return new String(imageByteArray); 
    }

}
