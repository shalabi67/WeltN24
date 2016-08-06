package com.weltn24.async;

import org.springframework.http.HttpHeaders;

public class HttpHeader {
	public static HttpHeaders getHttpHeaders()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.set( "User-Agent", "Mozilla/5.0");
		//headers.set( "Accept", "application/json" );
		return headers;
	}
}
