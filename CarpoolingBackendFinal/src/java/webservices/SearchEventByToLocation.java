package webservices;

import dao.*;
import java.util.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pojo.*;




@Path(value = "/searchEventByToLocation")
public class SearchEventByToLocation {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    
    public String getFriends(JSONObject userParam){
        
      JSONObject result = new JSONObject();
      result.put("Message","no results");
      String idStr =  (String)userParam.get("locationId");
      
      JSONArray ev = new JSONArray();
    
    List<Location> locations =  new LocationHome().getLocationById(Integer.parseInt(idStr));
       
      if(locations.isEmpty() == false){
          
          List<Event> events = new EventHome().searchEventByToLocations(locations.get(0));
          
          if(events.isEmpty() == false){
              
              for(Event event : events){
                 JSONObject obj = JSONUtils.ConvertEventToJson(event);
                 ev.add(obj);
              }
              
              result.put("EventList", ev);
              result.put("Message","ok");
                      
          }else {
              result.put("Message","no events starts from this location");
          }
      
      }else{
      
       result.put("Message","not exists Location");
      }
      
        return result.toString();
        
    
    }
}
