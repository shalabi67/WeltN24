package com.weltn24.async;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import com.weltn24.user.User;

/**
 * A background task to get specific user.
 * @author mohammad
 * @see AsyncUserController
 */
public class UserTask implements Runnable  {
	private String serviceUrl;
	private DeferredResult<User> result;
	public UserTask(String serviceUrl, DeferredResult<User> result) {
		this.serviceUrl = serviceUrl;
		this.result = result;
	}

	@Override
	public void run() {
		RestTemplate restTemplate = new RestTemplate();
  		User user = new User();
  		try {
  			ResponseEntity<User> userEntity = restTemplate.exchange(serviceUrl, 
  					HttpMethod.GET, 
  					new HttpEntity<String>(HttpHeader.getHttpHeaders()), 
  					User.class);
  			
  			user = userEntity.getBody();
  		}
  		catch(RestClientException e) {
  			result.setErrorResult(e);
  			return;
  		} 
  		result.setResult(user);
		
	}

}
