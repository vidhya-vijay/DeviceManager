package com.cmad.DeviceManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MessageApplication extends SpringBootServletInitializer {
	
	@Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder MessageApplication) {
        return MessageApplication.sources(MessageApplication.class);
        }
	public static void main(String[] args) {
		SpringApplication.run(MessageApplication.class, args);
	}

}
