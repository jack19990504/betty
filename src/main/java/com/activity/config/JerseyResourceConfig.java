package com.activity.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.web.filter.RequestContextFilter;
import com.activity.controller.*;
import com.activity.controller.rest.ActivityMemberController;
import com.activity.controller.rest.LineController;
import com.activity.controller.rest.MemberController;


/**
 * Created by plen on 2017/11/25.
 */
//@Component
//this class is loaded by JerseyServletBeanConfig
//tag it as a component
//will create "The resource configuration is not modifiable in this context." error
public class JerseyResourceConfig extends ResourceConfig {


    public JerseyResourceConfig() {
        register(MemberController.class);
        register(RequestContextFilter.class);
        register(LineController.class);
        register(ActivityMemberController.class);
        //packages("com.example.test.*");
    }
    
}