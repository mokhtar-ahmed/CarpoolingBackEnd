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
public interface UserManagementInt {
    
    public String blockUser(String input);
    public String unBlockUser(String input);
    public String joinEvent(String input);
    public String AcceptRequest(String input);
    public String RejectRequest(String input);
    public String ReceveNotification(String input);
}
