package com.activity.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.activity.dao.MemberDAO;
import com.activity.entity.Member;
import com.activity.util.AuthenticationUtil;
import com.activity.util.WebResponse;

@Controller
@Path("/login")
@CrossOrigin("*")
public class LoginController {

	@Autowired
	MemberDAO memberDAO;
	
	@GET
	@Path("/name")
	@Produces(MediaType.APPLICATION_JSON)
	public Response printWelcome() {
		AuthenticationUtil authenticationUtil = new AuthenticationUtil();
		WebResponse webResponse = new WebResponse();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getDetails() + "\n" + authentication.getCredentials() + "\n"
				+ authentication.getPrincipal());
		if(!authenticationUtil.getCurrentUsername().equals("anonymousUser"))
		{
			Member member = new Member();
			member.setMemberEmail(authenticationUtil.getCurrentUsername());
			member = memberDAO.get(member);
			webResponse.OK();
			webResponse.setData(member);
		}
		else
		{
			webResponse.UNPROCESSABLE_ENTITY();
			webResponse.setData("please login!");
		}
		
		return Response.status(webResponse.getStatusCode()).entity(webResponse.getData()).build();

	}
}