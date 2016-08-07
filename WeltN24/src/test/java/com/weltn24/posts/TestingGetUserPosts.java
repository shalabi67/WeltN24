package com.weltn24.posts;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;

import com.weltn24.WeltN24Application;
import com.weltn24.user.Post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestingGetUserPosts {
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Autowired
	WeltN24Application application;
	
	@Autowired
	PostAdapter postAdapter;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }
	
	@Test
	public void testGetUserPosts() throws Exception {
		ResultActions result = getUserPosts(1);	
		validate(result, 1);
	}

	private ResultActions getUserPosts(long userId) throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/posts?userId=" + userId))
				.andExpect(request().asyncStarted())
		        .andReturn();
		
		ResultActions result = this.mockMvc.perform(asyncDispatch(mvcResult));
		return result;
	}
	private void validate(ResultActions result, int userId) throws Exception {
		Post posts[] = postAdapter.createUserPosts(userId);
		int i = 0;
		for(Post post : posts) {
			postAdapter.comparePost(post, result, i);
			i++;
		}
	}
	private void validateEmpty(ResultActions result) throws Exception {
		result.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test
	public void testUserPostsNotFound() {
		ResultActions result;
		try {
			result = getUserPosts(-1);
			validateEmpty(result);
		} catch (Exception e) {
			if(e.getCause() instanceof HttpClientErrorException) {
				HttpClientErrorException ex = (HttpClientErrorException) e.getCause();
				Assert.assertEquals(404, ex.getRawStatusCode());
				return;
			}
			e.printStackTrace();
			fail();
		}
					
	}

}
