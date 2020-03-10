package com.activity.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.activity.util.AuthenticationUtil;

@Controller
@Path("/login")
public class LoginController {

  @GET
  @Path("/name")
  public String printWelcome() {
	  AuthenticationUtil authenticationUtil = new AuthenticationUtil();
	  Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
	  System.out.println(authentication.getDetails()+"\n"+authentication.getCredentials() +"\n"+authentication.getPrincipal());

      return "hello : " + authenticationUtil.getCurrentUsername() + " " + authenticationUtil.checkAuthority() ;

  }
}