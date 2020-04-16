package com.activity.controller;

import javax.servlet.http.HttpServletResponse;
/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Joe Grandja
 */

@Controller
@CrossOrigin
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
	@CrossOrigin
	@RequestMapping("/login")
	public Response login(HttpServletResponse response) {
		
		
		response.addHeader("Access-Control-Allow-Origin" ,"*");
		response.addHeader("Location", "http://localhost:3000/signin");
		response.setStatus(302);
		return Response.status(401).build();
	}

	@RequestMapping("/login?error")
	public void loginError(Model model,HttpServletResponse response) {
		model.addAttribute("loginError", true);
		response.setHeader("Location", "http://localhost:3000/signin?loginError=true");
	    response.setStatus(302);
	}
	
	

}
