package com.activity.controller.rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.activity.engine.control.EngineFunc;
import com.activity.engine.util.FileUtil;
import com.activity.util.WebResponse;

@MultipartConfig
@RestController
@Path("/TrainFace")
public class TrainFaceController {

	static String UPLOADED_Photo_Train_PATH = "C:\\Users\\jack1\\Desktop\\face\\Engine\\output";

	 @POST
	 //@Path("/TrainFace/{userId}")
	 @Consumes(MediaType.MULTIPART_FORM_DATA)
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response TrainNewModel(MultipartFormDataInput multipartFormDataInput, @PathParam("userId") String userId) throws IOException {
	  //final HttpSession session = request.getSession();
	  WebResponse webResponse = new WebResponse();
	  FileUtil fileUtil = new FileUtil();
	  String photoName = "";
	  // Get the file
	  Map<String, List<InputPart>> uploadForm = multipartFormDataInput.getFormDataMap();
	  List<InputPart> inputParts = uploadForm.get("photos");
	  FileOutputStream fileOutputStream;
	  ArrayList<String> photosNameList = new ArrayList<>();
	  for (InputPart inputPart : inputParts) {
	   MultivaluedMap<String, String> header = inputPart.getHeaders();
	   photoName = fileUtil.getFileName(header);

	   // here
	   photosNameList.add(photoName);// LIST NAME
	   // here

	   // convert the uploaded file to inputstream
	   InputStream inputStream;
	   try {
	    inputStream = inputPart.getBody(InputStream.class, null);
	    byte[] bytes = toByteArray(inputStream);
	    File file = new File(UPLOADED_Photo_Train_PATH + photoName);
	    if (!file.getParentFile().exists()) {
	     file.getParentFile().mkdirs();
	    }
	    file.createNewFile();
	    fileOutputStream = new FileOutputStream(file);
	    fileOutputStream.write(bytes);
	   } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	   }

	  }
	  // 回傳名字陣列至entity
//	  TrainModel trainModel = new TrainModel();
//	  trainModel.setPhotosNameList(photosNameList);
//	  trainModel.setUserId(userId);
//
//	  // init func
//	  UpdateTxtImpl trainRetxtImpl = new UpdateTxtImpl();
//	  trainRetxtImpl.updatetxtTrain(trainModel);
//
//	  EngineFunc enginefunc = new EngineFunc();
//	  enginefunc.trainface(null);
//	  
//	  //DELETE list.txt
//	  delFile(UPLOADED_Photo_TrainList_PATH);
	    
	  webResponse.OK();
	  // webResponse.setData(trainface);
	  return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	 }
	 public static byte[] toByteArray(final InputStream input) throws IOException {
	        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
	            IOUtils.copy(input, output);
	            return output.toByteArray();
	        }
	    }
}
