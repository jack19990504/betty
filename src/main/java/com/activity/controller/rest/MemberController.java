package com.activity.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.activity.controller.rest.MemberController;
import com.activity.dao.MemberDAO;
import com.activity.entity.Member;
import com.activity.util.AuthenticationUtil;
import com.activity.util.WebResponse;
import com.google.gson.Gson;

@Path("/member")

@RestController
//@CrossOrigin
public class MemberController {

	@Autowired
	MemberDAO memberDAO;
	
	//send mail bean	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(Member member) {
		

		memberDAO.insert(member);

		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(member)).build();
	}
	
	@CrossOrigin
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		List<Member> memberList = memberDAO.getList();
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(memberList)).build();
	}

	@PATCH
	@Path("/{Patchid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("Patchid") String id, Member member) {
		
		member.setMemberEmail(id);
		final Member oldMember = memberDAO.get(member);
		memberDAO.update(oldMember, member);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(member)).build();
	}
	
	@DELETE
	@Path("/{Deleteid}")
	public Response delete(@PathParam("Deleteid") String id) {
		
		Member member = new Member();
		member.setMemberEmail(id);
		member = memberDAO.get(member);
		memberDAO.delete(member);
		return Response.status(200).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") String id) {
//		if(id!=null) {
			Member member = new Member();
			member.setMemberEmail(id);
			member = memberDAO.get(member);
//			if(member.getMemberName() != null) {
				
//			}
//		}
		
		
		System.out.println("id="+id);
		Gson gson = new Gson();
		return Response.status(200).entity(gson.toJson(member)).build();
		
	}
	//忘記密碼所使用的api
	@POST
	@Path("/forgetpassword/{memberEmail}")
	public Response forgetPassword(@PathParam("memberEmail") String memberEmail)
	{
		final WebResponse webResponse = new WebResponse();
		Member member = new Member();
		member.setMemberEmail(memberEmail);
		member = memberDAO.get(member);
		if(member.getMemberName().equals(null))
		{
			webResponse.NO_CONTENT();
			webResponse.setData("this mail has not be regisetered!");
			
		}
		else
		{
				
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				//產生隨機密碼 為長度十二的亂碼
				UUID uuid = UUID.randomUUID();
				String[] uuids = uuid.toString().split("-");
				String newPassword = uuids[0] + uuids[1];
				//加密此亂碼供spring security判斷使用
				String encodedPassword = passwordEncoder.encode(newPassword);
				
				member.setMemberPassword(newPassword);
				member.setMemberEncodedPassword(encodedPassword);
				//更新密碼
				memberDAO.updateMemberPassword(member);
				
				
				sendMail(memberEmail, newPassword);
				System.out.println(newPassword);
				webResponse.OK();
				webResponse.setData("Your password has been reset , and new password has been sent to your mail");
			
			
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	//更改密碼api
	@POST
	@Path("/updatepassword/{oldPassword}/{newPassword}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePassword(@PathParam("oldPassword") String oldPassword,@PathParam("newPassword") String newPassword)
	{
		final WebResponse webResponse = new WebResponse();
		final AuthenticationUtil authUtil = new AuthenticationUtil();
		System.out.println(authUtil.getCurrentUsername());
		String memberEmail = authUtil.getCurrentUsername();
		if(oldPassword.equals(null) || newPassword.equals(null))
		{
			webResponse.BAD_REQUEST();
			webResponse.setData("Please enter password !");
		}
		else
		{
			Member member = new Member();
			member.setMemberEmail(memberEmail);
			member = memberDAO.get(member);
			if(member.getMemberPassword().equals(oldPassword))
			{
				//加密新密碼
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String encodedPassword = passwordEncoder.encode(newPassword);
				
				member.setMemberPassword(newPassword);
				member.setMemberEncodedPassword(encodedPassword);
				//更改密碼
				memberDAO.updateMemberPassword(member);
				
				webResponse.setData(member);
				webResponse.OK();
			}
			else
			{
				webResponse.UNPROCESSABLE_ENTITY();
				webResponse.setData("Wrong password !");
				
			}
			
		}
		
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}
	
	public void sendMail(String userId , String password)
	{
		SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo(userId);

	        msg.setSubject("Your new password !");
	        msg.setText("Your new password is : " + password);

	        javaMailSender.send(msg);
	}
}
