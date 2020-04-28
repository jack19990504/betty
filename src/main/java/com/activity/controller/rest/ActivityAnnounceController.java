package com.activity.controller.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.activity.dao.ActivityAnnounceDAO;
import com.activity.entity.ActivityAnnounce;
import com.activity.util.AuthenticationUtil;
import com.activity.util.WebResponse;
import com.google.gson.Gson;

@Path("/activityannounce")
@RestController
public class ActivityAnnounceController {
	
	@Autowired
	ActivityAnnounceDAO activityAnnounceDAO;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList() {
		final WebResponse webResponse = new WebResponse();
		
			List<ActivityAnnounce> ActivityAnnounceList = activityAnnounceDAO.getList();
			webResponse.OK();
			webResponse.setData(ActivityAnnounceList);
			
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") Integer id) {
		final WebResponse webResponse = new WebResponse();
		if(id != null) {
			ActivityAnnounce activityAnnounce = new ActivityAnnounce();
			activityAnnounce.setAInum(id);
			activityAnnounce = activityAnnounceDAO.get(activityAnnounce);
			if(activityAnnounce.getAnnounceTitle() != null) {
				webResponse.OK();
				webResponse.setData(activityAnnounce);
			}
			else {
				webResponse.NOT_FOUND();
				webResponse.getError().setMessage("data not found");
			}
		}
		else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("id required");
		}
		
		System.out.println("id="+id);
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(ActivityAnnounce activityAnnounce) {
		final WebResponse webResponse = new WebResponse();
			activityAnnounceDAO.insert(activityAnnounce);
			webResponse.OK();
			webResponse.setData(activityAnnounce);
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@PATCH
	@Path("/{Patchid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("Patchid") Integer id, ActivityAnnounce activityAnnounce) {
		final WebResponse webResponse = new WebResponse();
		if(id != null) {
			activityAnnounce.setAInum(id);
			final ActivityAnnounce oldActivityAnnounce = activityAnnounceDAO.get(activityAnnounce);
			if(oldActivityAnnounce.getAnnounceTitle() != null) {
				activityAnnounceDAO.update(oldActivityAnnounce, activityAnnounce);
				activityAnnounce = activityAnnounceDAO.get(activityAnnounce);
				webResponse.OK();
				webResponse.setData(activityAnnounce);
			}
			else {
				webResponse.NOT_FOUND();
				webResponse.getError().setMessage("Data not found");
			}
		}
		else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("id required");
		}
		
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@DELETE
	@Path("/{AInum}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("AInum") Integer id) {
//		ActivityAnnounce activityAnnounce = new ActivityAnnounce();
//		activityAnnounce = activityAnnounceDAO.get(activityAnnounce);
		final WebResponse webResponse = new WebResponse();
		if(id != null) {
			ActivityAnnounce  activityAnnounce = new ActivityAnnounce();
			activityAnnounce.setAInum(id);
			activityAnnounce = activityAnnounceDAO.get(activityAnnounce);
			if(activityAnnounce.getAnnounceTitle() != null) {
				activityAnnounce.setAInum(id);
				activityAnnounceDAO.delete(activityAnnounce);
				webResponse.OK();
				webResponse.setData(activityAnnounce);
			}
			else {
				webResponse.NOT_FOUND();
				webResponse.getError().setMessage("Data not found");
			}
		}
		else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("id required");
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	
	
}
