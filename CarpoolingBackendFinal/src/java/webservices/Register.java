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
        if (u.getUsername()!=null&&u.getEmail()!=null&&u.getPhone()!=null) {
            
            UserImp userimp = new UserImp();
            User u1=userimp.retrieveUserByUserName(u);
            User u2= userimp.retrieveUserBymail(u.getEmail());
            User u3=userimp.retrieveUserByPhone(u.getPhone());
            
            if(u1==null&&u2==null&&u3==null){
                userimp.addUser(u);
                CircleImp circleImpObj = new CircleImp();
                Circle circleObj1 = new Circle(u,"Friends");
                Circle circleObj2 = new Circle(u,"Family");
                Circle circleObj3 = new Circle(u,"Work");
                circleImpObj.addCircle(circleObj1);
                circleImpObj.addCircle(circleObj2);
                circleImpObj.addCircle(circleObj3);
//                SaveImg saveImg=new SaveImg();
//                try {
//                    saveImg.test(p.getString("image"), user);
//                } catch (JSONException ex) {
//                    Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
//                }
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
            }
            else {
                JSONObject subj = new JSONObject();
                try {
                if(u1!=null&&u2!=null&&u3!=null)
                {
                    subj.put("register", "false");
                    us.put("HasError", "true");
                    us.put("HasWarning", "false");
                    us.put("FaultsMsg", "username&mail&phone must be unique");
                    us.put("ResponseValue", subj);
                }
                else if(u1!=null&&u2!=null)
                {
                   subj.put("register", "false");
                    us.put("HasError", "true");
                    us.put("HasWarning", "false");
                    us.put("FaultsMsg", "username&mail must be unique");
                    us.put("ResponseValue", subj); 
                }
                else if(u1!=null&&u3!=null)
                {
                    
                   subj.put("register", "false");
                    us.put("HasError", "true");
                    us.put("HasWarning", "false");
                    us.put("FaultsMsg", "username&phone must be unique");
                    us.put("ResponseValue", subj); 
                }
                else if(u2!=null&&u3!=null)
                {
                    subj.put("register", "false");
                    us.put("HasError", "true");
                    us.put("HasWarning", "false");
                    us.put("FaultsMsg", "mail&phone must be unique");
                    us.put("ResponseValue", subj); 
                }
                else if(u1!=null)
                {
                    subj.put("register", "false");
                    us.put("HasError", "true");
                    us.put("HasWarning", "false");
                    us.put("FaultsMsg", "username must be unique");
                    us.put("ResponseValue", subj);
                }
                else if(u2!=null)
                {
                    subj.put("register", "false");
                    us.put("HasError", "true");
                    us.put("HasWarning", "false");
                    us.put("FaultsMsg", "mail must be unique");
                    us.put("ResponseValue", subj);
                }
                else if(u3!=null)
                {
                    subj.put("register", "false");
                    us.put("HasError", "true");
                    us.put("HasWarning", "false");
                    us.put("FaultsMsg", "phone must be unique");
                    us.put("ResponseValue", subj);
                }
                    
                return us.toString();
            } catch (JSONException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            return us.toString();
            
        } else {
            try {
                JSONObject subj = new JSONObject();
                subj.put("register", "false");
                us.put("HasError", "true");
                us.put("HasWarning", "false");
                us.put("FaultsMsg", "user object is null");
                us.put("ResponseValue", subj);
                return us.toString();
            } catch (JSONException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
        
    }
}