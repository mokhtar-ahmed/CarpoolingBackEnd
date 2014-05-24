/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import dao.EventDAO;
import dao.JoinEventDAO;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.Event;

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
    public boolean checkIfDateExsit(Date date,int UserId)
    {
            int year  =date.getYear();
            int month =date.getMonth();
            int day   =date.getDay();
            int hour  =date.getHours();
            
            int yearEvent,monthEvent,dayEvent,hourEvent;
            
            List own    =new EventDAO().retrieveUserEvents(UserId);
            List attend =new JoinEventDAO().retrieveAttendedEvent(UserId);
            
            for(Iterator it = own.iterator(); it.hasNext();)
            {
                Event event = (Event) it.next();
            
                yearEvent =event.getEventDate().getYear();
                monthEvent =event.getEventDate().getMonth();
                dayEvent = event.getEventDate().getDay();
                hourEvent =event.getEventDate().getHours();
                
                if(year == yearEvent && month == monthEvent && day == dayEvent && hour == hourEvent)
                {
                    return true;
                }
            }
            for(Iterator it = attend.iterator(); it.hasNext();)
            {
                Event event = (Event) it.next();
            
                yearEvent =event.getEventDate().getYear();
                monthEvent =event.getEventDate().getMonth();
                dayEvent = event.getEventDate().getDay();
                hourEvent =event.getEventDate().getHours();
                
                if(year == yearEvent && month == monthEvent && day == dayEvent && hour == hourEvent)
                {
                    return true;
                }
            }
        
            return false;
    }
}
