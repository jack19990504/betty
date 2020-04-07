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
		List<ActivityAnnounce> ActivityAnnounceList = activityAnnounceDAO.getList();
		return Response.status(200).entity(ActivityAnnounceList).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") int id) {
		ActivityAnnounce activityAnnounce = new ActivityAnnounce();
		activityAnnounce.setAInum(id);
		activityAnnounce = activityAnnounceDAO.get(activityAnnounce);
		System.out.println("id="+id);
		return Response.status(200).entity(activityAnnounce).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(ActivityAnnounce activityAnnounce) {
		

		activityAnnounceDAO.insert(activityAnnounce);

		return Response.status(200).entity(activityAnnounce).build();
	}
	
	@PATCH
	@Path("/{Patchid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("Patchid") int id, ActivityAnnounce activityAnnounce) {
		
		activityAnnounce.setAInum(id);
		final ActivityAnnounce oldActivityAnnounce = activityAnnounceDAO.get(activityAnnounce);
		activityAnnounceDAO.update(oldActivityAnnounce, activityAnnounce);
		return Response.status(200).entity(activityAnnounce).build();
	}
	
	@DELETE
	@Path("/{AInum}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("AInum") int id,ActivityAnnounce activityAnnounce) {
//		ActivityAnnounce activityAnnounce = new ActivityAnnounce();
//		activityAnnounce = activityAnnounceDAO.get(activityAnnounce);
		activityAnnounceDAO.delete(id);
		return Response.status(200).build();
	}

	
	
}
