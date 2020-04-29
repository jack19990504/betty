package com.activity.controller.rest;

import java.io.File;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.OpenOption;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.activity.dao.MemberDAO;
import com.activity.dao.PhotoDAO;
import com.activity.dao.RegistrationDAO;
import com.activity.engine.control.EngineFunc;
import com.activity.engine.control.GetResult;
import com.activity.engine.entity.Face;
import com.activity.engine.entity.RecognizeFace;
import com.activity.engine.util.AttributeCheck;
import com.activity.engine.util.CmdUtil;
import com.activity.entity.Member;
import com.activity.util.WebResponse;

@CrossOrigin("*")
@Path("/engine")
@RestController
public class EngineController {

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	PhotoDAO photoDAO;

	@Autowired
	RegistrationDAO registrationDAO;

	static String enginePath = "C:\\Users\\jack1\\Desktop\\face\\Engine";
	static String outputFacePath = "face";
	static String outputFramePath = "frame";
	static String trainedBinaryPath = "eGroup\\jack_kobe.Model.binary";
	static String trainedFaceInfoPath = "eGroup\\jack_kobe.Model.faceInfo";
	static String jsonPath = "output\\";
	static String cam = "0";

	static String resultJsonPath = "C:\\Users\\jack1\\Desktop\\face\\Engine\\output";
	static String jsonName = "output.cache.egroup";

	@GET
	public Response openRec() {
		// open cam
		EngineFunc engineFunc = new EngineFunc();
		RecognizeFace recognizeFace = new RecognizeFace();
		recognizeFace.setEnginePath(enginePath);
		recognizeFace.setOutputFacePath(outputFacePath);
		recognizeFace.setOutputFramePath(outputFramePath);
		recognizeFace.setTrainedBinaryPath(trainedBinaryPath);
		recognizeFace.setTrainedFaceInfoPath(trainedFaceInfoPath);
		recognizeFace.setJsonPath(jsonPath);
		recognizeFace.setCam(cam);
		recognizeFace.setHideMainWindow(false);
		engineFunc.recognizeFace(recognizeFace);

		return Response.status(200).build();

	}

	@POST
	@Path("/rec/{activityId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRec(@PathParam("activityId") Integer id) {
		WebResponse webResponse = new WebResponse();

		EngineFunc engineFunc = new EngineFunc();
		RecognizeFace reco = new RecognizeFace();
		File file = new File(enginePath + "/" + id + ".egroupList");
		if (file.exists())// 檢測檔案是否存在
		{
			reco.setPhotoListPath(id + ".egroupList");
			reco.setEnginePath(enginePath);
			reco.setOutputFacePath(outputFacePath);
			reco.setThreshold(0.65);
			reco.setThreads(3);
			reco.setResolution("1440p");
			reco.setOutputFramePath(outputFramePath);
			reco.setTrainedBinaryPath(trainedBinaryPath);
			reco.setTrainedFaceInfoPath(trainedFaceInfoPath);
			reco.setMinimumFaceSize(25);
			reco.setJsonPath(jsonPath + id);

			boolean isdone = engineFunc.recoFaceWithPhotoList(reco);

			if (isdone) {
				webResponse.OK();
				webResponse.setData("reco successfully");
			} else {
				webResponse.BAD_REQUEST();
				webResponse.setData("reco failed");
			}
		} else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("the list is not exist!");
		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@POST
	public void closed() {
		CmdUtil.cmdProcessTerminate("RecognizeFace.exe");
	}

	@GET
	@Path("/result")
	@Produces(MediaType.APPLICATION_JSON)
	public Response result() {
		// init func
		
		while (true) {
			List<Face> faceList = GetResult.photoResult(resultJsonPath, ".cache.egroup", true);
			System.out.println(faceList.size());
			Member member ;
			for (int i = faceList.size()-2;i > 0;i--) {
				Face face = faceList.get(i);
				if (!face.equals(null) && face.getHasFound().equals("1")) {
					System.out.println("test");
					member = new Member();
					member.setMemberEmail(face.getPersonId());
					member = memberDAO.get(member);
					System.out.println(member.getMemberEmail());
					if(member.getMemberPassword()!= null )
					{
						return Response.status(200).entity(member).build();
					}
						
				}
			}
			
		}

	}

	@GET
	@Path("/signIn/{activityId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response signInRegistrationWithFace(@PathParam("activityId") Integer id) {
		Member member;
		while (true) {
			List<Face> faceList = GetResult.photoResult(resultJsonPath, jsonName, true);

			for (int i = faceList.size()-2;i > 0;i--) {
				Face face = faceList.get(i);
				if (!face.equals(null) && face.getHasFound().equals("1")) {
					
					System.out.println("test");
					member = new Member();
					member.setMemberEmail(face.getPersonId());
					member = memberDAO.get(member);
					System.out.println(member.getMemberEmail());
					
					if(member.getMemberPassword()!= null )
					{
						return Response.status(200).entity(member).build();
					}
						
				}
			}

		}

	}

	@GET
	@Path("/writePhoto")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPhotoRecord() {
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

}
