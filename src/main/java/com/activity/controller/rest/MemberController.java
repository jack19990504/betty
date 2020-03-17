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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.activity.controller.rest.MemberController;
import com.activity.dao.MemberDAO;
import com.activity.entity.Member;
import com.google.gson.Gson;

@Path("/member")

@RestController
//@CrossOrigin
public class MemberController {

	@Autowired
	MemberDAO memberDAO;
		
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(Member member) {
		

		memberDAO.insert(member);

		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(member)).build();
	}
	
	@CrossOrigin
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		List<Member> memberList = memberDAO.getList();
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(memberList)).build();
	}

	@PATCH
	@Path("/{Patchid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("Patchid") String id, Member member) {
		
		member.setMemberEmail(id);
		final Member oldMember = memberDAO.get(member);
		memberDAO.update(oldMember, member);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(member)).build();
	}
	
	@DELETE
	@Path("/{Deleteid}")
	public Response delete(@PathParam("Deleteid") String id) {
		
		Member member = new Member();
		member.setMemberEmail(id);
		member = memberDAO.get(member);
		memberDAO.delete(member);
		return Response.status(200).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") String id) {
//		if(id!=null) {
			Member member = new Member();
			member.setMemberEmail(id);
			member = memberDAO.get(member);
//			if(member.getMemberName() != null) {
				
//			}
//		}
		
		
		System.out.println("id="+id);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(member)).build();
		
	}
}
