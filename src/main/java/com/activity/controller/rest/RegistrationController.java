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

import com.activity.controller.rest.RegistrationController;
import com.activity.dao.RegistrationDAO;
import com.activity.dao.Impl.RegistrationDAOImpl;
import com.activity.entity.Registration;
import com.google.gson.Gson;

@Path("/registration")

@Controller

public class RegistrationController {
	
	@Autowired
	RegistrationDAO registrationDAO;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(Registration registration) {
		

		registrationDAO.insert(registration);

		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(registration)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		List<Registration> registrationList = registrationDAO.getList();
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(registrationList)).build();
	}

	@PATCH
	@Path("/{Patchid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("Patchid") String id, Registration registration) {
		
		registration.setMember_Email(id);
		final Registration oldRegistration = registrationDAO.get(registration);
		registrationDAO.update(oldRegistration, registration);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(registration)).build();
	}
	
	@DELETE
	@Path("/{Deleteid}")
	public Response delete(@PathParam("Deleteid") String id) {
		
		Registration registration = new Registration();
		registration.setMember_Email(id);
		registration = registrationDAO.get(registration);
		registrationDAO.delete(registration);
		return Response.status(200).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") String id) {
		Registration registration = new Registration();
		registration.setMember_Email(id);
		registration = registrationDAO.get(registration);
		System.out.println("id="+id);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(registration)).build();
		
	}
	
}
