package com.activity.config;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by plen on 2017/11/25.
 */
@Component
public class JerseyServletBeanConfig {

    @Bean
    public ServletRegistrationBean jerseyServlet() {

        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new ServletContainer(), "/api/*");
        registrationBean.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyResourceConfig.class.getName());

        return registrationBean;
    }
   
}