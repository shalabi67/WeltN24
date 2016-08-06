package com.weltn24.async;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.weltn24.WeltN24Application;
import com.weltn24.user.User;

@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class UserProcessorTask implements Runnable {	
	
	@Autowired
	WeltN24Application application;
	
	public String serviceUrl;
	
	List<User> list;
	public void run() {
		this.serviceUrl = application.getUsersServiceUrl();
		RestTemplate restTemplate = new RestTemplate();
		
		User user = restTemplate.getForObject(serviceUrl, User.class);

	}

}
