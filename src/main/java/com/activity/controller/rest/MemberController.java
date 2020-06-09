package com.activity.controller.rest;

import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.activity.dao.MemberDAO;
import com.activity.entity.MailTemplate;
import com.activity.entity.Member;
import com.activity.util.AuthenticationUtil;
import com.activity.util.WebResponse;

@Path("/member")

@RestController
@CrossOrigin("*")
public class MemberController {

	@Autowired
	MemberDAO memberDAO;

	// send mail bean
	@Autowired
	private JavaMailSender javaMailSender;

	@POST
	@Path("/check")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkExist(Member member) {
		final WebResponse webResponse = new WebResponse();
		Member member1 = new Member();
		member1.setMemberEmail(member.getMemberEmail());
		if (memberDAO.get(member1).getMemberName() != null) {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("此帳號已註冊");
		} else {
			webResponse.OK();
			webResponse.setData(member);
		}
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(Member member) {
		final WebResponse webResponse = new WebResponse();
		memberDAO.insert(member);
		webResponse.OK();
		webResponse.setData(member);

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@CrossOrigin
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		final WebResponse webResponse = new WebResponse();

		List<Member> memberList = memberDAO.getList();
		webResponse.OK();
		webResponse.setData(memberList);

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@PATCH
	@Path("/{Patchid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("Patchid") String id, Member member) {

		final WebResponse webResponse = new WebResponse();

		if (!id.equals(null)) {

			member.setMemberEmail(id);

			final Member oldMember = memberDAO.get(member);

			if (!oldMember.getMemberName().equals(null)) {

				memberDAO.update(oldMember, member);
				member = memberDAO.get(member);
				webResponse.OK();
				webResponse.setData(member);

			} else {

				webResponse.NOT_FOUND();
				webResponse.setData("Data not found");
			}
		} else {

			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("id required");

		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@DELETE
	@Path("/{Deleteid}")
	@Produces("application/json")
	public Response delete(@PathParam("Deleteid") String id) {
		final WebResponse webResponse = new WebResponse();
		if (!id.equals(null)) {
			Member member = new Member();
			member.setMemberEmail(id);
			member = memberDAO.get(member);
			memberDAO.delete(member);
			webResponse.OK();
			webResponse.setData(member);
		} else {
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("id required");
		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response get(@PathParam("id") String id) {

		final WebResponse webResponse = new WebResponse();

		if (!id.equals(null)) {

			Member member = new Member();
			member.setMemberEmail(id);
			member = memberDAO.get(member);

			if (member.getMemberName() != null) {
				webResponse.OK();
				webResponse.setData(member);
			} else {
				webResponse.NOT_FOUND();
				webResponse.setData("data not found");
			}
		} else {

			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("id required!");

		}

		System.out.println("id=" + id);

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();

	}

	// 忘記密碼所使用的api
	@POST
	@Path("/forgetpassword/{memberEmail}")
	@Produces("application/json")
	public Response forgetPassword(@PathParam("memberEmail") String memberEmail) throws MessagingException {

		final WebResponse webResponse = new WebResponse();

		if (!memberEmail.equals(null)) {

			Member member = new Member();

			member.setMemberEmail(memberEmail);

			member = memberDAO.get(member);

			if (member.getMemberName().equals(null)) {

				webResponse.NOT_FOUND();

				webResponse.setData("this mail has not be regisetered!");

			} else {

				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				// 產生隨機密碼 為長度十二的亂碼
				UUID uuid = UUID.randomUUID();
				String[] uuids = uuid.toString().split("-");
				String newPassword = uuids[0] + uuids[1];
				// 加密此亂碼供spring security判斷使用
				String encodedPassword = passwordEncoder.encode(newPassword);

				member.setMemberPassword(newPassword);
				member.setMemberEncodedPassword(encodedPassword);
				// 更新密碼
				memberDAO.updateMemberPassword(member);

				sendMail(memberEmail, newPassword);
				System.out.println(newPassword);
				webResponse.OK();
				webResponse.setData("Your password has been reset , and new password has been sent to your mail");
			}
		} else {

			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("Member Email required!");

		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	// 更改密碼api
	@POST
	@Path("/updatepassword/{oldPassword}/{newPassword}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePassword(@PathParam("oldPassword") String oldPassword,
			@PathParam("newPassword") String newPassword) {

		final WebResponse webResponse = new WebResponse();

		final AuthenticationUtil authUtil = new AuthenticationUtil();

		System.out.println(authUtil.getCurrentUsername());

		String memberEmail = authUtil.getCurrentUsername();

		if (oldPassword.equals(null) || newPassword.equals(null)) {

			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("Please enter password !");

		} else {
			Member member = new Member();
			member.setMemberEmail(memberEmail);
			member = memberDAO.get(member);
			if (member.getMemberPassword().equals(oldPassword)) {
				// 加密新密碼
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String encodedPassword = passwordEncoder.encode(newPassword);

				member.setMemberPassword(newPassword);
				member.setMemberEncodedPassword(encodedPassword);
				// 更改密碼
				memberDAO.updateMemberPassword(member);

				webResponse.setData(member);
				webResponse.OK();
			} else {
				webResponse.BAD_REQUEST();
				webResponse.setData("Wrong password !");

			}

		}

		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();
	}

	public void sendMail(String userId, String password) throws MessagingException {
		SimpleMailMessage msg = new SimpleMailMessage();

		MailTemplate mail = new MailTemplate();

		Member member = new Member();
		member.setMemberEmail(userId);
		member = memberDAO.get(member);

		mail.setAddressee(member);
		mail.setNewPassword(password);

//		
//		msg.setTo(userId);
//
//		msg.setSubject("ActFun : 您的新密碼");
//		msg.setText(mail.getMail());

		MimeMessage mailMessage = javaMailSender.createMimeMessage();
		mailMessage.setSubject("ActFun : 您的新密碼", "UTF-8");

		MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
		helper.setFrom("actfun.official@gmail.com");
		helper.setTo(member.getMemberEmail());
		helper.setText(mail.getMail(), true);

		javaMailSender.send(mailMessage);

		// javaMailSender.send(msg);
	}

}
