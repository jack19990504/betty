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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.activity.dao.ActivityDAO;
import com.activity.entity.Activity;
import com.activity.entity.Search;
import com.activity.util.AuthenticationUtil;
import com.activity.util.WebResponse;

@CrossOrigin("*")
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
		final WebResponse webResponse = new WebResponse();
		if (id != null) {
			Activity activity = new Activity();
			activity.setActivityOrganizer(id);
			final List<Activity> activityList = activityDAO.getOrganizerActivityList(activity);
			if (activityList != null) {
				webResponse.OK();
				webResponse.setData(activityList);
			} else {
				webResponse.NOT_FOUND();
				webResponse.getError().setMessage("查無資料!");
			}
		} else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("請輸入主辦單位!");
		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();

	}

	@GET
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActivitySearch(Search search) {
		final WebResponse webResponse = new WebResponse();
		if (search.getSearch() != null) {
			List<Activity> activityList = activityDAO.getActivitySearch(search);
			webResponse.OK();
			webResponse.setData(activityList);
		} else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.getError().setMessage("請輸入搜尋字串");
		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

//	@GET
//	@Path("/organizer/search")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getOrganizerSearch(Activity activity) {
//		
//		List<Organizer> organizerList = activityDAO.getOrganizerSearch(activity);		
//		
//		return Response.status(200).entity(organizerList).build();
//	}

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

	// @CrossOrigin(origins = "http://localhost:3000")
	@PATCH
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Integer id, Activity activity) {
		final WebResponse webResponse = new WebResponse();

		System.out.println("patch");
		System.out.println(activity.getActivityMoreContent() + "\t" + activity.getActivityId() + "\t"
				+ activity.getActivityPrecautions());
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
				webResponse.getError().setMessage("查無資料!");
			}
		} else {
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
		final AuthenticationUtil auth = new AuthenticationUtil();
		activity.setActivityOrganizer(auth.getCurrentUsername());
		activityDAO.insert(activity);
		webResponse.OK();
		activity = activityDAO.getActivityByCols(activity);
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

	@POST
	@Path("/files/{actId}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response hello(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, @PathParam("actId") Integer id,
			Activity activity) {
		String uploadedFileLocation = "C:\\Users\\jack1\\Desktop\\test\\react_pages\\public\\assets\\images\\";
		File file = new File(uploadedFileLocation);
		if (!file.exists()) {
			file.mkdirs();
		}
		String fileName = fileDetail.getFileName();
		String test = fileName.substring(fileName.lastIndexOf("."));
		String out = "";
		if (test.equalsIgnoreCase(".jpg") || test.equalsIgnoreCase(".jpeg") || test.equalsIgnoreCase(".png")) {
			writeToFile(uploadedInputStream, uploadedFileLocation + fileName);

			 out = "File uploaded to : " + uploadedFileLocation + "side = " + test;

			final Activity oldactivity = activityDAO.get(activity);
			String picPath = "public\\assets\\images\\" + fileName;
			activity.setActivityCover(picPath);
			activity.setActivityId(id);
			activityDAO.update(oldactivity, activity);
		}
		else
		{
			out = "no";
		}
		// save it

		return Response.status(200).entity(out).build();
	}

	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			//out = new FileOutputStream(new File(uploadedFileLocation));
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