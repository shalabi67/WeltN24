package com.weltn24.async;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.weltn24.WeltN24Application;
import com.weltn24.user.User;

@RestController
public class AsyncUserController {
	
	@Autowired
	WeltN24Application application;
	
	
	@Async
	@RequestMapping(value ="/users/{userId}", method = RequestMethod.GET)
    public @ResponseBody Future<User> getUser(@PathVariable Long userId) {
		String serviceUrl = application.getUsersServiceUrl() + userId;
		
		RestTemplate restTemplate = new RestTemplate();
		User user = new User();
		try {
			ResponseEntity<User> userEntity = restTemplate.exchange(serviceUrl, 
					HttpMethod.GET, 
					new HttpEntity<String>(HttpHeader.getHttpHeaders()), 
					User.class);
			
			user = userEntity.getBody();
			Thread.sleep(10000);
		}
		catch(RestClientException e) {
			
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return new AsyncResult<User>(user);
    }
	@Async
    @RequestMapping(value ="/posts", method = RequestMethod.GET)
    public String getUserPosts(@RequestParam(value="userId") int userId) {
    	//Song[] songs = template.getForObject(url, Song[].class); 
    	return "Success";
    }	

}
