/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mytest;

import dao.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.*;

/**
 *
 * @author Nourhan
 */
public class TestDao {
    
        public static void main(String args[])
    {
        Event e = new Event();
        //e.setEventName("iti");
        e.setId(1);
//        EventDAO edao = new EventDAO();
////        e = edao.retrieveEvent(e);
//        System.out.println(e);
//        e.setEventDate(new Date(2015-1900, 11, 12));
//        e.setNoOfSlots(0);
//        JoinEvent j=(JoinEvent) e.getJoinEvents().iterator().next();
//        UserStatueDAO usd = new UserStatueDAO();
//        UserStatue us=usd.retrieveUserStatueById(3);
//        j.setUserStatue(us);    
//        e.getJoinEvents().add(e);
//        boolean b=edao.updateEvent(e);
//        System.out.println(b);
    }
    
//    
//    public static void main(String args[])
//    {
//        Event e = new Event();
//        e.setEventName("iti");
//        EventDAO edao = new EventDAO();
//        List l = edao.retrieveAllEvents(e);
//        for(int i=0; i<l.size();i++)
//        System.out.println(l.get(i));
//    }    
//   
//    
//    
//    
}
