package com.activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;


//123456489
@SpringBootApplication
@EnableJdbcHttpSession
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
