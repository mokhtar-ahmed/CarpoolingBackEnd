/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservices;

import dao.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import pojo.*;
import util.AppUtill;
/**
 *
 * @author Nourhan
 */
@Path("/comment")
public class CommentManagement {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getAllComments")
        public JSONObject retrieveEventComments(String input)
    {
        try {
            JSONObject event = new JSONObject(input);
            EventDAO edao = new EventDAO();
            int id = event.getInt("idEvent");
            Event myEvent = new Event();
            myEvent.setId(id);
            myEvent = edao.retrieveEvent(myEvent);
            Set comments=myEvent.getComments();
            Comment comment;
            JSONArray allComments = new JSONArray();
            Object[] commentss =comments.toArray();
            for(int i=0;i<comments.size();i++)
            {
                comment=(Comment)commentss[i];
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
            }
            AppUtill au = new AppUtill();
            JSONObject output=au.reponsArray(allComments);
            return output;
            } catch (JSONException ex) {
                ex.printStackTrace();
                return null;
            }
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addComment")
    public JSONObject addComment(String input)
    {
        Comment comm = new Comment();
        EventDAO edao = new EventDAO();
        UserImp udao = new UserImp();
        CommentDAo cda = new CommentDAo();
        try {
            JSONObject comment = new JSONObject(input);
            
            if(!comment.isNull("event"))
            {
             JSONObject event=comment.getJSONObject("event");
                int eventId=event.getInt("id");
                Event myEvent = new Event();
                myEvent.setId(eventId);
                myEvent=edao.retrieveEvent(myEvent);
                comm.setEvent(myEvent);
            }
            if(!comment.isNull("owner"))
            {
                JSONObject user=comment.getJSONObject("owner");
                    int userId=user.getInt("id");
                    User u=new User();
                    u.setId(userId);
                    User myUser=udao.retrieveUserById(u);
                    comm.setUser(myUser);
            }
            if(!comment.isNull("text"))
            {
                String txt= comment.getString("text");
                comm.setCommentText(txt);
            }
            if(!comment.isNull("date"))
            {
                String d = comment.getString("date");
                DateFormat df = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
                Date date = df.parse(d);
                comm.setCommentDate(date);
            }
            JSONObject jsonOutput=new JSONObject();
            JSONObject commentId=new JSONObject();
            boolean b =cda.addComment(comm);
            if(b)
            {
                Comment output=cda.retrieveComment(comm);
               commentId.put("commentID", output.getId());
               jsonOutput.put("HasError", false);
               jsonOutput.put("HasWarning", false);
               jsonOutput.put("FaultsMsg", "");
               jsonOutput.put("ResponseValue",commentId);
            }
            return  jsonOutput;
            
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
        
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/deleteComments")
    public JSONObject deleteComment(String input)
    {
        try {
            JSONObject comm= new JSONObject(input);
            int id=comm.getInt("id");
            Comment comment = new Comment();
            comment.setId(id);
            CommentDAo cdao= new CommentDAo();
            comment = cdao.retrieveComment(comment);
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
               return jsonOutput;
            }
            }
            return null;
            
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
