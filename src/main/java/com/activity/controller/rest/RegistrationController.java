package com.activity.controller.rest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.activity.dao.ActivityDAO;
import com.activity.dao.RegistrationDAO;
import com.activity.engine.util.AttributeCheck;
import com.activity.entity.Activity;
import com.activity.entity.Member;
import com.activity.entity.Registration;
import com.activity.util.AuthenticationUtil;
import com.activity.util.WebResponse;
import com.sun.jersey.core.header.ContentDisposition;

@Path("/registration")
@CrossOrigin("*") 
@RestController

public class RegistrationController {

	@Autowired
	RegistrationDAO registrationDAO;
	@Autowired
	ActivityDAO activityDAO;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getOne(@PathParam("id") Integer id){
		final AttributeCheck attributeCheck = new AttributeCheck();
		final WebResponse webResponse = new WebResponse();
		if(id != null)
		{
			Registration registration = new Registration();

			registration.setAInum(id);

			registration = registrationDAO.get(registration);
			if(attributeCheck.stringsNotNull(registration.getMember_Email()))
			{
				webResponse.OK();
				webResponse.setData(registration);
			}
			else
			{
				webResponse.NOT_FOUND();
				webResponse.setData("no data");
			}
		}
		else
		{
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("id required");
		}
		


		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@CrossOrigin
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(Registration registration) {
		final AttributeCheck attributeCheck = new AttributeCheck();
		final WebResponse webResponse = new WebResponse();
		final AuthenticationUtil authUtil = new AuthenticationUtil();

		Registration tRegistration = new Registration();
		System.out.println(registration.getActivity_Id());
		tRegistration.setMember_Email(registration.getMember_Email());
		tRegistration.setActivity_Id(registration.getActivity_Id());
		tRegistration = registrationDAO.getOneRegistration(tRegistration);
		Integer attendPeople = registrationDAO.checkAttendPeople(registration);
		// 檢查此使用者是否報名過此活動，如是則顯示錯誤訊息
		if (attributeCheck.stringsNotNull(tRegistration.getMember_Email())) {
			System.out.println("TEST1");

			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("You have already registered this activity!");

		} else {
			// 檢查使用者在近一個月內取消報名的次數
			Member member = new Member();
			member.setMemberEmail(authUtil.getCurrentUsername());
			Integer cancelTimes = registrationDAO.getUserCancelTimeInMonth(member);
			Integer notAttendTimes = registrationDAO.getNoCancelAndNoAttend(member);
			System.out.println(member.getMemberEmail() + "\t" + cancelTimes);
			// 如超過三次則無法報名
			if (cancelTimes >= 3) {
				System.out.println("TEST2");
				webResponse.UNPROCESSABLE_ENTITY();
				webResponse.setData("You cancel 3 registrations within a month! \n please wait until next month!");
			} else if (notAttendTimes >= 2) {
				System.out.println("TEST3");
				webResponse.UNPROCESSABLE_ENTITY();
				webResponse.setData(
						"You cancel and attend 3 registrations within a month! \n please wait until next month!");
			} else if (attendPeople > 0) {
				Activity activity = new Activity();
				activity.setActivityId(registration.getActivity_Id());
				activity = activityDAO.get(activity);
				if (attendPeople == activity.getAttendPeople()) {
					System.out.println("TEST4");
					webResponse.UNPROCESSABLE_ENTITY();
					webResponse.setData("Applicants are full !");
				}
				else
				{
					registrationDAO.insert(registration);
					webResponse.OK();
					webResponse.setData(registration);
				}
			} else {
				registrationDAO.insert(registration);
				webResponse.OK();
				webResponse.setData(registration);
			}
		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		final WebResponse webResponse = new WebResponse();
		
			List<Registration> registrationList = registrationDAO.getList();
			webResponse.OK();
			webResponse.setData(registrationList);
			
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@GET
	@Path("/activity/{Getid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActivityList(@PathParam("Getid") Integer id) {
		final WebResponse webResponse = new WebResponse();
		if (id.equals(null)) {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("need activtiy id!");
		} else {
			List<Registration> registrationList = registrationDAO.getActivityList(id);
			if (registrationList.size() == 0) {
				webResponse.NO_CONTENT();
				webResponse.setData("There is no user registered this activity");

			} else {
				webResponse.OK();
				webResponse.setData(registrationList);
			}
		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@PATCH
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Integer id, Registration registration) {
		final WebResponse webResponse = new WebResponse();
//		System.out.println(id + "   " + activityID);
//		registration.setMember_Email(id);
//		registration.setActivity_Id(activityID);
		if (id == null) {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("id required!");
		} else {
			registration.setAInum(id);
			final Registration oldRegistration = registrationDAO.get(registration);
			if (!oldRegistration.getMember_Email().equals(null)) {
				registrationDAO.update(oldRegistration, registration);
				registration = registrationDAO.get(registration);
				webResponse.OK();
				webResponse.setData(registration);
			} else {
				webResponse.NOT_FOUND();
				webResponse.setData("data not found!");
			}

		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@DELETE
	@Path("/{AInum}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("AInum") Integer id) {
		
		final WebResponse webResponse = new WebResponse();
		
		if (id == null) {
			
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("id required!");
			
		} else {
			
			Registration r = new Registration();
			r.setAInum(id);
			r = registrationDAO.get(r);
			registrationDAO.delete(id);
			webResponse.OK();
			webResponse.setData(r);
			
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	//獲取單一使用者所有報名資訊
	@GET
	@Path("/member/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") String id) {
		
		final WebResponse webResponse = new WebResponse();
		
		if (!id.equals(null)) {
			
			Registration registration = new Registration();
			registration.setMember_Email(id);
			List<Registration> RegistrationList = registrationDAO.getMemberList(registration);

			
			if (RegistrationList.size() != 0) {
				
				webResponse.OK();
				webResponse.setData(RegistrationList);
				
			} else {
				
				webResponse.setData("You have not registered any activtiy!");
				webResponse.NOT_FOUND();
				
				
			}
		} else {
			
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("memberEmail required!");
			
		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();

	}

	@PATCH
	@Path("/cancel/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCancel(@PathParam("id") Integer id) {
		
		final WebResponse webResponse = new WebResponse();
		
		if (id != null) {
			
			Registration registration = new Registration();
			registration.setAInum(id);
			registration = registrationDAO.get(registration);
			
			if (!registration.getMember_Email().equals(null)) {
				
				registrationDAO.updateCancelTime(registration);
				webResponse.OK();
				webResponse.setData(registration);
				
			} else {
				
				webResponse.NOT_FOUND();
				webResponse.setData("data not found");
				
			}
		} else {
			
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("id required");
			
		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@POST
	@Path("/signIn/{activityId}/{memberEmail}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response signInByMemberEmail(@PathParam("activityId") Integer id,@PathParam("memberEmail") String memberEmail)
	{
		final AttributeCheck attributeCheck = new AttributeCheck();
		final WebResponse webResponse = new WebResponse();
		if(id!=null && attributeCheck.stringsNotNull(memberEmail))
		{
			Registration registration = new Registration();
			registration.setActivity_Id(id);
			registration.setMember_Email(memberEmail);
			
			registration = registrationDAO.getOneRegistration(registration);
			if(registration.getAInum() != null)
			{
				registrationDAO.signInByMemberEmail(registration);
				webResponse.OK();
				registration = registrationDAO.getOneRegistration(registration);
				webResponse.setData(registration);
			}
			else
			{
				webResponse.NOT_FOUND();
				webResponse.setData("查無報名資料!");
			}
		}
		else
		{
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("id or memberEmail required!");
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	

	@POST
	@Path("/signOut/{activityId}/{memberEmail}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response signOutByMemberEmail(@PathParam("activityId") Integer id,@PathParam("memberEmail") String memberEmail)
	{
		final AttributeCheck attributeCheck = new AttributeCheck();
		final WebResponse webResponse = new WebResponse();
		if(id!=null && attributeCheck.stringsNotNull(memberEmail))
		{
			Registration registration = new Registration();
			registration.setActivity_Id(id);
			registration.setMember_Email(memberEmail);
			
			
			registration = registrationDAO.getOneRegistration(registration);
			System.out.println(registration.getMember_Email() +"\t" + registration.getActivity_Id() + "\t" + registration.getAInum());
			
			if(registration.getAInum() != null)
			{
				registrationDAO.signOutByMemberEmail(registration);
				webResponse.OK();
				registration = registrationDAO.getOneRegistration(registration);
				System.out.println(registration.getIsSignIn() +  "\t" + registration.getIsSignOut());
				webResponse.setData(registration);
			}
			else
			{
				webResponse.NOT_FOUND();
				webResponse.setData("查無報名資料!");
			}
		}
		else
		{
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("id or memberEmail required!");
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@Path("/QRcodeSignIn/{activityId}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signInByQRcode(Registration registration,@PathParam("activityId") Integer id)
	{
		final AttributeCheck attributeCheck = new AttributeCheck();
		final WebResponse webResponse = new WebResponse();
		
		if(!registration.getAInum().equals(null))
		{
			
			registration = registrationDAO.get(registration);
			
			if(!registration.getActivity_Id().equals(id))
			{
				webResponse.BAD_REQUEST();
				webResponse.setData("Wrong QRcode!");
			}
			else
			{
				if(attributeCheck.stringsNotNull(registration.getMember_Email()))
				{
					registrationDAO.signInByAINum(registration);
					webResponse.OK();
					webResponse.setData(registration.getMember());
				}
				else
				{
					webResponse.NOT_FOUND();
					webResponse.setData("this regis is not found!");
				}
			}
			
			
		}
		else
		{
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("AInum required!");
		}
		
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	
	@Path("/QRcodeSignOut/{activityId}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signOutByQRcode(Registration registration,@PathParam("activityId") Integer id)
	{
		final AttributeCheck attributeCheck = new AttributeCheck();
		final WebResponse webResponse = new WebResponse();
		
			if(!registration.getAInum().equals(null))
			{
				
				registration = registrationDAO.get(registration);
				
				if(!registration.getActivity_Id().equals(id))
				{
					webResponse.BAD_REQUEST();
					webResponse.setData("Wrong QRcode!");
				}
				else
				{
					if(attributeCheck.stringsNotNull(registration.getMember_Email()))
					{
						registrationDAO.signOutByAINum(registration);
						webResponse.OK();
						webResponse.setData(registration.getMember());
					}
					else
					{
						webResponse.NOT_FOUND();
						webResponse.setData("this regis is not found!");
					}
				}

			}
			else
			{
				webResponse.UNPROCESSABLE_ENTITY();
				webResponse.setData("AInum required!");
			}
		

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	@Path("/ifSignUp/{id}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ifSignUp(@PathParam("id") Integer id)
	{
		final AuthenticationUtil authUtil = new AuthenticationUtil();
		final WebResponse webResponse = new WebResponse();
		if(id != null)
		{
			Activity activity = new Activity();
			activity.setActivityId(id);
			activity = activityDAO.get(activity);
			String memberEmail = authUtil.getCurrentUsername();
			
			List<Activity> aList = registrationDAO.getUserIsSignUpSameDay(activity, memberEmail);
			if(aList.size() == 0)
			{
				webResponse.OK();
				webResponse.setData("ok");
			}
			else
			{
				webResponse.OK();
				webResponse.setData("no");
			}
			
		}
		else
		{
			webResponse.setData("id required");
			webResponse.UNPROCESSABLE_ENTITY();
		}
		
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	
	@GET
	@Path("/writeExcel/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response writeExcel(@PathParam("id") Integer id) throws Exception {
		
		final WebResponse webResponse = new WebResponse();
		
		if(id != null)
		{
			List<Registration> registrationList = registrationDAO.getListWithMemberInformation(id);
			
			if(registrationList.size() != 0)
			{
				
				String pathh = write(registrationList, registrationList.get(0).getActivity().getActivityName());
				
				StreamingOutput fileStream =  new StreamingOutput() {

					@Override
					public void write(OutputStream output) throws IOException, WebApplicationException {
						 try
			                {
							 System.out.println(id);
			                    java.nio.file.Path path = Paths.get(pathh);
			                    byte[] data = Files.readAllBytes(path);
			                    output.write(data);
			                    output.flush();
			                } 
			                catch (Exception e) 
			                {
			                    throw new WebApplicationException("File Not Found !!");
			                }
			            }
			        };
			        
			        System.out.println(registrationList.get(0).getActivity().getActivityName());
			        String fileName = pathh.substring(pathh.lastIndexOf("/upload/")+8);
			        System.out.println(fileName);
			        String header = "attachment; filename =\""+fileName+"\"";
			        ContentDisposition contentDisposition = ContentDisposition.type("attachment")
			        	    .fileName(fileName).build();
			        return Response
			                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
			                .header("Content-Disposition","attachment; filename = ".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))))
			                .build();
				
				
			}
			
			else
			{
				webResponse.NO_CONTENT();
				webResponse.setData("No user regisetered this activtiy");
			}
			
		}
		else
		{
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("id required");
		}
		

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	
	        

	public String write(List<Registration> registrationList, String filename) throws Exception {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet1;
		String filePath = "C:/Users/jack1/Desktop/somthing/upload/" + filename +".xls";
		//String filePath = "C:\\Users\\Morris\\Desktop\\upload\\" + filename + ".xls";
		sheet1 = workbook.createSheet("TEST");
		// 設定開始欄位為第一欄

		sheet1.setColumnWidth(0, 4000);
		sheet1.setColumnWidth(1, 7000);
		sheet1.setColumnWidth(2, 4000);
		sheet1.setColumnWidth(3, 4000);
		sheet1.setColumnWidth(4, 4000);
		sheet1.setColumnWidth(5, 4000);
		sheet1.setColumnWidth(6, 4000);
		sheet1.setColumnWidth(7, 4000);
		sheet1.setColumnWidth(8, 4000);
		sheet1.setColumnWidth(9, 4000);
		sheet1.setColumnWidth(10, 4000);

		int rowIndex = 0;
		// 設定並寫入表頭，欄位+1
		HSSFRow titlerow = sheet1.createRow(rowIndex);
		// 第一列第一欄為
		final String Title = "報名名單";
		titlerow.createCell(rowIndex).setCellValue(Title);
		// 跳列
		rowIndex++;

		// 名字 電話 緊急連絡人 電話 *mail 備註 供餐 性別

		// 次要表頭

		final String[] head = { "會員名字", "會員Email", "會員性別", "會員電話", "緊急聯絡人姓名", "緊急連絡人電話", "緊急聯絡人關係", "備註", "餐點選項" };

		titlerow = sheet1.createRow(rowIndex);
		for (int i = 0; i < head.length; i++) {
			titlerow.createCell(i).setCellValue(head[i]);
		}
		rowIndex++;
		for (Registration r : registrationList) {

			if (r.getCancelRegistrationString() != null)
				continue;

			// 根據行指定列座標j,然後在單元格中寫入資料
			HSSFRow rowDate = sheet1.createRow(sheet1.getLastRowNum() + 1);

			rowDate.createCell(0).setCellValue(r.getMember().getMemberName());

			rowDate.createCell(1).setCellValue(r.getMember_Email());

			rowDate.createCell(2).setCellValue(r.getMember().getMemberGender());

			rowDate.createCell(3).setCellValue(r.getMember().getMemberPhone());

			rowDate.createCell(4).setCellValue(r.getMember().getEmergencyContact());

			rowDate.createCell(5).setCellValue(r.getMember().getEmergencyContactPhone());

			rowDate.createCell(6).setCellValue(r.getMember().getEmergencyContactRelation());

			rowDate.createCell(7).setCellValue(r.getRegistrationRemark());

			if (r.getRegistrationMeal() == 0) {
				rowDate.createCell(8).setCellValue("不供餐");
			} else if (r.getRegistrationMeal() == 1) {
				rowDate.createCell(8).setCellValue("葷");
			}

			else if (r.getRegistrationMeal() == 2) {
				rowDate.createCell(8).setCellValue("素");
			}

		}
		
        //下載路徑到 "下載項目"
        
        


        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();

        return filePath;
	}

}
