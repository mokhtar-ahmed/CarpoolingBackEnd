package pojo;
// Generated May 16, 2014 8:22:14 AM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * UserStatue generated by hbm2java
 */
public class UserStatue  implements java.io.Serializable {


     private Integer id;
     private String statue;
     private Set joinEvents = new HashSet(0);

    public UserStatue() {
    }

	
    public UserStatue(String statue) {
        this.statue = statue;
    }
    public UserStatue(String statue, Set joinEvents) {
       this.statue = statue;
       this.joinEvents = joinEvents;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getStatue() {
        return this.statue;
    }
    
    public void setStatue(String statue) {
        this.statue = statue;
    }
    public Set getJoinEvents() {
        return this.joinEvents;
    }
    
    public void setJoinEvents(Set joinEvents) {
        this.joinEvents = joinEvents;
    }




}


