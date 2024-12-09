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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.InstructorComponent;
import app.entities.Instructor;

@Component
@Path("/instructor")
public class InstructorController {
	
	@Autowired
	InstructorComponent ic;
	
	@GET
	@Path("/retrieve")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Instructor getInstructor(@QueryParam("p") Long pk) {
		return ic.findInstructor(pk);
	}
	
	@GET
	@Path("/retrieveAll")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Instructor> getInstructorAll() {
		return ic.findInstructorAll();
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public Instructor addInstructor(
							  @FormParam("n") String name) throws Exception{
		
			return ic.addInstructor(name);	
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public Instructor updateInstructor(
							  @FormParam("pk") Long pk,
							  @FormParam("n") String name) throws Exception{
		
		return ic.updateInstructor(pk, name);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteInstructor(@QueryParam("pk") Long pk) {
	    
		return ic.deleteInstructor(pk);
	    
	}
	
	
	
}