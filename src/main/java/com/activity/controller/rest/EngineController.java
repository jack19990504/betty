package com.activity.controller.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.activity.dao.MemberDAO;
import com.activity.engine.control.EngineFunc;
import com.activity.engine.control.GetResult;
import com.activity.engine.entity.Face;
import com.activity.engine.entity.RecognizeFace;
import com.activity.engine.util.AttributeCheck;
import com.activity.engine.util.CmdUtil;
import com.activity.engine.util.FileUtil;
import com.activity.entity.Member;
import com.activity.util.WebResponse;

@Path("/engine")
@RestController
public class EngineController {

	@Autowired
	MemberDAO memberDAO;

	static String enginePath = "C:\\Users\\jack1\\Desktop\\face\\Engine";
	static String outputFacePath = "face";
	static String outputFramePath = "frame";
	static String trainedBinaryPath = "eGroup\\jack_kobe.Model.binary";
	static String trainedFaceInfoPath = "eGroup\\jack_kobe.Model.faceInfo";
	static String jsonPath = "output\\output";
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
	public void closed() {
		CmdUtil cmdUtil = new CmdUtil();
		cmdUtil.cmdProcessTerminate("RecognizeFace.exe");
	}

	@GET
	@Path("/result")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response result() {
		// init func
		AttributeCheck attributeCheck = new AttributeCheck();
		WebResponse webResponse = new WebResponse();
		GetResult getResult = new GetResult();
		List<Face> faceList = getResult.photoResult(resultJsonPath, jsonName, true);
		int length = faceList.size() - 2;
		System.out.println(length);
		for (int i = length; i > 0; i--) {
			Face face = faceList.get(i);
			if (face.getHasFound().equals("1")) {
				final String personId = face.getPersonId();
				if (attributeCheck.stringsNotNull(personId)) {
					Member member = new Member();
					member.setMemberEmail(personId);
					member = memberDAO.get(member);
					if (attributeCheck.objectNotNull(member)) {
						webResponse.OK();
						webResponse.setData(member);
						return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
					}
				}

			}
		}
		return Response.status(200).entity(faceList).build();
	}

}
