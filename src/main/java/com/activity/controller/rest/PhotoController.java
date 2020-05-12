package com.activity.controller.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import com.activity.dao.PhotoDAO;
import com.activity.engine.control.EngineFunc;
import com.activity.engine.control.GetResult;
import com.activity.engine.entity.Face;
import com.activity.engine.entity.RecognizeFace;
import com.activity.engine.util.AttributeCheck;
import com.activity.entity.Activity;
import com.activity.entity.Member;
import com.activity.entity.Photo;
import com.activity.util.WebResponse;
@CrossOrigin("*") 
@RestController
@Path("/photo")
public class PhotoController {

	@Autowired
	PhotoDAO photoDAO;

	static String enginePath = "C:\\Users\\jack1\\Desktop\\face\\Engine";
	static String outputFacePath = "face";
	static String outputFramePath = "frame";
	static String trainedBinaryPath = "eGroup\\jack_kobe.Model.binary";
	static String trainedFaceInfoPath = "eGroup\\jack_kobe.Model.faceInfo";
	static String jsonPath = "output\\";

	static String resultJsonPath = "C:\\Users\\jack1\\Desktop\\face\\Engine\\output";
	static String jsonName = ".cache.egroup";

	
	
	
	static String reactFolderPath = "C:\\Users\\jack1\\Desktop\\test\\react_pages\\public\\assets\\images\\ActivityPhoto\\";
	
	//將辨識紀錄寫入資料庫
	@POST
	@Path("/writeMemberPhoto/{activityId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response writeMemberPhoto(@PathParam("activityId") Integer id) {
		AttributeCheck attributeCheck = new AttributeCheck();
		WebResponse webResponse = new WebResponse();
		System.out.println(id +jsonName);
		List<Face> faceList = GetResult.photoResult(resultJsonPath, id+jsonName, true);
		System.out.println(faceList.size());
		List<String> recoWho = new ArrayList<>();
		if (faceList.size() == 0) {
			webResponse.setData("no faces in the result!");
			webResponse.UNPROCESSABLE_ENTITY();
		} else {
			for(int i = 0 ;  i < faceList.size() -1 ; i ++ )
			{
				if(faceList.get(i).getHasFound().equals("1"))
				{
					photoDAO.writePhoto(faceList.get(i));
					recoWho.add(faceList.get(i).getPersonId());
				}
			}
			
			webResponse.OK();
			webResponse.setData(recoWho);
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	
	//將資料夾裡的照片寫入資料庫中
	@POST
	@Path("/writePhoto/{activityId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPhotoRecord(@PathParam("activityId") Integer id) throws FileNotFoundException, IOException{
		AttributeCheck attributeCheck = new AttributeCheck();
		WebResponse webResponse = new WebResponse();
		if(attributeCheck.stringsNotNull(String.valueOf(id)))
		{
			String path = reactFolderPath + id;
			System.out.println(path);
			String result = readfileToDataBase(path,id);
			if(result.equals(""))
			{
				webResponse.NOT_FOUND();
				webResponse.setData("dict not found or not exist!");
			}
			else
			{
				String[] Paths = result.split("\n");
				for(String pathh : Paths)
				{
					if(attributeCheck.stringsNotNull(pathh))
					{
						photoDAO.writePhoto(id, pathh);
					}
					
				}
				webResponse.OK();
				webResponse.setData(result + "\thas been writed to database");
			}
		}
		else
		{
			
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("please enter path");
			
		}
		
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	//把相片寫成egroupList,供引擎辨識
	@POST
	@Path("/recognize/{activityId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recognizePhoto(@PathParam("activityId") Integer activityId) throws FileNotFoundException, IOException {
		AttributeCheck attributeCheck = new AttributeCheck();
		WebResponse webResponse = new WebResponse();
		if(attributeCheck.stringsNotNull(String.valueOf(activityId)))
		{
			String dictLocation = reactFolderPath +activityId + "/";
			String list = readfile(dictLocation);
			if(attributeCheck.stringsNotNull(list))
			{
				if(list.equals("檔案"))
				{
					webResponse.BAD_REQUEST();
					webResponse.setData("this activity does not exist or you have not upload pics!");
				}
				else
				{
					writePhotoList(enginePath + "\\" + activityId +".egroupList",list);
					webResponse.OK();
					webResponse.setData(list+"\thas been writed to a list");
				}
			}
			
			else
			{
				webResponse.NOT_FOUND();
				webResponse.setData("you haven't upload any pic");
			}
			
		}
		else
		{
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("activityId required!");
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	
	//獲取會員某個活動的照片
	@GET
	@Path("/memberPhoto/{memberEmail}/{activityId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMemberPhoto(@PathParam("memberEmail")String memberEmail,@PathParam("activityId") Integer id)
	{
		AttributeCheck attributeCheck = new AttributeCheck();
		WebResponse webResponse = new WebResponse();
		if(attributeCheck.stringsNotNull(memberEmail))
		{
			Member member = new Member();
			member.setMemberEmail(memberEmail);
			List<Photo> memberPhotoList = photoDAO.getMemberPhoto(member,id);
			if(memberPhotoList.size() == 0)
			{
				webResponse.NOT_FOUND();
				webResponse.setData("no pic!");
			}
			else
			{
				webResponse.OK();
				webResponse.setData(memberPhotoList);
			}
				
		}	
		else
		{
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("memberEmail required!");
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	//獲取單一活動的所有照片
	@GET
	@Path("/activityPhoto/{activityId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActivityPhoto(@PathParam("activityId") Integer id)
	{
		AttributeCheck attributeCheck = new AttributeCheck();
		WebResponse webResponse = new WebResponse();
		if(id != null)
		{
			Activity activity = new Activity();
			activity.setActivityId(id);
			List<Photo> memberPhotoList = photoDAO.getActivityPhoto(activity);
			if(memberPhotoList.size() == 0)
			{
				webResponse.NOT_FOUND();
				webResponse.setData("no pic!");
			}
			else
			{
				webResponse.OK();
				webResponse.setData(memberPhotoList);
			}
				
		}	
		else
		{
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("memberEmail required!");
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@POST
	@Path("/all/{activityId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response all(@PathParam("activityId") Integer id) throws IOException
	{
		AttributeCheck attributeCheck = new AttributeCheck();
		WebResponse webResponse = new WebResponse();
		
		if(attributeCheck.stringsNotNull(String.valueOf(id)))
		{
			String dictLocation = reactFolderPath +id + "/";
			String list = readfile(dictLocation);
			if(attributeCheck.stringsNotNull(list))
			{
				if(list.equals("檔案"))
				{
					webResponse.BAD_REQUEST();
					webResponse.setData("this activity does not exist or you have not upload pics!");
				}
				else
				{
					writePhotoList(enginePath + "/" + id +".egroupList",list);
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
							System.out.println(id +jsonName);
							List<Face> faceList = GetResult.photoResult(resultJsonPath, id+jsonName, true);
							System.out.println(faceList.size());
							List<String> recoWho = new ArrayList<>();
							if (faceList.size() == 0) {
								webResponse.setData("no faces in the result!");
								webResponse.UNPROCESSABLE_ENTITY();
							} else {
								for(int i = 0 ;  i < faceList.size() -1 ; i ++ )
								{
									if(faceList.get(i).getHasFound().equals("1"))
									{
										photoDAO.writePhoto(faceList.get(i));
										recoWho.add(faceList.get(i).getPersonId());
									}
								}
								
								webResponse.OK();
								webResponse.setData(recoWho);
							}
						} else {
							webResponse.BAD_REQUEST();
							webResponse.setData("reco failed");
						}
					} else {
						webResponse.UNPROCESSABLE_ENTITY();
						webResponse.setData("the list is not exist!");
					}
				}
			}
			
			else
			{
				webResponse.NOT_FOUND();
				webResponse.setData("you haven't upload any pic");
			}
			
		}
		else
		{
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("activityId required!");
		}
		
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteActivityPhoto(Photo photo)
	{
		
		WebResponse webResponse = new WebResponse();
		photoDAO.deletePhoto(photo);
		webResponse.OK();
		webResponse.setData("pic deleted!");
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/memberphoto/{id}")
	public Response deleteMemberPhoto(@PathParam("id") Integer id)
	{
		Photo photo = new Photo();
		photo.setAINum(id);
		WebResponse webResponse = new WebResponse();
		photoDAO.deleteMemberPhoto(photo);
		webResponse.OK();
		webResponse.setData("pic deleted!");
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	
	public static String readfileToDataBase(String filepath,Integer id) throws FileNotFoundException, IOException {
		String result = "";
		try {
			
			File file = new File(filepath);
			if (!file.isDirectory()) {
				
			} else if (file.isDirectory()) {
				
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "//" + filelist[i]);
					if (!readfile.isDirectory()) {
						String fileName = readfile.getName();
						String fileType = fileName.substring(fileName.lastIndexOf("."),fileName.length());
						
						if(fileType.equals(".jpg") || fileType.equals(".jpeg") || fileType.equals(".png"))
							result +=  "assets/images/ActivityPhoto/"+id+"/" + fileName + "\n";
						
					} else if (readfile.isDirectory()) {
						readfile(filepath + "//" + filelist[i]);
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return result;
	}

	public static String readfile(String filepath) throws FileNotFoundException, IOException {
		String result = "";
		try {
			
			File file = new File(filepath);
			if (!file.isDirectory()) {
				return "檔案";

			} else if (file.isDirectory()) {
				System.out.println("資料夾");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "//" + filelist[i]);
					if (!readfile.isDirectory()) {
						String fileName = readfile.getName();
						String fileType = fileName.substring(fileName.lastIndexOf("."),fileName.length());
						System.out.println(fileType);
						
						if(fileType.equals(".jpg") || fileType.equals(".jpeg") || fileType.equals(".png"))
							result +=  readfile.getPath() + "\n";
						
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
		return result;
	}

	private void writePhotoList(String listLocation, String data) throws IOException {
		FileOutputStream out = new FileOutputStream(listLocation);
		out.write(data.getBytes());
		out.flush();
		out.close();

	}
	
}
