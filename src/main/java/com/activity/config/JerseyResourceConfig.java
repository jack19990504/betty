package com.activity.config;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.web.filter.RequestContextFilter;
import com.activity.controller.*;
import com.activity.controller.rest.ActivityController;
import com.activity.controller.rest.EngineController;
import com.activity.controller.rest.FileUploadController;
import com.activity.controller.rest.LineController;
import com.activity.controller.rest.MemberController;
import com.activity.controller.rest.OrganizerController;
import com.activity.controller.rest.RegistrationController;
import com.activity.controller.rest.TrainFaceController;


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
        register(RegistrationController.class);
        register(EngineController.class);
        register(FileUploadController.class);
        register(MultiPartFeature.class);
        register(OrganizerController.class);
        register(MainController.class);
        register(LoginController.class);
        register(ActivityController.class);
        //packages("com.example.test.*");
    }
    
}