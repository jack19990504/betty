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

import com.activity.dao.ActivityDAO;
import com.activity.entity.Activity;
import com.activity.util.AuthenticationUtil;
import com.activity.util.WebResponse;

@Path("/activity")
@RestController
public class ActivityController {

	@Autowired
	ActivityDAO activityDAO;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Integer id) {
		final WebResponse webResponse = new WebResponse();
		if (id != null) {
			Activity activity = new Activity();
			activity.setActivityId(id);

			activity = activityDAO.get(activity);
			if (activity.getActivityName() != null) {
				webResponse.OK();
				webResponse.setData(activity);
			} else {
				webResponse.NOT_FOUND();
				webResponse.getError().setMessage("找不到資料!");
			}
		} else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("請輸入活動ID!");
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") Integer id) {
		final WebResponse webResponse = new WebResponse();
		if (id != null) {
			Activity activity = new Activity();
			activity.setActivityId(id);

			activity = activityDAO.get(activity);
			if (activity.getActivityId() != null) {
				activity.setActivityId(id);
				activityDAO.delete(activity);
				webResponse.OK();
				webResponse.setData(activity);
			} else {
				webResponse.NOT_FOUND();
				webResponse.getError().setMessage("找不到資料!");
			}
		} else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("請輸入活動ID!");
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	@PATCH
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Integer id, Activity activity) {
		final WebResponse webResponse = new WebResponse();
		if (id != null) {
			activity.setActivityId(id);
			final Activity oldactivity = activityDAO.get(activity);
			if(oldactivity.getActivityId() != null)
			{
				activityDAO.update(oldactivity, activity);
				activity.setActivityId(id);
				activity = activityDAO.get(activity);
				webResponse.OK();
				webResponse.setData(activity);
			}
			else
			{
				webResponse.NOT_FOUND();
				webResponse.getError().setMessage("找不到資料!");
			}
		}
		else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("請輸入活動ID!");
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(Activity activity) {
		final WebResponse webResponse = new WebResponse();
		activityDAO.insert(activity);
		webResponse.OK();
		webResponse.setData(activity);
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList() {
		final WebResponse webResponse = new WebResponse();
		final List<Activity> activityList = activityDAO.getList();
		webResponse.OK();
		webResponse.setData(activityList);
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

}