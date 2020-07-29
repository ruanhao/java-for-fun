package com.hao.spring.cloud.jwt.service.context;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * This class is used to inject the correlation ID into any outgoing HTTP-based
 * service requests being executed from a RestTemplate instance. This is done to
 * ensure that you can establish a linkage between service calls. To do this
 * you’re going to use a Spring Interceptor that’s being injected into the
 * RestTemplate class
 *
 */
public class UserContextInterceptor implements ClientHttpRequestInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(UserContextInterceptor.class);
	
	// The intercept() method is invoked
	// before the actual HTTP service call
	// occurs by the RestTemplate.
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		HttpHeaders headers = request.getHeaders();
		headers.add(UserContext.AUTHORIZATION_HEADER, UserContextHolder.getContext().getAccessToken());

		return execution.execute(request, body);
	}
}