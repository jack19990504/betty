package com.activity.config;

import javax.servlet.MultipartConfigElement;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by plen on 2017/11/25.
 */
@Component
public class JerseyServletBeanConfig {
	@Autowired
	private MultipartConfigElement multipartConfigElement;
    @Bean
    public ServletRegistrationBean jerseyServlet() {

        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new ServletContainer(), "/api/*");
        registrationBean.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyResourceConfig.class.getName());
        registrationBean.setMultipartConfig(multipartConfigElement);
        return registrationBean;
    }
   
}