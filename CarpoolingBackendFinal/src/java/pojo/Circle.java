package pojo;
// Generated May 20, 2014 9:21:31 AM by Hibernate Tools 3.6.0


import java.util.HashSet;
import java.util.Set;

/**
 * Circle generated by hbm2java
 */
public class Circle  implements java.io.Serializable {


     private Integer id;
     private User user;
     private String circleName;
     private Set existIns = new HashSet(0);

    public Circle() {
    }

	
    public Circle(User user, String circleName) {
        this.user = user;
        this.circleName = circleName;
    }
    public Circle(User user, String circleName, Set existIns) {
       this.user = user;
       this.circleName = circleName;
       this.existIns = existIns;
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
    public String getCircleName() {
        return this.circleName;
    }
    
    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }
    public Set getExistIns() {
        return this.existIns;
    }
    
    public void setExistIns(Set existIns) {
        this.existIns = existIns;
    }




}


