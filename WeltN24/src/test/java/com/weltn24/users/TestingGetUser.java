package com.weltn24.users;

import static org.hamcrest.CoreMatchers.is;
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
import com.weltn24.user.User;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestingGetUser {
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Autowired
	WeltN24Application application;
	
	@Autowired
	UserAdapter userAdapter;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }
	
	@Test
	public void testGetUser() throws Exception {
		ResultActions result = getUser(1);	
		validate(result, 1);
	}

	private ResultActions getUser(long userId) throws Exception {
		MvcResult mvcResult = mockMvc.perform(get(application.getUsersServiceUrl() + userId))
				.andExpect(request().asyncStarted())
		        .andReturn();
		
		ResultActions result = this.mockMvc.perform(asyncDispatch(mvcResult));
		return result;
	}
	private void validate(ResultActions result, int userId) throws Exception {
		User user = userAdapter.createUser(userId);
		userAdapter.compareUser(user, result);
	}
	private void validateFail(ResultActions result) throws Exception {
		result
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void testUserNotFound() {
		ResultActions result;
		try {
			result = getUser(-1);
			validateFail(result);
			fail();
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
