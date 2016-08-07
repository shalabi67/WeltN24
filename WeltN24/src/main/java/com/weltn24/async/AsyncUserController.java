package com.weltn24.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.weltn24.WeltN24Application;
import com.weltn24.user.Post;
import com.weltn24.user.User;

@RestController
public class AsyncUserController {
	
	@Autowired
	WeltN24Application application;
	
	@RequestMapping(value ="/users/{userId}", method = RequestMethod.GET)
    public DeferredResult<User> getUser(@PathVariable Long userId) {
		String serviceUrl = application.getUsersServiceUrl() + userId;
		
		DeferredResult<User> deferredResult = new DeferredResult<User>();
	    UserTask task = new UserTask(serviceUrl, deferredResult);
	    
	    ExecutorService service =  Executors.newSingleThreadExecutor();
	    service.submit(task);
	    
	    
	    return deferredResult;
	    		
    }

    @RequestMapping(value ="/posts", method = RequestMethod.GET)
    public DeferredResult<Post[]> getUserPosts(@RequestParam(value="userId") long userId) {
    	String serviceUrl = application.getPostsServiceUrl() + userId;
		
		DeferredResult<Post[]> deferredResult = new DeferredResult<Post[]>();
	    UserPostsTask task = new UserPostsTask(serviceUrl, deferredResult);
	    
	    ExecutorService service =  Executors.newSingleThreadExecutor();
	    service.submit(task);
	    
	    
	    return deferredResult;
    }	

}
