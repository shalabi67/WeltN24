package com.weltn24.async;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import com.weltn24.user.Post;


public class UserPostsTask implements Runnable  {
	private String serviceUrl;
	private DeferredResult<Post[]> result;
	public UserPostsTask(String serviceUrl, DeferredResult<Post[]> result) {
		this.serviceUrl = serviceUrl;
		this.result = result;
	}

	@Override
	public void run() {
		RestTemplate restTemplate = new RestTemplate();
  		Post[] userPosts = new Post[0];
  		try {
  			ResponseEntity<Post[]> userEntity = restTemplate.exchange(serviceUrl, 
  					HttpMethod.GET, 
  					new HttpEntity<String>(HttpHeader.getHttpHeaders()), 
  					Post[].class);
  			
  			userPosts = userEntity.getBody();
  		}
  		catch(RestClientException e) {
  			result.setErrorResult(e);
  			return;
  		} 
  		result.setResult(userPosts);
		
	}

}
