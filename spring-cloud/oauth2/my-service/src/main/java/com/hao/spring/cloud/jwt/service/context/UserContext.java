package com.hao.spring.cloud.jwt.service.context;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * The UserContext class is used to hold the HTTP header values for an individual 
 * service client request being processed by your microservice. 
 * It consists of a getter/setter method that retrieves and stores values 
 * from java.lang.ThreadLocal .
 * @author bit5
 */
@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserContext {
	public static final String AUTHORIZATION_HEADER = "Authorization";

	private String accessToken;


}