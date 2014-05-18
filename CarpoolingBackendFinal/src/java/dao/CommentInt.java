/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import pojo.Comment;

/**
 *
 * @author Rehab
 */
public interface CommentInt {
    public void addComment(Comment comment);
    public void deleteComment(Comment comment);
    public Comment retrieveComment(Comment comment);
    
}
