package com.weltn24.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weltn24.BeanConfiguration;

@Service
public class UserService {	
	@Autowired
	BeanConfiguration beanConfiguration;
	

	
    /*
    @Async
    public Future<Integer> processNews(News news) {
    	NewsProcessorTask task = beanConfiguration.newsProcessorTask(news);
    	ThreadPool.executeTask(task);
        
        return new AsyncResult<Integer>(0);
    }
    */

}
