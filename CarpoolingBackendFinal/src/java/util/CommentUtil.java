/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import org.codehaus.jettison.json.JSONObject;
import pojo.*;
import dao.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;

/**
 *
 * @author Nourhan
 */
public class CommentUtil {
    
    public Comment convertFromJsonToJava(JSONObject comment)
    {
        Comment comm = new Comment();
        EventDAO edao = new EventDAO();
        UserDAO udao = new UserDAO();
        try {
            if(!comment.isNull("event"))
            {
                JSONObject event=comment.getJSONObject("event");
                int eventId=event.getInt("id");
                Event myEvent=edao.retrieveEvent(eventId);
                comm.setEvent(myEvent);
            }
            
            if(!comment.isNull("owner"))
            {
                JSONObject user=comment.getJSONObject("owner");
                    int userId=user.getInt("id");
                    User myUser=udao.retrieveUserById(userId);
                    comm.setUser(myUser);
            }
            if(!comment.isNull("text"))
            {
                String txt= comment.getString("text");
                comm.setCommentText(txt);
            }
            if(!comment.isNull("date"))
            {
                String d = comment.getString("date");
                DateFormat df = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
                Date date = df.parse(d);
                comm.setCommentDate(date);
            }
            
            return comm;
        }catch (JSONException ex) {
                ex.printStackTrace();
                return null;
            } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
        }
        
    
    
}
