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

import app.components.RoomComponent;
import app.entities.Room;

@Component
@Path("/room")
public class RoomController {
	
	@Autowired
	RoomComponent rc;
	
	@GET
	@Path("/retrieve")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Room getSubject(@QueryParam("p") Long pk) {
		return rc.findRoom(pk);
	}
	
	@GET
	@Path("/retrieveAll")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Room> getSubjectAll() {
		return rc.findRoomAll();
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public Room addRoom(
							  @FormParam("rn") String roomNumber){
		
			return rc.addRoom(roomNumber);	
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public Room updateRoom(
							  @FormParam("pk") Long pk,
							  @FormParam("rn") String roomNumber) throws Exception{
		
		return rc.updateRoom(pk, roomNumber);
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteRoom(@QueryParam("pk") Long pk) {
	    
		return rc.deleteRoom(pk);
	    
	}
	
	
	
}