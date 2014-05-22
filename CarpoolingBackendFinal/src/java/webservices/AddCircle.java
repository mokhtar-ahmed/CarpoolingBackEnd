package webservices;

import dao.CircleImp;
import dao.UserImp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.Circle;
import pojo.User;

/**
 *
 * @author Rehab
 */
@Path("/addCircle")
public class AddCircle {
    
    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public String addCirclee(@FormParam(value = "circleDataObj")String circleDataObj)
    {
        JSONObject status=new JSONObject();
        try {
            JSONObject o1=new JSONObject(circleDataObj);
            System.out.println(circleDataObj.toString());
            User owner=new User();
            owner.setId(o1.getInt("userId"));
            UserImp userimp=new UserImp();
            owner=userimp.retrieveUserById(owner);
            System.out.println(owner.getName());
            Circle circle1 = null;
            circle1 = new Circle(owner, o1.getString("circleName"));
            CircleImp circleImp=new CircleImp();
            //==========check
            if(circleImp.retrieveUserCircleByName(o1.getInt("userId"), o1.getString("circleName"))!=null)
            {
                status.put("added", "false");
                status.put("reson", "there is circle with same name");
                return status.toString();
            }
            //===========
            circleImp.addCircle(circle1);
            circle1=circleImp.retrieveCircleByUserIdAndCircleName(circle1);
            
            
            status.put("added", "true");
            return status.toString();
        } catch (JSONException ex) {
            Logger.getLogger(AddCircle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
