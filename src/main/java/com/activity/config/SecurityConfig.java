package com.activity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.httpBasic()//設定為httpBasic Authentication
		.and().authorizeRequests().antMatchers("/css/**", "/index","main.css","/api/line").permitAll()
		.antMatchers("/user/**").hasAnyAuthority("0","1")
		.antMatchers("/admin/**","api/**").hasAuthority("1")
		.and().csrf().disable().formLogin().loginPage("/login").failureUrl("/login-error")
	.failureUrl("/login-error").and().exceptionHandling().accessDeniedPage("/");
//				.usernameParameter("memberEmail").passwordParameter("memberPassword")
				
		
//		http.authorizeRequests().antMatchers("/css/**", "/index").permitAll()
//		.antMatchers("/user/**").hasAnyAuthority("0","1")
//		.antMatchers("/admin/**","api/**").hasAuthority("1")
//		.and().formLogin().loginPage("/login").failureUrl("/login-error")
//				.failureUrl("/login-error").and().exceptionHandling().accessDeniedPage("/");
	}

	 
	 @Autowired
	 private DataSource dataSource;
	 
	 @Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		 System.out.println("TEst");
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		 String password = passwordEncoder.encode("a4129889");
		 System.out.println(password.length()+"\n"+password);
		 auth
		   .jdbcAuthentication()
		    .dataSource(dataSource).usersByUsernameQuery(
		    	    "select memberEmail,memberEncodedPassword,memberEnabled from member where memberEmail = ?")
		    .authoritiesByUsernameQuery(
		     "select memberEmail,memberType from member where memberEmail = ?").passwordEncoder(passwordEncoder);
	 }


}