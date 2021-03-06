package com.activity.config;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by plen on 2017/11/25.
 */

public class JerseyServletBeanConfig {
	File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));                  
    MultipartConfigElement multipartConfigElement = new  MultipartConfigElement(uploadDirectory.getAbsolutePath(), 100000000, 1000000000 * 5, 1000000000 / 2); 
	
    @Bean
    public ServletRegistrationBean jerseyServlet() {

        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new ServletContainer(), "/api/*");
        registrationBean.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyResourceConfig.class.getName());
        registrationBean.setMultipartConfig(multipartConfigElement);
        registrationBean.setName("rest");
        return registrationBean;
    }
   
   
}