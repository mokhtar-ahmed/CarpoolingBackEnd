/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Nourhan
 */
public class AppUtill {
    
    public JSONObject reponsArray (JSONArray input)
    {
        try {
            JSONObject jsonOutput = new JSONObject();
            jsonOutput.put("HasError", false);
            jsonOutput.put("HasWarning", false);
            jsonOutput.put("FaultsMsg", "");
            jsonOutput.put("ResponseValue",input);
            return jsonOutput;
        } catch (JSONException ex) {
            Logger.getLogger(AppUtill.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    
    
    public JSONObject reponsObject(JSONObject input)
    {
        try {
            JSONObject jsonOutput = new JSONObject();
            jsonOutput.put("HasError", false);
            jsonOutput.put("HasWarning", false);
            jsonOutput.put("FaultsMsg", "");
            jsonOutput.put("ResponseValue",input);
            return jsonOutput;
        } catch (JSONException ex) {
            Logger.getLogger(AppUtill.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    

}
