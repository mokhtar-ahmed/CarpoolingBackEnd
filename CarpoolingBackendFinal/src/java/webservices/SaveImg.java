///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package webservices;
//
///**
// *
// * @author Rehab
// */
//import dao.UserImp;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.imageio.ImageIO;
//import javax.ws.rs.FormParam;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.coyote.http11.upgrade.AbstractServletOutputStream;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;
//import pojo.User;
//
///**
// *
// * @author Rehab
// */
//@Path("/saveImg")
//public class SaveImg {
//
//    public String saveImgg(byte[] image, User user) {
//        try {
//            String path = ""
//                    + "F://" + user.getId() + ".png";
//            BufferedImage img = ImageIO.read(new ByteArrayInputStream(image));
////            OutputStream stream = new FileOutputStream(new File(path));
////            stream.write(image);
//            ImageIO.write(img, "png", new File(path));
//            
//            user.setUserImage(path);
//            UserImp ui=new UserImp();
//            User u= new User();
//            u=ui.retrieveUserByUserName(user);
//            u.setUserImage(path);
//            ui.edit(u);
//
//            return "eshta";
//        } catch (IOException ex) {
//            Logger.getLogger(SaveImg.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "fffffffff";
//    }
//
//    public static byte[] decodeImage(String imageDataString) {
//        return Base64.decodeBase64(imageDataString);
//         
//    }
//
//    @POST
//    @Path("/save")
////    @Consumes(MediaType.APPLICATION_JSON)
////    @Produces(MediaType.APPLICATION_JSON)
//    public String test(@FormParam(value = "image")String image,@FormParam(value = "user")String user){try {
//        //String image, User user) {
//        System.out.println(image);
//        
//        byte[] img = decodeImage(image);
//        JSONObject o =new JSONObject(user);
//        User u=new User();
//        u.setUsername(o.getString("username"));
//        UserImp ui=new UserImp();
//        u=ui.retrieveUserByUserName(u);
//
////        u.setId(o.getInt("userId"));
////        u.setUsername(o.getString("userName"));
//        String s = saveImgg(img, u);
//        return s;
//        } catch (JSONException ex) {
//            Logger.getLogger(SaveImg.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//}
//



package webservices;

/**
 *
 * @author Rehab
 */
import dao.UserImp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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
@Path("/saveImg")
public class SaveImg {

    public String saveImgg(byte[] image, User user) {
        try {
            String path = ""
                    + "F://" + user.getId() + ".png";
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(image));
            ImageIO.write(img, "png", new File(path));
            
            UserImp ui=new UserImp();
            User u= new User();
            u=ui.retrieveUserByUserName(user);
            u.setUserImage(path);
            ui.edit(u);

            return "eshta";
        } catch (IOException ex) {
            Logger.getLogger(SaveImg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "fffffffff";
    }

    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }

    @POST
    @Path("/save")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    public String test(@FormParam(value = "image")String image,@FormParam(value = "user")String user){try {
        //String image, User user) {
        System.out.println(image);
        
        byte[] img = decodeImage(image);
        JSONObject o =new JSONObject(user);
        User u=new User();
//        u.setId(o.getInt("userId"));
        u.setUsername(o.getString("username"));
        UserImp userImp=new UserImp();
        u=userImp.retrieveUserByUserName(u);
        String s = saveImgg(img, u);
        return s;
        } catch (JSONException ex) {
            Logger.getLogger(SaveImg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}