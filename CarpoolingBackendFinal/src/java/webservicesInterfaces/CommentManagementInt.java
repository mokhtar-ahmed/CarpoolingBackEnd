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
public interface CommentManagementInt {
    
    public String retrieveEventComments(String input);
    public String addComment(String input);
    public String deleteComment(String input);
    
}
