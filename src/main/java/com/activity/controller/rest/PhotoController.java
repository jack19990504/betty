package com.activity.controller.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.activity.dao.Impl.PhotoDAOImpl;
import com.activity.engine.control.GetResult;
import com.activity.engine.entity.Face;
import com.activity.engine.util.AttributeCheck;
import com.activity.util.WebResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

@RestController
@Path("/photo")
public class PhotoController {

	@Autowired
	PhotoDAOImpl photoDAO;

	static String enginePath = "C:\\Users\\jack1\\Desktop\\face\\Engine";
	static String outputFacePath = "face";
	static String outputFramePath = "frame";
	static String trainedBinaryPath = "eGroup\\jack_kobe.Model.binary";
	static String trainedFaceInfoPath = "eGroup\\jack_kobe.Model.faceInfo";
	static String jsonPath = "output\\output";

	static String resultJsonPath = "C:\\Users\\jack1\\Desktop\\face\\Engine\\output";
	static String jsonName = "output.cache.egroup";

	@GET
	@Path("/writeMemberPhoto")
	@Produces(MediaType.APPLICATION_JSON)
	public Response writeMemberPhoto() {
		AttributeCheck attributeCheck = new AttributeCheck();
		WebResponse webResponse = new WebResponse();
		List<Face> faceList = GetResult.photoResult(resultJsonPath, jsonName, true);
		if (faceList.size() == 0) {
			webResponse.setData("no faces in the result!");
			webResponse.UNPROCESSABLE_ENTITY();
		} else {
			for (Face face : faceList) {
				if (face.getHasFound().equals("1")) {
					photoDAO.writePhoto(face);
				}
			}
			webResponse.OK();
			webResponse.setData(faceList);
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@GET
	@Path("/writeActivtiyPhoto/{path}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPhotoRecord(@PathParam("path") String path) throws FileNotFoundException, IOException{
		AttributeCheck attributeCheck = new AttributeCheck();
		WebResponse webResponse = new WebResponse();
		boolean issuccess = readfile(path);
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
		try {

			File file = new File(filepath);
			if (!file.isDirectory()) {
				System.out.println("檔案");
				System.out.println("path=" + file.getPath());
				System.out.println("absolutepath=" + file.getAbsolutePath());
				System.out.println("name=" + file.getName());

			} else if (file.isDirectory()) {
				System.out.println("資料夾");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "//" + filelist[i]);
					if (!readfile.isDirectory()) {
						System.out.println("path=" + readfile.getPath());
						System.out.println("absolutepath=" + readfile.getAbsolutePath());
						System.out.println("name=" + readfile.getName());

					} else if (readfile.isDirectory()) {
						readfile(filepath + "//" + filelist[i]);
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return true;
	}

}
