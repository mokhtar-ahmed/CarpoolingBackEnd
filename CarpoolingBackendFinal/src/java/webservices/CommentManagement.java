/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import dao.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.*;
import util.ApplicatipnUtil;
import util.CommentUtil;
import webservicesInterfaces.CommentManagementInt;

/**
 *
 * @author Nourhan
 */
@Path("/comment")
public class CommentManagement implements CommentManagementInt{
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getAllComments")
    public String retrieveEventComments(String input) {
        try {
            JSONObject event = new JSONObject(input);
            EventDAO edao = new EventDAO();
            
            int id = event.getInt("idEvent");
            Event myEvent=edao.retrieveEvent(id);
            if(myEvent!=null)
            {
            Set comments=myEvent.getComments();
            
            JSONArray allComments = new JSONArray();
            int i=0;
            for (Iterator it = comments.iterator(); it.hasNext();) 
            {
                Comment comment = (Comment)it.next();
                
                //CommentID
                int commentId = comment.getId();
                //user
                int userId =comment.getUser().getId();
                String rank =comment.getUser().getRank();
                String usename=comment.getUser().getUsername();
                String image=comment.getUser().getUserImage();
                String email =comment.getUser().getEmail();
                
                JSONObject user =new JSONObject();
                
                user.put("Userid", userId);
                user.put("usernam",usename );
                user.put("rank",rank );
                user.put("userimage",image );
                user.put("email",email );
                Date date=comment.getCommentDate();
                String text=comment.getCommentText();
                
                JSONObject commentobj = new JSONObject();
                commentobj.put("id", commentId);
                commentobj.put("CommentOwner", user);
                commentobj.put("CommentText", text);
                commentobj.put("date", date);
                allComments.put(i, commentobj);
                
                i++;
            }
            
            JSONObject jsonOutput = new JSONObject();
            jsonOutput.put("HasError", false);
            jsonOutput.put("HasWarning", false);
            jsonOutput.put("FaultsMsg", "");
            jsonOutput.put("ResponseValue",allComments);
            
            return  jsonOutput.toString();
            }
            else
                return new ApplicatipnUtil().jsonException("this even's id t not found");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("you have json Exception ");
        }
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addComment")
    public String addComment(String input) {
        try {
            
            JSONObject commentJson = new JSONObject(input);
            CommentDAO cdao = new CommentDAO();
            Comment comment = new CommentUtil().convertFromJsonToJava(commentJson);
            
            JSONObject jsonOutput=new JSONObject();
            JSONObject commentId=new JSONObject();
            if(comment!=null)
            {
                boolean b =cdao.addComment(comment);
                if(b)
                {
                    Comment output=cdao.retrieveCommentbyExample(comment);
                    commentId.put("commentID", output.getId());
                    jsonOutput.put("HasError", false);
                    jsonOutput.put("HasWarning", false);
                    jsonOutput.put("FaultsMsg", "");
                    jsonOutput.put("ResponseValue",commentId);
                    
                    return jsonOutput.toString();
                }
                else
                {
                    return new ApplicatipnUtil().jsonException("you comment not added");
                }
            }
            else
                return new ApplicatipnUtil().jsonException("problem with json you send");
            
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("you have json Exception ");
        }
    }
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/deleteComments")
    public String deleteComment(String input){
        
        try {
            CommentDAO cdao= new CommentDAO();
            JSONObject commentJson= new JSONObject(input);
            int id=commentJson.getInt("id");
            
            Comment comment = cdao.retrieveComment(id);
            System.out.println(comment);
            JSONObject jsonOutput=new JSONObject();
            JSONObject commentjson=new JSONObject();
            if(comment!=null)
            {
                boolean b =cdao.deleteComment(comment);
                if(b)
                {
                    commentjson.put("isDeleted", "true");
                    jsonOutput.put("HasError", false);
                    jsonOutput.put("HasWarning", false);
                    jsonOutput.put("FaultsMsg", "");
                    jsonOutput.put("ResponseValue",commentjson);
                    return jsonOutput.toString();
                }
                else
                    return new ApplicatipnUtil().jsonException("you comment not deleted");                
            }
            else
                return new ApplicatipnUtil().jsonException("This comment not found in database");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return new ApplicatipnUtil().jsonException("you have json Exception ");
        }
    }
}
