/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservicesInterfaces;

/**
 *
 * @author Nourhan
 */
public interface EventManagementInt {
    
    public String addEvent(String input);
    public String updateEvent(String input);
    public String markAsDeletedEvent(String input);
    public String cancelEvent(String input);
    public String retrieveEvent(String input);
    public String retrieveAllUserEvents(String input);
    public String wantToJoinUsers(String input);
    
}
