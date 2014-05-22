package webservices;

import org.json.simple.JSONObject;
import pojo.Event;

class JSONUtils {

    public static final String DATE = "date";
    public static final String EVENT_STATE = "status";
    public static final String ID = "id";
    public static final String LOCATION = "location";
    public static final String NAME = "name";
    public static final String NO_OF_SLOTS = "noOfSlots";
    public static final String USER = "user";
    public static JSONObject ConvertEventToJson(Event event) {
      
       
       JSONObject obj = new JSONObject();
  
        obj.put(DATE,event.getEventDate());
        obj.put(EVENT_STATE,event.getEventStatue());
        obj.put(ID,event.getId());
        obj.put(LOCATION,event.getLocation());
        obj.put(NAME,event.getEventName());
        obj.put(NO_OF_SLOTS,event.getNoOfSlots());
       
       return obj;
         
    }
    
    public static Event ConvertToUser(JSONObject userObject){

     Event event = new Event();
    
     int id = -1;
     
     if((String)userObject.get(ID) != null)
        id =  Integer.parseInt((String)userObject.get(ID));
      
      event.setId(id);
     
      event.setEventName((String)userObject.get( NAME ));
      event.setEventStatue((String)userObject.get( EVENT_STATE));
      
      int noOfSlots =-1;
      
      if ((String)userObject.get( NO_OF_SLOTS) != null )
        noOfSlots =  Integer.parseInt((String)userObject.get( NO_OF_SLOTS));
         
      event.setNoOfSlots(noOfSlots);
   
   
   
      return event;    
  }
  
}
