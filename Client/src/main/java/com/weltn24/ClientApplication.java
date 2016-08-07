package com.weltn24;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAsync
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

		String result = restTemplate.getForObject(userServiceUrl + "/2", String.class);
		System.out.println(result);
		//result.get();
    	/*
		//TaskExecutorAdapter executer = new TaskExecutorAdapter(Executors.newFixedThreadPool(2));
		AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

		ListenableFuture<ResponseEntity<String>> future =  asyncRestTemplate.getForEntity(userServiceUrl + "/2", String.class);//asyncRestTemplate.getForObject(userServiceUrl + "/2", String.class);
		try {
	        // waits for the service to send the response
	        Thread.sleep(1000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	        // Need assertions
	    }
		while(!future.isDone()) {
			try {
		        // waits for the service to send the response
		        Thread.sleep(10);
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		        // Need assertions
		    }
		}
		ResponseEntity<String> entity = future.get();
		System.out.println(entity.getBody());
		
		future.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
		    @Override
		    public void onSuccess(ResponseEntity<String> entity) {
		    	System.out.println(entity.getBody());
		    }

		    @Override
		    public void onFailure(Throwable t) {
		       
		    }
		});
		*/
		
		
		
		
	}
	
	HttpHeaders getHttpHeaders()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.set( "User-Agent", "Mozilla/5.0");
		//headers.set( "Accept", "application/json" );
		return headers;
	}
}
