package com.activity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.httpBasic()//設定為httpBasic Authentication
		.and().authorizeRequests()
		
		//全部使用者都可使用
		.antMatchers("/api/line/**","/api/member/check/**","/api/files/uploadFace/**").permitAll()
		//允許line bot可存取API
		//任何人皆可抓取所有活動清單or任一活動
		//未註冊會員可以使用以下方法的api
		.antMatchers(HttpMethod.GET ,"/api/activity/**").permitAll()
		.antMatchers(HttpMethod.POST ,"/api/member/**").permitAll()
		
		
		//會員以上才可使用
		.antMatchers("/api/registration/**").hasAnyAuthority("0","1")
		.antMatchers(HttpMethod.GET ,"/api/login/name/**","/api/photo/**").hasAnyAuthority("0","1")
		.antMatchers(HttpMethod.POST ,"/api/registration/**","/api/organizer/","/api/feedback/**").hasAnyAuthority("0","1")
		.antMatchers("/api/member/**").hasAnyAuthority("0","1")
		
		//管理者可使用所有剩下的API
		
		.antMatchers("/api/**").hasAuthority("1") 
		.and().cors().and()
		.csrf().disable()
		.formLogin().loginProcessingUrl("/login").defaultSuccessUrl("http://localhost:3000/",true).failureUrl("http://localhost:3000/signin?loginError=true")
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("http://localhost:3000/");


		
		
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
		 
		 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		 
		 auth
		   .jdbcAuthentication()
		    .dataSource(dataSource).usersByUsernameQuery(
		    	    "select memberEmail,memberEncodedPassword,memberEnabled from member where memberEmail = ?")
		    .authoritiesByUsernameQuery(
		     "select memberEmail,memberType from member where memberEmail = ?").passwordEncoder(passwordEncoder);
	 }


}