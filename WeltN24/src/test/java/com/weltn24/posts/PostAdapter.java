package com.weltn24.posts;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;

import com.weltn24.WeltN24Application;
import com.weltn24.async.HttpHeader;
import com.weltn24.user.Post;

@Component
public class PostAdapter {
	@Autowired
	WeltN24Application application;

	public Post[] createUserPosts(long userId) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Post[]> userEntity = restTemplate.exchange(getUserId(application.getPostsServiceUrl(), userId), 
					HttpMethod.GET, 
					new HttpEntity<String>(HttpHeader.getHttpHeaders()), 
					Post[].class);
			
		Post post[] = userEntity.getBody();
		return post;
	}
	
	public void comparePost(Post post, ResultActions result, int i) throws Exception {
		String start = "$[" + i +"]";
		result.andExpect(jsonPath(start + ".id", is(post.getId())))
		.andExpect(jsonPath(start + ".userId", is(post.getUserId())))
		.andExpect(jsonPath(start + ".title", is(post.getTitle())))
		.andExpect(jsonPath(start + ".body", is(post.getBody())));
	}
	
	private String getUserId(String url, long userId) {
		return url + userId;
	}
	

}
