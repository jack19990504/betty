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
import com.activity.engine.util.AttributeCheck;
import com.activity.entity.Feedback;
import com.activity.util.WebResponse;

@Path("/feedback")
@CrossOrigin("*") 
@RestController
public class FeedbackController {
	
	@Autowired
	FeedbackDAO feedbackDAO;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(Feedback feedback) {
		final WebResponse webResponse = new WebResponse();
		final AttributeCheck attributeCheck = new AttributeCheck();	
			
			Feedback oldFeedback = feedback;
			oldFeedback = feedbackDAO.getOne(oldFeedback);
			if(attributeCheck.stringsNotNull(oldFeedback.getSuggestFeedback()))
			{
				feedbackDAO.insert(feedback);
				webResponse.OK();
				webResponse.setData(feedback);
			}
			else
			{
				webResponse.UNPROCESSABLE_ENTITY();
				webResponse.setData("您已經填寫過此活動的回饋單!");
			}
			
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@GET
	@Path("/activity/{Getid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActivityList(@PathParam("Getid") Integer id) {
		final WebResponse webResponse = new WebResponse();
		if(id != null) {
			List<Feedback> feedbackList = feedbackDAO.getActivityFeedback(id);
			if (feedbackList != null) {
				webResponse.OK();
				webResponse.setData(feedbackList);
			}
			else {
				webResponse.NOT_FOUND();
				webResponse.getError().setMessage("查無資料!");
			}
		}
		else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("請輸入活動代碼");
		}
		
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") Integer id) {
		final WebResponse webResponse = new WebResponse();
		if(id != null) {
			Feedback feedback = new Feedback();
			feedback.setAInum(id);
			feedback = feedbackDAO.get(feedback);
			if(feedback.getMember_Email() != null) {
				webResponse.OK();
				webResponse.setData(feedback);
			}
			else {
				webResponse.NOT_FOUND();
				webResponse.getError().setMessage("查無資料!");
			}
		}
		else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("id required");
		}
		System.out.println("id="+id);
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
		
	}

}
