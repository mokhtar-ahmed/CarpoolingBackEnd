/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import dao.CircleImp;
import dao.ExistInImp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.Circle;
import pojo.ExistIn;
import pojo.User;

/**
 *
 * @author Rehab
 */
@Path("/deleteCircle")
public class deleteCircle {
    @POST
    @Path("/delete")
    public String deleteCirclee(@FormParam(value = "circleId")String circleId)
    {
        
        try {
            JSONObject o=new JSONObject(circleId);
            Circle c = new Circle();
            c.setId(o.getInt("circleId"));
             JSONObject status=new JSONObject();
          
                ExistInImp existInImpObj = new ExistInImp();
                List<ExistIn> circleRelations = existInImpObj.isCircleEmpty(o.getInt("circleId"));
                CircleImp circleimp = new CircleImp();

                if(circleRelations.isEmpty())
                {

                    circleimp.deleteCircle(c);
                }
                else
                {
                    for(int i=0;i<circleRelations.size();i++)
                    {
                        existInImpObj.deleteExistIn(circleRelations.get(i));
                    }

                    circleimp.deleteCircle(c);
                }
  
           
            status.put("deleted", "true");
            return status.toString();
          
            
            
            
        } catch (JSONException ex) {
            Logger.getLogger(deleteCircle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
