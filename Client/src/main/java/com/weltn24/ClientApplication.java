package com.weltn24;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties
public class ClientApplication implements CommandLineRunner {
	@Value("${userServiceUrl}")
	private String userServiceUrl;
	
	@Value("${userPostsServiceUrl}")
	private String userPostsServiceUrl;
	
	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		RestTemplate restTemplate = new RestTemplate();
		//ResponseEntity<String> s = //restTemplate.getForObject("http://jsonplaceholder.typicode.com/users/1", String.class);
		//restTemplate.exchange("http://jsonplaceholder.typicode.com/users/1", HttpMethod.GET, new HttpEntity<String>(getHttpHeaders()), String.class);

    	
    	
    	
		AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

		ListenableFuture<ResponseEntity<String>> future =  asyncRestTemplate.getForEntity(userServiceUrl + "/2", String.class);//asyncRestTemplate.getForObject(userServiceUrl + "/2", String.class);
		future.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
		    @Override
		    public void onSuccess(ResponseEntity<String> entity) {
		    	System.out.println(entity.getBody());
		    }

		    @Override
		    public void onFailure(Throwable t) {
		       
		    }
		});
		
		
	}
	
	HttpHeaders getHttpHeaders()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.set( "User-Agent", "Mozilla/5.0");
		//headers.set( "Accept", "application/json" );
		return headers;
	}
}
