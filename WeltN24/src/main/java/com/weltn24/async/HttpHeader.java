package com.weltn24.async;

import org.springframework.http.HttpHeaders;

/**
 * get HTTP header. this is essential to be able to use RestTemplate.
 * @author mohammad
 *
 */
public class HttpHeader {
	/**
	 * Creates HTTP header. this can be created as singleton pattern
	 * @return HttpHeaders
	 */
	public static HttpHeaders getHttpHeaders()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.set( "User-Agent", "Weltn24/1.0");
		//headers.set( "Accept", "application/json" );
		return headers;
	}
}
