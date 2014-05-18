/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;


import dao.CircleImp;
import dao.UserImp;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import pojo.Circle;
import pojo.User;


/**
 *
 * @author Rehab
 */
@Path("/viewcircle")
public class viewAllCircles {
    
    @POST
    @Path("/view")
    @Produces(MediaType.TEXT_PLAIN)
    public String viewCircles(@FormParam(value = "userId")String userId)//JSONObject user)
    {
        System.out.println("ENTEREEEEEEEEEEEEEEEEEED");
               System.err.println("HELLLLLLLLLLLLLLLLLLLLLLLLLO");
        System.out.println("ENTEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEED");
        JSONObject userIdJSObj,circleJSObj;
        JSONArray userCirclesJSArray;
        UserImp userDao = new UserImp();
        User selectedUser = new User();
        Circle circleObj = new Circle();
        Set<Circle> userCirclesSet;
        try {
            
            userIdJSObj = new JSONObject(userId);
            circleJSObj = new JSONObject();
            userCirclesJSArray = new JSONArray();
            System.out.println(userIdJSObj);
            User user = new User();
            user.setId(userIdJSObj.getInt("userId"));
            selectedUser = userDao.retrieveUserById(user);
            if(selectedUser == null)
            {
               System.out.println("%%%%%%%%%%%%%%%%%%%%%%%5NULL");  
            }
           
            userCirclesSet = selectedUser.getCircles();
            
            System.out.println("Size of Set = "+ "   "+userCirclesSet.size());
            Iterator<Circle> iterator = userCirclesSet.iterator();
             while (iterator.hasNext()) {
                circleObj = iterator.next();
                System.out.println(circleObj.getCircleName()+circleObj.getId());
                circleJSObj = new JSONObject();
                circleJSObj.put("circleName", circleObj.getCircleName().toString());
                circleJSObj.put("circleId", circleObj.getId().toString());
                circleJSObj.put("circleImage", "EMPTY");
                userCirclesJSArray.put(circleJSObj); }
//            for (Circle circle : userCirclesSet) {
//                circleObj = circle;
//                System.out.println(circle.getCircleName()+circle.getIdCircle());
//                circleJSObj.put("circleName", circleObj.getCircleName().toString());
//                circleJSObj.put("circleId", circleObj.getIdCircle().toString());
//                userCirclesJSArray.put(circleJSObj);
//            }
     
     
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null ;
        }
        
        System.out.println("************************"+"        "+userCirclesJSArray.toString());
        return  userCirclesJSArray.toString() ;
//        JSONObject o;
//        User us = null;
//        try {
//            o = new JSONObject(user);
//            us= new User();
//            us.setId(o.getInt("userId"));
//        } catch (JSONException ex) {
//            Logger.getLogger(viewAllCircles.class.getName()).log(Level.SEVERE, null, ex);
//        }yUserName(us);
//        List<Circle> c=circleimp.retri
//        
//        CircleImp circleimp = new CircleImp();
//        UserImp u=new UserImp();
//        User us1 = u.retrieveUserBeveUserCircles(us1);
//        JSONObject circles=new JSONObject();
//        for(int i=0;i<c.size();i++)
//        {
//            try {
//                String name =c.get(i).getCircleName();
//                int cid=c.get(i).getId();
//                JSONObject circledata=new JSONObject();
//                circledata.put("circleName", name);
//                circledata.put("circleId", cid);
//                JSONArray ja=new JSONArray();
//                ja.put(circledata);
//                circles.put("circles", ja);
//                System.out.println("looollllllllllllllll");
//            } catch (JSONException ex) {
//                //Logger.getLogger(viewAllCircles.class.getName()).log(Level.SEVERE, null, ex);
//                ex.printStackTrace();
//            }
//        }
////        System.out.println("looollllllly");
//        //return circles;
//        //return "eshta ya sasa ";
//        return circles.toString();
        //return "true";
    }
    
}
