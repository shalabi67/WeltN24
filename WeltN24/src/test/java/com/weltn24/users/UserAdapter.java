package com.weltn24.users;

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
import com.weltn24.user.User;

@Component
public class UserAdapter {
	@Autowired
	WeltN24Application application;

	public User createUser(long userId) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<User> userEntity = restTemplate.exchange(getUserId(application.getUsersServiceUrl(), userId), 
					HttpMethod.GET, 
					new HttpEntity<String>(HttpHeader.getHttpHeaders()), 
					User.class);
			
		User user = userEntity.getBody();
		return user;
	}
	
	public void compareUser(User user, ResultActions result) throws Exception {
		compareUserInfo(user, result);
		compareAddress(user, result);
		compareGeo(user, result);
		compareCompany(user, result);
	}
	public void compareUserInfo(User user, ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.id", is(user.getId())))
		.andExpect(jsonPath("$.name", is(user.getName())))
		.andExpect(jsonPath("$.username", is(user.getUsername())))
		.andExpect(jsonPath("$.email", is(user.getEmail())))
		.andExpect(jsonPath("$.phone", is(user.getPhone())))
		.andExpect(jsonPath("$.website", is(user.getWebsite())));
		
	}
	public void compareAddress(User user, ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.address.street", is(user.getAddress().getStreet())))
		.andExpect(jsonPath("$.address.suite", is(user.getAddress().getSuite())))
		.andExpect(jsonPath("$.address.city", is(user.getAddress().getCity())))
		.andExpect(jsonPath("$.address.zipcode", is(user.getAddress().getZipcode())));
	}
	public void compareGeo(User user, ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.address.geo.lat", is(user.getAddress().getGeo().getLat())))
		.andExpect(jsonPath("$.address.geo.lng", is(user.getAddress().getGeo().getLng())));
	}
	public void compareCompany(User user, ResultActions result) throws Exception {
		result.andExpect(jsonPath("$.company.name", is(user.getCompany().getName())))
		.andExpect(jsonPath("$.company.catchPhrase", is(user.getCompany().getCatchPhrase())))
		.andExpect(jsonPath("$.company.bs", is(user.getCompany().getBs())));
	}
	
	private String getUserId(String url, long userId) {
		return url + userId;
	}
	

}
