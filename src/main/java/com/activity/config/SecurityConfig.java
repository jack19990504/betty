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
@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.httpBasic()//設定為httpBasic Authentication
		.and().authorizeRequests()
		.antMatchers("/api/line/**").permitAll()
		//允許line bot不登入也可存取API
		.antMatchers(HttpMethod.GET ,"/api/activity/**").permitAll()
		//任何人皆可抓取所有活動清單or任一活動
		.antMatchers("/api/member/check").permitAll()
		.antMatchers(HttpMethod.POST ,"/api/member/**").permitAll()
		
		.antMatchers(HttpMethod.GET ,"/api/registration/","/api/login/name/").hasAnyAuthority("0","1")
		.antMatchers(HttpMethod.POST ,"/api/files/uploadFace/**").permitAll()
		//會員或管理員可使用這隻POST API上傳人臉
		.antMatchers("/api/member/**").hasAnyAuthority("1")
		//會員與管理者皆可存取member API
		.antMatchers("/api/**").hasAuthority("1") 
		.and().cors().and()
		.csrf().disable()
		.formLogin().loginProcessingUrl("/login").failureUrl("http://localhost:3000/signin?loginError=true")
		.and().logout().logoutUrl("/logout").logoutSuccessUrl("http://localhost:3000/").invalidateHttpSession(true).deleteCookies("JSESSIONID")
		;


		
		
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