package com.weltn24;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.weltn24.async.UserProcessorTask;

@Configuration

public class BeanConfiguration {
	@Bean
	@Scope(value="prototype", proxyMode = ScopedProxyMode.NO)		 
    public UserProcessorTask newsProcessorTask() {
        return new UserProcessorTask();
    }
}
