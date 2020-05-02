package com.activity.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.activity.dao.ActivityTypesDAO;
import com.activity.entity.ActivityTypes;
import com.activity.util.WebResponse;
@CrossOrigin("*") 
@Path("/activityTypes")
@RestController
public class ActivityTypesController {
	
	@Autowired
	ActivityTypesDAO activityTypesDAO;
	
	
	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertTypes(@PathParam("id") Integer id,String[] array)
	{
		final WebResponse webResponse = new WebResponse();
		
		ActivityTypes activityTypes ;
		List<ActivityTypes> aList = new ArrayList<>();
		activityTypesDAO.deleteActivityTypes(id);
		for(String type : array)
		{
			activityTypes =  new ActivityTypes();
			activityTypes.setActivityId(id);
			activityTypes.setActivityType(type);
			
			aList.add(activityTypes);
			
			activityTypesDAO.insertActivityTypes(activityTypes);
		}
		
		webResponse.OK();
		webResponse.setData(aList);
		
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTypes(@PathParam("id") Integer id)
	{
		final WebResponse webResponse = new WebResponse();
		activityTypesDAO.deleteActivityTypes(id);
		webResponse.OK();
		webResponse.setData("已成功刪除!");
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
}
