package com.activity.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Joe Grandja
 */

@Controller
@CrossOrigin("*")
public class MainController {

	@RequestMapping("/")
	public String root() {
		return "redirect:/index";
	}
	
	@RequestMapping("/index")
	public void index(HttpServletResponse response) {
		response.setHeader("Location", "http://localhost:3000/");
	    response.setStatus(302);
	}

	@RequestMapping("/user/index")
	public String userIndex() {
		return "user/index";
	}

	@RequestMapping("/admin/index")
	public String adminIndex() {
		return "admin/index";
	}
	@CrossOrigin("*")
	@RequestMapping("/login")
	public void login(HttpServletResponse response) {
		
		
		response.addHeader("Access-Control-Allow-Origin" ,"*");
		response.addHeader("Location", "http://localhost:3000/");
		response.setStatus(302);
		
	}

	@RequestMapping("/login?error")
	public void loginError(Model model,HttpServletResponse response) {
		System.out.println("fuck");
		model.addAttribute("loginError", true);
		response.setHeader("Location", "http://localhost:3000/signin?loginError=true");
	    response.setStatus(302);
	}
	
	

}
