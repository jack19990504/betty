package com.activity.controller.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.activity.dao.FeedbackDAO;
import com.activity.entity.Feedback;
import com.google.gson.Gson;

@Path("/feedback")
@CrossOrigin("*") 
@RestController
public class FeedbackController {
	
	@Autowired
	FeedbackDAO feedbackDAO;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(Feedback feedback) {
		

		feedbackDAO.insert(feedback);

		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(feedback)).build();
	}
	
	@GET
	@Path("/activity/{Getid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActivityList(@PathParam("Getid") int id) {
		List<Feedback> feedbackList = feedbackDAO.getActivityFeedback(id);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(feedbackList)).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") int id) {
		Feedback feedback = new Feedback();
		feedback.setAInum(id);
		feedback = feedbackDAO.get(feedback);
		System.out.println("id="+id);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(feedback)).build();
		
	}

}
