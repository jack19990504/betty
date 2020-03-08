package com.activity.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

	private Authentication authentication;

	public AuthenticationUtil() {
		this.authentication = SecurityContextHolder.getContext().getAuthentication();
		// TODO Auto-generated constructor stub
	}
	
	public String getCurrentUsername() {
	      return authentication.getName();
	   }

	public boolean checkAuthority() {
		boolean isAuthenticated = false;
		if (authentication.getAuthorities().isEmpty()) {
			return isAuthenticated;
		}
		else {	
			String auth = authentication.getAuthorities().toString().replace("[", "").replace("]", "");
			System.out.println(auth + authentication.getName() + authentication.isAuthenticated());
			if(auth.equalsIgnoreCase("ROLE_ANONYMOUS"))
				return isAuthenticated;
			else if(auth.equalsIgnoreCase("0"))
				return isAuthenticated;
			else
				isAuthenticated = true;
			return isAuthenticated;
		}
		
	}

}
