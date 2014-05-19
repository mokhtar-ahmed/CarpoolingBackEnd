
package webservices;


import dao.*;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.*;

/**
 *
 * @author Nourhan
 */
@Path("location")
public class LocationManagement {
    
    public static final String ID="id";
    public static final String LONGITUDE="longitude";
    public static final String LATTITUDE="lattitude";
    public static final String ALTITUDE="altitude";
    public static final String ADDRESS="address";
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/allLocations")
    public String retrieveAllLocation()
    {
        
        System.out.println("enter to retrive all locations ***********");
        JSONArray allAdresses = new JSONArray();
        
        try {
            LocationDAO locationDAO = new LocationDAO();
            List<Location> l =locationDAO.retrieveAllLocation();
            Iterator<Location> iterator =  l.iterator();
            
            while(iterator.hasNext())
            {
                Location myLocation=iterator.next();
                
                int id=myLocation.getId();
                JSONObject locatoinJson= new JSONObject();
                
                locatoinJson.put(ID, id);
                locatoinJson.put(LONGITUDE, myLocation.getLongitude());
                locatoinJson.put(LATTITUDE,myLocation.getLattitude());
                locatoinJson.put(ALTITUDE, myLocation.getAltitude());
                locatoinJson.put(ADDRESS, myLocation.getAddress());
                allAdresses.put(locatoinJson);
               
            }
            
        }catch (JSONException ex) {
                 ex.printStackTrace();
            }
        System.out.println(allAdresses.toString());
        return allAdresses.toString();
    }
    
}
