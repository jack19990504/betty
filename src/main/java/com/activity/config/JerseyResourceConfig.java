package com.activity.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.RequestContextFilter;



/**
 * Created by plen on 2017/11/25.
 */
//@Component
//this class is loaded by JerseyServletBeanConfig
//tag it as a component
//will create "The resource configuration is not modifiable in this context." error
public class JerseyResourceConfig extends ResourceConfig {


    public JerseyResourceConfig() {
        //register(CustomerController.class);
        register(RequestContextFilter.class);
        //register(CustomerDAO.class);
        //register(CustomerDAOImpl.class);
        //packages("com.example.test.*");
    }
    
}