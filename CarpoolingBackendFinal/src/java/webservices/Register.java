///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package webservices;
//
//import dao.CircleImp;
//import dao.UserImp;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.ws.rs.FormParam;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;
//import pojo.Circle;
//import pojo.User;
//
///**
// *
// * @author Rehab
// */
//@Path("/register")
//public class Register {
//
//    
//    @POST
//    @Path("/reg")
//    public String Reg(@FormParam(value = "user") String user) {
//        JSONObject us = new JSONObject();
//        
//        User u = null;
//        JSONObject p = null;
//        try {
//            p=new JSONObject(user);
//        } catch (JSONException ex) {
//            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            SaveImg saveImgObj = new SaveImg();
//            
//            u = new User(p.getString("name"), p.getString("username"), p.getString("password"),
//                    p.getString("gender"), null, null, null, null,
//                    p.getString("mail"), null, p.getString("phone"), null,null, null, null,
//                    null, null, null);
//            
//            String imageString = saveImgObj.test(p.getString("image"), user);
//            System.out.println(p.getString("image"));
//            
//            
//            
//        } catch (JSONException ex) {
//            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        if (u != null) {
//            UserImp userimp = new UserImp();
//            User u1=userimp.retrieveUserByUserName(u);
//            CircleImp circleImpObj = new CircleImp();
//                Circle circleObj1 = new Circle(u1,"Friends");
//                Circle circleObj2 = new Circle(u1,"Family");
//                Circle circleObj3 = new Circle(u1,"Work");
//                circleImpObj.addCircle(circleObj1);
//                circleImpObj.addCircle(circleObj2);
//                circleImpObj.addCircle(circleObj3);
//            if(u1 == null){
//                userimp.addUser(u);
//                SaveImg saveImg=new SaveImg();
//                
//                try {
//                    saveImg.test(p.getString("image"), user);
//                    
//                     try {
//                JSONObject subj = new JSONObject();
//                subj.put("register", "true");
//                us.put("HasError", "false");
//                us.put("HasWarning", "false");
//                us.put("FaultsMsg", "false");
//                us.put("ResponseValue", subj);
////                return p.getString("image");
//            } catch (JSONException ex) {
//                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
//            }
//                    
//                } catch (JSONException ex) {
//                    Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            else {
//                try {
//                JSONObject subj = new JSONObject();
//                subj.put("register", "false");
//                us.put("HasError", "false");
//                us.put("HasWarning", "false");
//                us.put("FaultsMsg", "false");
//                us.put("ResponseValue", subj);
//                return us.toString();
//            } catch (JSONException ex) {
//                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            }
////            Phone phone = null;
////            try {
////                phone = new Phone(user.getString("phone"), u);
////            } catch (JSONException ex) {
////                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
////            }
////            PhoneImp phoneimp = new PhoneImp();
////            phoneimp.addPhone(phone);
//           
//           
//            
//            return us.toString();
//        } else {
//            try {
//                JSONObject subj = new JSONObject();
//                subj.put("register", "false");
//                us.put("HasError", "false");
//                us.put("HasWarning", "false");
//                us.put("FaultsMsg", "false");
//                us.put("ResponseValue", subj);
//                return us.toString();
//            } catch (JSONException ex) {
//                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        return null;
//        
//    }
//    
//    
//}
package webservices;
import dao.CircleImp;
import dao.UserImp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.Circle;
import pojo.User;
/**
 *
 * @author Rehab
 */
@Path("/register")
public class Register {
    
    @POST
    @Path("/reg")
    public String Reg(@FormParam(value = "user") String user) {
        JSONObject us = new JSONObject();
        
        User u = null;
        JSONObject p = null;
        try {
            p=new JSONObject(user);
        } catch (JSONException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            u = new User(p.getString("name"), p.getString("username"), p.getString("password"),
                    p.getString("gender"), null, null, null, null,
                    p.getString("mail"), null, p.getString("phone"), null,null, null, null,
                    null, null, null);
        } catch (JSONException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (u != null) {
            UserImp userimp = new UserImp();
            User u1=userimp.retrieveUserByUserName(u);
             
            if(u1 == null){
                userimp.addUser(u);
                CircleImp circleImpObj = new CircleImp();
                Circle circleObj1 = new Circle(u,"Friends");
                Circle circleObj2 = new Circle(u,"Family");
                Circle circleObj3 = new Circle(u,"Work");
                circleImpObj.addCircle(circleObj1);
                circleImpObj.addCircle(circleObj2);
                circleImpObj.addCircle(circleObj3);
                SaveImg saveImg=new SaveImg();
                try {
                    saveImg.test(p.getString("image"), user);
                } catch (JSONException ex) {
                    Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                try {
                JSONObject subj = new JSONObject();
                subj.put("register", "false");
                us.put("HasError", "false");
                us.put("HasWarning", "false");
                us.put("FaultsMsg", "false");
                us.put("ResponseValue", subj);
                return us.toString();
            } catch (JSONException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
//            Phone phone = null;
//            try {
//                phone = new Phone(user.getString("phone"), u);
//            } catch (JSONException ex) {
//                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            PhoneImp phoneimp = new PhoneImp();
//            phoneimp.addPhone(phone);
            try {
                JSONObject subj = new JSONObject();
                subj.put("register", "true");
                us.put("HasError", "false");
                us.put("HasWarning", "false");
                us.put("FaultsMsg", "false");
                us.put("ResponseValue", subj);
            } catch (JSONException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return us.toString();
        } else {
            try {
                JSONObject subj = new JSONObject();
                subj.put("register", "false");
                us.put("HasError", "false");
                us.put("HasWarning", "false");
                us.put("FaultsMsg", "false");
                us.put("ResponseValue", subj);
                return us.toString();
            } catch (JSONException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
        
    }
}