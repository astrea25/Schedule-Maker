package app.rest.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import app.components.MessageTwilioComponent;
import app.components.TwilioReply;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.TwilioReply;
import app.components.UserComponent;
import app.entities.User;

@Component
@Path("/user")
public class UserController {

    @Autowired
    private UserComponent uc;
    
    @Autowired 
    private MessageTwilioComponent mtc;
    
    
//    @GET
//    @Path("/addSectionTwilio")
//    @Produces(MediaType.APPLICATION_JSON)
//    public TwilioReply addSectionTwilio(@QueryParam("stupk") Long studentID, @QueryParam("secpk") Long sectionPk) throws Exception {
//       return this.mtc.sendMessageToUser(studentID, sectionPk);
//    }
    //

    @GET
    @Path("/retrieveAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll(){
    	return uc.getAllUsers();
    }
    
    @GET
    @Path("/retrieve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@QueryParam("pk") Long studentID) {
        return uc.findUser(studentID);
    }
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(@FormParam("un") String username, @FormParam("pw") String password, @FormParam("n") String name, @FormParam("c") String course, @FormParam("pn") String phoneNumber) {
    	return uc.createUser(username, password, name, course, phoneNumber);
    }
    
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(@FormParam("pk") Long studentID, @FormParam("n") String name, @FormParam("c") String course, @FormParam("pn") String phoneNumber) {
    	return uc.updateUser(studentID, name, course, phoneNumber);
    }
    
    @POST
    @Path("/addSection")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public User addSection(@FormParam("stupk") Long studentID, @FormParam("secpk") Long sectionPk) throws Exception {
    	return uc.addSection(studentID, sectionPk);
    }
    
    @DELETE
    @Path("/deleteSection")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User deleteSection(@QueryParam("stupk") Long studentID, @QueryParam("secpk") Long sectionPk) throws Exception {
    	return uc.deleteSection(studentID, sectionPk);
    }
    
    @PUT
    @Path("/updateSection")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public User updateSection(@FormParam("stupk") Long studentID, @FormParam("oldsec") Long oldSectionPk, @FormParam("newsec") Long newSectionPk) {
    	return uc.updateSection(studentID, oldSectionPk, newSectionPk);
    }
    
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteUser(@QueryParam("pk") Long studentID) {
    	return uc.deleteUser(studentID);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@FormParam("un") String username, @FormParam("pw") String password) {
        return uc.login(username, password);
    }
    
    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response logoutUser(@FormParam("sid") String sessionId) {
    	try {
            uc.logout(sessionId);
            return Response.ok("Logout successful").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage())
                           .build();
        }
    }

    @GET
    @Path("/validate")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public User validateSession(@QueryParam("sid") String sessionId) {
        return uc.getUserFromSession(sessionId);
    }
}
