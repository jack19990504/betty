package com.activity.controller.rest;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import com.activity.engine.control.EngineFunc;
import com.activity.engine.control.GetResult;
import com.activity.engine.entity.Face;
import com.activity.engine.entity.RecognizeFace;
import com.activity.engine.util.CmdUtil;

@Path("/engine")
@RestController
public class EngineController {
	
	static String enginePath = "C:\\Users\\jack1\\Desktop\\face\\eGroupAI_FaceEngine_CPU_V4.0.0";
	static String outputFacePath = "face";
	static String outputFramePath = "frame";
	static String trainedBinaryPath = "eGroup\\jack.Model.binary";
	static String trainedFaceInfoPath = "eGroup\\jack.Model.faceInfo";
	static String jsonPath = "output\\output";
	static String cam = "0";
	
	
	static String resultJsonPath = "C:\\Users\\jack1\\Desktop\\face\\eGroupAI_FaceEngine_CPU_V4.0.0\\output";
	static String jsonName = "output.cache.egroup";
	
	@GET
	public Response openRec()
	{
		//open cam
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
	public void closed()
	{
		CmdUtil cmdUtil = new CmdUtil();
		cmdUtil.cmdProcessTerminate("RecognizeFace.exe");
	}
	
	@GET
	@Path("/result")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response result()
	{
		GetResult getResult = new GetResult();
		List<Face> faceList = getResult.cacheResult(resultJsonPath, jsonName);
		return Response.status(200).entity(faceList).build();
	}

}
