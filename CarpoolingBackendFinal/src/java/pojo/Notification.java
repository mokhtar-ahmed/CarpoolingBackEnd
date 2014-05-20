package pojo;
// Generated May 20, 2014 5:05:58 AM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * Notification generated by hbm2java
 */
public class Notification  implements java.io.Serializable {


     private Integer id;
     private User user;
     private Event event;
     private Date notificationDate;
     private String eventType;
     private String eventState;

    public Notification() {
    }

	
    public Notification(User user, Event event, Date notificationDate) {
        this.user = user;
        this.event = event;
        this.notificationDate = notificationDate;
    }
    public Notification(User user, Event event, Date notificationDate, String eventType, String eventState) {
       this.user = user;
       this.event = event;
       this.notificationDate = notificationDate;
       this.eventType = eventType;
       this.eventState = eventState;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public Event getEvent() {
        return this.event;
    }
    
    public void setEvent(Event event) {
        this.event = event;
    }
    public Date getNotificationDate() {
        return this.notificationDate;
    }
    
    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }
    public String getEventType() {
        return this.eventType;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public String getEventState() {
        return this.eventState;
    }
    
    public void setEventState(String eventState) {
        this.eventState = eventState;
    }




}


