package com.hao.spring.cloud.license.context;

import org.springframework.util.Assert;

/**
 * The UserContextHolder class is used to store the UserContext in a 
 * ThreadLocal class. 
 * Once it’s stored in the ThreadLocal storage, any code that’s executed for 
 * a request will use the UserContext object stored in the UserContextHolder.
 */
public class UserContextHolder {
	
	private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();

	public static final UserContext getContext() {
		UserContext context = userContext.get();

		if (context == null) {
			context = createEmptyContext();
			userContext.set(context);

		}
		return userContext.get();
	}

	public static final void setContext(UserContext context) {
		Assert.notNull(context, "Only non-null UserContext instances are permitted");
		userContext.set(context);
	}

	public static final UserContext createEmptyContext() {
		return new UserContext();
	}
}
