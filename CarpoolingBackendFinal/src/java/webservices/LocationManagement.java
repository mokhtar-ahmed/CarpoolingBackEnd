/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import util.ApplicatipnUtil;
import dao.LocationDAO;
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
import pojo.Location;
import webservicesInterfaces.LocationManagementInt;

/**
 *
 * @author Nourhan
 */
public class LocationManagement implements LocationManagementInt{

    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/allLocation")
    public String retrieveAllLocation() {
        
        JSONArray allAdresses = new JSONArray();
        JSONObject jsonOutput = new JSONObject();
        try {
            LocationDAO locationDAO = new LocationDAO();
            List<Location> l =locationDAO.retrieveAllLocation();
            Iterator<Location> iterator =  l.iterator();
            int i=0;
            while(iterator.hasNext())
            {
                Location myLocation=iterator.next();
                int id=myLocation.getId();
                String address=myLocation.getAddress();
                JSONObject locatoinJson= new JSONObject();
                locatoinJson.put("id", id);
                locatoinJson.put("address", address);
                allAdresses.put(i, locatoinJson);
                i++;
            }
            jsonOutput.put("HasError", true);
            jsonOutput.put("HasWarning", false);
            jsonOutput.put("FaultsMsg", "have problem");
            jsonOutput.put("ResponseValue",allAdresses);
        }catch (JSONException ex) {
                ex.printStackTrace();
                return new ApplicatipnUtil().jsonException("you have json Exception ");
            }
        return jsonOutput.toString();
    }
    
}
