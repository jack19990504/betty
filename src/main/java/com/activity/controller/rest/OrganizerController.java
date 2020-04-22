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
import org.springframework.web.bind.annotation.RestController;

import com.activity.controller.rest.OrganizerController;
import com.activity.dao.OrganizerDAO;
import com.activity.entity.Organizer;
import com.activity.entity.Search;
import com.activity.util.WebResponse;
import com.google.gson.Gson;

@Path("/organizer")

@RestController

public class OrganizerController {

	@Autowired
	OrganizerDAO organizerDAO;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(Organizer organizer) {
		final WebResponse webResponse = new WebResponse();

			organizerDAO.insert(organizer);
			webResponse.OK();
			webResponse.setData(organizer);

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		final WebResponse webResponse = new WebResponse();
		
			final List<Organizer> organizerList = organizerDAO.getList();
			webResponse.OK();
			webResponse.setData(organizerList);
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@PATCH
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") String id,Organizer organizer) {
		final WebResponse webResponse = new WebResponse();
		if(id != null) {
			organizer.setMemberEmail(id);
			final Organizer oldOrganizer = organizerDAO.get(organizer);
			if(oldOrganizer.getMemberEmail() != null) {
				organizerDAO.update(oldOrganizer, organizer);
				organizer.setMemberEmail(id);
				organizer = organizerDAO.get(organizer);
				webResponse.OK();
				webResponse.setData(organizer);
			}
			else {
				webResponse.NOT_FOUND();
				webResponse.getError().setMessage("找不到資料");
			}
		}
		else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("");
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(Organizer organizer) {
		
//		Organizer organizer = new Organizer();
//		organizer.setMemberEmail(id);
//		organizer = organizerDAO.get(organizer);
		organizerDAO.delete(organizer);
		return Response.status(200).build();
	}                     //有需要刪除主辦單位資料?
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") String id) {
		final WebResponse webResponse = new WebResponse();
		if (id != null) {
			Organizer organizer = new Organizer();
			organizer.setMemberEmail(id);
			organizer = organizerDAO.get(organizer);
			if(organizer.getOrganizerName() != null) {
				webResponse.OK();
				webResponse.setData(organizer);
			}
			else {
				webResponse.NOT_FOUND();
				webResponse.getError().setMessage("查無資料!");
			}
		}
		else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("請輸入會員帳號"); //會員帳號? 會員信箱?
		}
//		System.out.println("id="+id);
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
		
	}
	
	@GET
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizerSearch(Search search) {
		final WebResponse webResponse = new WebResponse();
		if(search.getSearch() != null) {
			List<Organizer> organizerList = organizerDAO.getOrganizerSearch(search);
			webResponse.OK();
			webResponse.setData(organizerList);
		}
		else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("請輸入搜尋字串");
		}
		
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
}
