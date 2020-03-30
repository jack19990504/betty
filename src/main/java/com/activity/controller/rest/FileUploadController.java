package com.activity.controller.rest;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import org.springframework.stereotype.Controller;

import com.activity.util.AuthenticationUtil;
import com.activity.util.WebResponse;
import com.activity.engine.control.EngineFunc;
import com.activity.engine.entity.TrainFace;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/files")
@Controller
public class FileUploadController {

	private final String dictLocation = "C:\\Users\\Morris\\Desktop\\人臉辨識引擎\\face\\engine\\resources";
	static String enginePath = "C:\\Users\\Morris\\Desktop\\人臉辨識引擎\\face\\engine";
	static String modelPath = "eGroup\\jack_kobe.Model";
	// private final String dictLocation =
	// "C:\\Users\\jack1\\Desktop\\face\\Engine\\resources";
	// static String enginePath = "C:\\Users\\jack1\\Desktop\\face\\Engine";
	// static String modelPath = "eGroup\\jack_kobe.Model";

	// 只可上傳一筆
	@POST
	@Path("/files")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response hello(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		String uploadedFileLocation = "C://upload/" + fileDetail.getFileName();
		String fileName = fileDetail.getFileName();
		String test = fileName.substring(fileName.length() - 4, fileName.length());
		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);

		String output = "File uploaded to : " + uploadedFileLocation + "side = " + test;

		return Response.status(200).entity(output).build();
	}

	// 可一次上傳多筆檔案
	@POST
	@Path("/multifiles")
	@Consumes(MediaType.MULTIPART_FORM_DATA+";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadMultipart(FormDataMultiPart multiPart) throws IOException {
		List<FormDataBodyPart> fields = multiPart.getFields("file");
		String uploadedFileLocation = "D://upload/";
		String errorMsg = "";
		final WebResponse webResponse = new WebResponse();
		for (FormDataBodyPart field : fields) {

			// get file origin name
			FormDataContentDisposition f = field.getFormDataContentDisposition();
			//轉成UTF8編碼
			String fileName = new String(f.getFileName().getBytes("ISO8859-1"),"UTF-8");
			
			String suffix = f.getFileName().substring(f.getFileName().length() - 4);
			//System.out.println(suffix);
			System.out.println(fileName);
			// if file is not a jpg or a png file
			if (!suffix.equals(".jpg") && !suffix.equals(".png") && !suffix.equals("jpeg")) {
				errorMsg += fileName + ",\t";
				continue;
			}

			writeToFile(field.getValueAs(InputStream.class), uploadedFileLocation + fileName);
		}
		if (!errorMsg.equals("")) {
			return Response.status(200).entity("all the pics are uploaded, \nexcept "+errorMsg+" which are not acceptable").build();
		
		}
		return Response.status(200).entity("all the pics are successfully uploaded").build();
	}

	@POST
	@Path("/uploadFace")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response TrainFace(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {
		final WebResponse webResponse = new WebResponse();
		final AuthenticationUtil authUtil = new AuthenticationUtil();
		if (!authUtil.getCurrentUsername().trim().equalsIgnoreCase("anonymousUser")) {
			// 人臉list檔名
			String faceListName = dictLocation + "\\list_" + authUtil.getCurrentUsername() + ".txt";
			// 獲取副檔名
			String fileName = fileDetail.getFileName();
			String fileExtension = fileName.substring(fileName.length() - 4, fileName.length());// 上傳圖片位置
			String uploadDict = dictLocation + "\\" + authUtil.getCurrentUsername();
			String photoName = uploadDict + "\\" + authUtil.getCurrentUsername() + "1" + fileExtension;
			File file = new File(uploadDict);
			if (!file.exists()) {
				file.mkdirs();
			}
			// System.out.println(uploadDict +"\n"+photoName);
			writeToFile(uploadedInputStream, photoName);

			// 人臉list內容
			String data = "resources\\" + authUtil.getCurrentUsername() + "\\" + authUtil.getCurrentUsername() + "1"
					+ fileExtension + "\t" + authUtil.getCurrentUsername() + "[No]1";
			writeFaceList(faceListName, data);

			TrainFace trainFace = new TrainFace();
			trainFace.setEnginePath(enginePath);
			trainFace.setTrainListPath("resources\\list_" + authUtil.getCurrentUsername() + ".txt");
			trainFace.setModelPath(modelPath);
			trainFace.setModelExist(true);

			EngineFunc engineFunc = new EngineFunc();
			engineFunc.trainFace(trainFace);

			webResponse.OK();
			webResponse.setData("file has been uploaded to " + uploadDict + " and list either to : " + faceListName);
		} else {
			webResponse.UNAUTHORIZED();
			webResponse.setData("please login!");
		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	private void writeFaceList(String listLocation, String data) throws IOException {
		FileOutputStream out = new FileOutputStream(listLocation);
		out.write(data.getBytes());
		out.flush();
		out.close();

	}

	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
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
