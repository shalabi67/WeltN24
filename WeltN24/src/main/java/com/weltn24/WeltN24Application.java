package com.weltn24;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAsync
public class WeltN24Application {
	@Value("${userServiceUrl}")
	private String userServiceUrl;
	
	@Value("${postsServiceUrl}")
	private String postsServiceUrl;
	
	public String getUsersServiceUrl() {
		return userServiceUrl;
	}
	
	public String getPostsServiceUrl() {
		return postsServiceUrl;
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(WeltN24Application.class, args);
	}
}
