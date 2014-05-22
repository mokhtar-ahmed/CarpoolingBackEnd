/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mytest;

import dao.UserImp;
import java.util.Iterator;
import pojo.Circle;
import pojo.Event;
import pojo.User;

/**
 *
 * @author Mo5a
 */
public class TestJoinEvent {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

   
        Thread t = new Thread(new Runnable() {

            @Override
        public void run() {
            User u=new User();
            u.setId(1);
                User user = new UserImp().retrieveUserById(u);
                Iterator it  =  user.getEvents().iterator();
                Iterator it1 =  user.getCircles().iterator();
           
        while(it.hasNext()){
       
            System.out.println(((Event)it.next()).getUser().getName());
          // System.out.println(((JoinEvent)it.next()).getEvent().getEventName());
       
       }
       while(it1.hasNext()){
           Circle c = (Circle)it1.next();
           System.out.println(c.getUser().getName());
       
       } 
    
            }
        });
    
        t.start();
        }
   
     

}
