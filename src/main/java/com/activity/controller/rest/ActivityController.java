package com.activity.controller.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
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
		final AuthenticationUtil authUtil = new AuthenticationUtil();
		
			if (id != null) {
				Activity activity = new Activity();
				activity.setActivityId(id);
				
				activity = activityDAO.get(activity);
				if (activity.getActivityName() != null) {
					activityDAO.getActivityTypes(activity);
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
	
	@GET
	@Path("/organizer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrganizerActivity(@PathParam("id") String id) {
		
				Activity activity = new Activity();
				activity.setActivityOrganizer(id);
				
				final List<Activity> activityList = activityDAO.getOrganizerActivityList(activity);

				return Response.status(200).entity(activityList).build();
	}
	

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") Integer id) {
		final WebResponse webResponse = new WebResponse();
		final AuthenticationUtil authUtil = new AuthenticationUtil();
		if (authUtil.checkAuthority()) {
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
		} else {
			webResponse.UNAUTHORIZED();
			webResponse.setData("authentication failed!");
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@PATCH
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Integer id, Activity activity) {
		final WebResponse webResponse = new WebResponse();
		final AuthenticationUtil authUtil = new AuthenticationUtil();
		if (authUtil.checkAuthority()) {
			if (id != null) {
				activity.setActivityId(id);
				final Activity oldactivity = activityDAO.get(activity);
				if (oldactivity.getActivityId() != null) {
					activityDAO.update(oldactivity, activity);
					activity.setActivityId(id);
					activity = activityDAO.get(activity);
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
		} else {
			webResponse.UNAUTHORIZED();
			webResponse.setData("authentication failed!");
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(Activity activity) {
		final WebResponse webResponse = new WebResponse();
		final AuthenticationUtil authUtil = new AuthenticationUtil();
		if (authUtil.checkAuthority()) {
			activityDAO.insert(activity);
			webResponse.OK();
			webResponse.setData(activity);
		} else {
			webResponse.UNAUTHORIZED();
			webResponse.setData("authentication failed!");
		}
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
	
	@POST
    @Path("/files/{actId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response hello(@FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,@PathParam("actId") Integer id,Activity activity)
    {
    	String uploadedFileLocation = "d://upload/" + fileDetail.getFileName();
    	String fileName = fileDetail.getFileName();
    	String test = fileName.substring(fileName.length()-4, fileName.length());
        // save it
        writeToFile(uploadedInputStream, uploadedFileLocation);

        String output = "File uploaded to : " + uploadedFileLocation + "side = " + test;
        activity.setActivityCover(output);
        activity.setActivityId(id);
        activityDAO.updateCover(activity);
        return Response.status(200).entity(output).build();
    }
	
	private void writeToFile(InputStream uploadedInputStream,
            String uploadedFileLocation) {

            try {
                OutputStream out = new FileOutputStream(new File(
                        uploadedFileLocation));
                int read = 0;
                byte[] bytes = new byte[1024];

                out = new FileOutputStream(new File(uploadedFileLocation));
                while ((read = uploadedInputStream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }

}