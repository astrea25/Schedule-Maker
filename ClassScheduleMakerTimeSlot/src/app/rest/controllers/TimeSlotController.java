package app.rest.controllers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.components.ScraperComponent;

@Component
@Path("/timeSlot")
public class TimeSlotController {
	
	@Autowired
	ScraperComponent scraperComponent;
	
	@POST
	@Path("/runScraper")
	@Produces(MediaType.APPLICATION_JSON)
	public String runScraper() {
		try {
			scraperComponent.scrapeAllDepartments();
			return "Successfully scraped AISIS.";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error occurred";
		}
	}
}
