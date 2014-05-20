package pojo;
// Generated May 20, 2014 9:21:31 AM by Hibernate Tools 3.6.0



/**
 * EventToLocationId generated by hbm2java
 */
public class EventToLocationId  implements java.io.Serializable {


     private int eventId;
     private int locationId;

    public EventToLocationId() {
    }

    public EventToLocationId(int eventId, int locationId) {
       this.eventId = eventId;
       this.locationId = locationId;
    }
   
    public int getEventId() {
        return this.eventId;
    }
    
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public int getLocationId() {
        return this.locationId;
    }
    
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof EventToLocationId) ) return false;
		 EventToLocationId castOther = ( EventToLocationId ) other; 
         
		 return (this.getEventId()==castOther.getEventId())
 && (this.getLocationId()==castOther.getLocationId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getEventId();
         result = 37 * result + this.getLocationId();
         return result;
   }   


}

