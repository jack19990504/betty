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

import com.activity.controller.rest.RegistrationController;
import com.activity.dao.RegistrationDAO;
import com.activity.engine.util.AttributeCheck;
import com.activity.entity.Member;
import com.activity.entity.Registration;
import com.activity.util.AuthenticationUtil;
import com.activity.util.WebResponse;
import com.google.gson.Gson;

@Path("/registration")

@RestController

public class RegistrationController {

	@Autowired
	RegistrationDAO registrationDAO;

	@CrossOrigin
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(Registration registration) {
		final AttributeCheck attributeCheck = new AttributeCheck();
		final WebResponse webResponse = new WebResponse();
		final AuthenticationUtil authUtil = new AuthenticationUtil();

		Registration tRegistration = new Registration();

		tRegistration.setMember_Email(registration.getMember_Email());
		tRegistration.setActivity_Id(registration.getActivity_Id());
		tRegistration = registrationDAO.getOneRegistration(tRegistration);
		//檢查此使用者是否報名過此活動，如是則顯示錯誤訊息
		if (attributeCheck.stringsNotNull(tRegistration.getMember_Email())) {

			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("You have already registered this activity!");

		} 
		else {
			//檢查使用者在近一個月內取消報名的次數
			Member member = new Member();
			member.setMemberEmail(authUtil.getCurrentUsername());
			Integer cancelTimes = registrationDAO.getUserCancelTimeInMonth(member);
			System.out.println(member.getMemberEmail() + "\t" + cancelTimes);
			//如超過三次則無法報名
			if (cancelTimes >= 3) {
				webResponse.UNPROCESSABLE_ENTITY();
				webResponse.setData("You cancel 3 registrations within a month! \n please wait until next month!");
			} else {
				registrationDAO.insert(registration);
				webResponse.OK();
				webResponse.setData(registration);
			}
		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		List<Registration> registrationList = registrationDAO.getList();
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(registrationList)).build();
	}

	@GET
	@Path("/activity/{Getid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActivityList(@PathParam("Getid") int id) {
		List<Registration> registrationList = registrationDAO.getActivityList(id);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(registrationList)).build();
	}

	@PATCH
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Registration registration) {
		registration.setAInum(id);
//		System.out.println(id + "   " + activityID);
//		registration.setMember_Email(id);
//		registration.setActivity_Id(activityID);

		final Registration oldRegistration = registrationDAO.get(registration);
		registrationDAO.update(oldRegistration, registration);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(registration)).build();
	}

	@DELETE
	@Path("/{AInum}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("AInum") int id) {
//		Registration registration = new Registration();
//		registration = registrationDAO.get(registration);
		registrationDAO.delete(id);
		return Response.status(200).build();
	}

	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") String id) {
		Registration registration = new Registration();
		registration.setMember_Email(id);
		registration = registrationDAO.get(registration);
		System.out.println("id=" + id);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(registration)).build();

	}

	@PATCH
	@Path("/cancel/{id}")
	public Response updateCancel(@PathParam("id") int id) {
		Registration registration = new Registration();
		registration.setAInum(id);

		registrationDAO.updateCancelTime(registration);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(registration)).build();
	}

}
