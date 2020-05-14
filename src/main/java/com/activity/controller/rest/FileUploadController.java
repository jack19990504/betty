package com.activity.controller.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.activity.dao.ActivityDAO;
import com.activity.dao.PhotoDAO;
import com.activity.engine.control.EngineFunc;
import com.activity.engine.entity.TrainFace;
import com.activity.engine.util.AttributeCheck;
import com.activity.entity.Activity;
import com.activity.util.WebResponse;

@CrossOrigin("*")
@Path("/files")
@Controller
public class FileUploadController {

	@Autowired
	ActivityDAO activityDAO;
	@Autowired
	PhotoDAO photoDAO;

//	private final String dictLocation = "C:\\Users\\Morris\\Desktop\\人臉辨識引擎\\face\\engine\\resources";
//	static String enginePath = "C:\\Users\\Morris\\Desktop\\人臉辨識引擎\\face\\engine";
	static String modelPath = "eGroup\\jack_kobe.Model";
	static String reactFolderPath = "C:\\Users\\jack1\\Desktop\\test\\react_pages\\public\\assets\\images";
	private final String dictLocation = "C:\\Users\\jack1\\Desktop\\face\\Engine\\resources";
	static String enginePath = "C:\\Users\\jack1\\Desktop\\face\\Engine";
	// static String modelPath = "eGroup\\jack_kobe.Model";
	long time = 0;
	int picN = 0;

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

	// 上傳activity cover
	@POST
	@Path("/files/activityCover/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postActivityCover(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, @PathParam("id") Integer id) {
		final WebResponse webResponse = new WebResponse();
		final AttributeCheck attributeCheck = new AttributeCheck();
		if (id != null) {
			Activity activity = new Activity();
			activity.setActivityId(id);
			activity = activityDAO.get(activity);
			if (attributeCheck.stringsNotNull(activity.getActivityName())) {
				String uploadedFileLocation = "C:\\Users\\jack1\\Desktop\\test\\react_pages\\public\\assets\\images\\activityCover\\"
						+ id;
				System.out.println(uploadedFileLocation);
				File file = new File(uploadedFileLocation);
				if (!file.exists()) {
					file.mkdirs();
				}

				String fileName = fileDetail.getFileName();
				String suffix = fileName.substring(fileName.lastIndexOf("."));
				if (suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".png")) {
					// save it
					writeToFile(uploadedInputStream, uploadedFileLocation + "\\" + fileName);
					String output = "assets\\images\\activityCover\\" + id +"\\"+ fileName;
					
					activity.setActivityCover(output);
					activityDAO.updateCover(activity);
					webResponse.OK();
					webResponse.setData(output);
				} else {
					webResponse.UNPROCESSABLE_ENTITY();
					webResponse.setData("please give a correct file!");
				}
			}
			else
			{
				webResponse.setData("Data not found!");
				webResponse.NOT_FOUND();
			}

		} else {
			webResponse.setData("id required!");
			webResponse.UNPROCESSABLE_ENTITY();
		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@POST
	@Path("/files/{fileDictName}")
	@Consumes(MediaType.MULTIPART_FORM_DATA + ";charset=utf-8")
	public Response hello2(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@PathParam("fileDictName") String fileDictName) throws IOException {
		long startTime = System.currentTimeMillis();
		// System.out.println("test");
		picN += 1;
		String uploadedFileLocation = reactFolderPath + "/ActivityPhoto/" + fileDictName + "/";
		File file = new File(uploadedFileLocation);
		if (!file.exists()) {
			file.mkdirs();
		}
		// String uploadedFileLocation = "C://upload/" + fileDetail.getFileName();
		String fileName = new String(fileDetail.getFileName().getBytes("ISO8859-1"), "UTF-8");
		String test = fileName.substring(fileName.lastIndexOf("."));
		// save it
		System.out.println(fileName);
		String output = "";
		// System.out.println(test);
		String toDB = "assets/images/ActivityPhoto/"+fileDictName+"/";
		if (test.equalsIgnoreCase(".jpg") || test.equalsIgnoreCase(".png") || test.equalsIgnoreCase(".jpeg")) {
			// System.out.println("test3");
			long endTime = System.currentTimeMillis();
			time += endTime - startTime;
			System.out.println("第" + picN + "照片" + "目前共花了" + time + "秒");
			writeToFile(uploadedInputStream, uploadedFileLocation + fileName);	
			photoDAO.writePhoto(Integer.parseInt(fileDictName),toDB+fileName);

			output = "File uploaded to : " + uploadedFileLocation + "side = " + test;

		} else {
			System.out.println("test2");
			output = "no";
		}

		return Response.status(200).entity(output).build();
	}

	// 可一次上傳多筆檔案
	@POST
	@Path("/multifiles/{fileDictName}")
	@Consumes(MediaType.MULTIPART_FORM_DATA + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadMultipart(FormDataMultiPart multiPart, @PathParam("fileDictName") String fileDictName)
			throws IOException {
		List<FormDataBodyPart> fields = multiPart.getFields("file");
		String uploadedFileLocation = reactFolderPath + "/" + fileDictName + "/";
		File file = new File(uploadedFileLocation);
		if (!file.exists()) {
			file.mkdirs();
		}
		String errorMsg = "";
		final WebResponse webResponse = new WebResponse();
		for (FormDataBodyPart field : fields) {

			// get file origin name
			FormDataContentDisposition f = field.getFormDataContentDisposition();
			// 轉成UTF8編碼
			String fileName = new String(f.getFileName().getBytes("ISO8859-1"), "UTF-8");

			String suffix = f.getFileName().substring(f.getFileName().lastIndexOf("."));
			// System.out.println(suffix);
			System.out.println(fileName);
			// if file is not a jpg or a png file
			if (!suffix.equals(".jpg") && !suffix.equals(".png") && !suffix.equals(".jpeg")) {
				errorMsg += fileName + ",\t";
				continue;
			}

			writeToFile(field.getValueAs(InputStream.class), uploadedFileLocation + fileName);
		}
		if (!errorMsg.equals("")) {
			webResponse.BAD_REQUEST();
			webResponse.setData("all the pics are uploaded, except " + errorMsg + " which are not acceptable");
			return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();

		} else {
			webResponse.OK();
			webResponse.setData("all the pics are successfully uploaded");
			return Response.status(200).entity(webResponse.getData()).build();
		}

	}

	@POST
	@Path("/uploadFace/{memberEmail}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response TrainFace(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, @PathParam("memberEmail") String memberEmail)
			throws IOException {
		final WebResponse webResponse = new WebResponse();

		// 人臉list檔名
		String faceListName = dictLocation + "\\list_" + memberEmail + ".txt";
		// 獲取副檔名
		String fileName = fileDetail.getFileName();
		String fileExtension = fileName.substring(fileName.length() - 4, fileName.length());// 上傳圖片位置
		String uploadDict = dictLocation + "\\" + memberEmail;
		String photoName = uploadDict + "\\" + memberEmail + "1" + fileExtension;
		File file = new File(uploadDict);
		if (!file.exists()) {
			file.mkdirs();
		}
		// System.out.println(uploadDict +"\n"+photoName);
		writeToFile(uploadedInputStream, photoName);

		// 人臉list內容
		String data = "resources\\" + memberEmail + "\\" + memberEmail + "1" + fileExtension + "\t" + memberEmail
				+ "[No]1";
		writeFaceList(faceListName, data);

		TrainFace trainFace = new TrainFace();
		trainFace.setEnginePath(enginePath);
		trainFace.setTrainListPath("resources\\list_" + memberEmail + ".txt");
		trainFace.setModelPath(modelPath);
		trainFace.setModelExist(true);

		EngineFunc engineFunc = new EngineFunc();
		engineFunc.trainFace(trainFace);

		webResponse.OK();
		webResponse.setData("file has been uploaded to " + uploadDict + " and list either to : " + faceListName);

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

			// out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			uploadedInputStream.close();
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
