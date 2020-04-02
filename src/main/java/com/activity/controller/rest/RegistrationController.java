package com.activity.controller.rest;

import java.io.FileOutputStream;
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

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.activity.controller.rest.RegistrationController;
import com.activity.dao.ActivityDAO;
import com.activity.dao.RegistrationDAO;
import com.activity.engine.util.AttributeCheck;
import com.activity.entity.Activity;
import com.activity.entity.Member;
import com.activity.entity.Registration;
import com.activity.util.AuthenticationUtil;
import com.activity.util.WebResponse;
import com.google.gson.Gson;

@Path("/registration")

@RestController

public class RegistrationController {

	@Autowired
	RegistrationDAO registrationDAO;
	ActivityDAO activityDAO;

	@CrossOrigin
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(Registration registration) {
		final AttributeCheck attributeCheck = new AttributeCheck();
		final WebResponse webResponse = new WebResponse();
		final AuthenticationUtil authUtil = new AuthenticationUtil();

		Registration tRegistration = new Registration();

		tRegistration.setMember_Email(registration.getMember_Email());
		tRegistration.setActivity_Id(registration.getActivity_Id());
		tRegistration = registrationDAO.getOneRegistration(tRegistration);
		Integer attendPeople = registrationDAO.checkAttendPeople(tRegistration);
		//檢查此使用者是否報名過此活動，如是則顯示錯誤訊息
		if (attributeCheck.stringsNotNull(tRegistration.getMember_Email())) {

			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("You have already registered this activity!");

		}
		else {
			//檢查使用者在近一個月內取消報名的次數
			Member member = new Member();
			member.setMemberEmail(authUtil.getCurrentUsername());
			Integer cancelTimes = registrationDAO.getUserCancelTimeInMonth(member);
			Integer notAttendTimes = registrationDAO.getNoCancelAndNoAttend(member);
			System.out.println(member.getMemberEmail() + "\t" + cancelTimes);
			//如超過三次則無法報名
			if (cancelTimes >= 3) {
				webResponse.UNPROCESSABLE_ENTITY();
				webResponse.setData("You cancel 3 registrations within a month! \n please wait until next month!");
			}
			else if(notAttendTimes >= 2){
				webResponse.UNPROCESSABLE_ENTITY();
				webResponse.setData("You did not cancel and attend 3 registrations within a month! \n please wait until next month!");
			}
			else if (attendPeople > 0) {
				Activity activity = new Activity();
				activity.setActivityId(registration.getActivity_Id());
				if(attendPeople == activity.getAttendPeople()) {
					webResponse.UNPROCESSABLE_ENTITY();
					webResponse.setData("Applicants are full !");
				}				
			}
			else {
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
		List<Registration> registrationList = registrationDAO.getList();
		return Response.status(200).entity(registrationList).build();
	}

	@GET
	@Path("/activity/{Getid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActivityList(@PathParam("Getid") int id) {
		List<Registration> registrationList = registrationDAO.getActivityList(id);
		
		return Response.status(200).entity(registrationList).build();
	}

	@PATCH
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Registration registration) {
		registration.setAInum(id);
//		System.out.println(id + "   " + activityID);
//		registration.setMember_Email(id);
//		registration.setActivity_Id(activityID);

		final Registration oldRegistration = registrationDAO.get(registration);
		registrationDAO.update(oldRegistration, registration);
		return Response.status(200).entity(registration).build();
	}

	@DELETE
	@Path("/{AInum}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("AInum") int id) {
//		Registration registration = new Registration();
//		registration = registrationDAO.get(registration);
		registrationDAO.delete(id);
		return Response.status(200).build();
	}

	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") String id) {
		Registration registration = new Registration();
		registration.setMember_Email(id);
		registration = registrationDAO.get(registration);
		System.out.println("id=" + id);
		return Response.status(200).entity(registration).build();

	}

	@PATCH
	@Path("/cancel/{id}")
	public Response updateCancel(@PathParam("id") int id) {
		Registration registration = new Registration();
		registration.setAInum(id);

		registrationDAO.updateCancelTime(registration);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(registration)).build();
	}
	
	@GET
	@Path("/writeExcel/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response writeExcel(@PathParam("id") int id) throws Exception
	{
		List<Registration> registrationList = registrationDAO.getListWithMemberInformation(id);
		
		write(registrationList,"測試檔案");
		
		return Response.status(200).entity(registrationList).build();
	}
	
	public void write(List<Registration> registrationList, String filename) throws Exception {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet1;
		//String filePath = "C:/Users/jack1/Desktop/somthing/upload/" + filename  + ".xls";
		String filePath = "C:\\Users\\Morris\\Desktop\\upload\\" + filename  + ".xls";
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
		//第一列第一欄為
		final String Title = "報名名單";
		titlerow.createCell(rowIndex).setCellValue(Title);
		//跳列
		rowIndex++;
		
		//名字 電話 緊急連絡人 電話 *mail 備註 供餐 性別
		
		// 次要表頭
		
		final String[] head = { "名字","Email", "性別" ,"電話", "緊急聯絡人姓名","緊急連絡人電話", "緊急聯絡人關係","備註","餐點"};
		
		titlerow = sheet1.createRow(rowIndex);
		for (int i = 0; i < head.length; i++) {
			titlerow.createCell(i).setCellValue(head[i]);
		}
		rowIndex++;
		for (Registration r : registrationList) {
			
			if(r.getCancelRegistrationString() != null)
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
			
			if(r.getRegistrationMeal() == 0 )
			{
				rowDate.createCell(8).setCellValue("不供餐");
			}
			else if(r.getRegistrationMeal() == 1 )
			{
				rowDate.createCell(8).setCellValue("葷");
			}
			
			else if(r.getRegistrationMeal() == 2 )
			{
				rowDate.createCell(8).setCellValue("素");
			}


		}
		FileOutputStream fos = new FileOutputStream(filePath);
		workbook.write(fos);
		fos.close();
		workbook.close();
	}
	
	

}
