package com.activity.controller.rest;

import java.util.ArrayList;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.activity.controller.rest.OrganizerController;
import com.activity.dao.OrganizerDAO;
import com.activity.dao.Impl.OrganizerDAOImpl;
import com.activity.entity.Organizer;
import com.google.gson.Gson;

@Path("/organizer")

@Controller

public class OrganizerController {

	@Autowired
	OrganizerDAO organizerDAO;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(Organizer organizer) {
		

		organizerDAO.insert(organizer);

		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(organizer)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		List<Organizer> organizerList = organizerDAO.getList();
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(organizerList)).build();
	}
	
	@PATCH
	@Path("/{Patchid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("Patchid") String id, Organizer organizer) {
		
		organizer.setMemberEmail(id);
		final Organizer oldOrganizer = organizerDAO.get(organizer);
		organizerDAO.update(oldOrganizer, organizer);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(organizer)).build();
	}
	
	@DELETE
	@Path("/{Deleteid}")
	public Response delete(@PathParam("Deleteid") String id) {
		
		Organizer organizer = new Organizer();
		organizer.setMemberEmail(id);
		organizer = organizerDAO.get(organizer);
		organizerDAO.delete(organizer);
		return Response.status(200).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") String id) {
		Organizer organizer = new Organizer();
		organizer.setMemberEmail(id);
		organizer = organizerDAO.get(organizer);
		System.out.println("id="+id);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(organizer)).build();
		
	}
}
