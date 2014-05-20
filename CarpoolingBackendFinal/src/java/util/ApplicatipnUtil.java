/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import dao.JoinEventDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Nourhan
 */
public class ApplicatipnUtil {
    
    public String jsonException(String message)
    {
        String out;
        JSONObject jsonOutput = new JSONObject();
        try {
            
            jsonOutput.put("HasError", true);
            jsonOutput.put("HasWarning", false);
            jsonOutput.put("FaultsMsg", message);
            jsonOutput.put("ResponseValue", "");
           
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        out=jsonOutput.toString();
          return  out;
    }
    public int numOfAttendUsers(int eventId)
    {
        JoinEventDAO joinEventDAO = new JoinEventDAO();
        List l =joinEventDAO.retrieveAcceptUsers(eventId);
        return l.size();   
    }
}
