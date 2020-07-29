package com.hao.spring.cloud.jwt.service.context;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Often in a REST-based environment you are going to want to pass
 * contextual information to a service call that will help you operationally
 * manage the service. For example, you might pass a correlation ID or
 * authentication token in the HTTP header of the REST call that can then be
 * propagated to any downstream service calls. The correlation ID allows you to
 * have a unique identifier that can be traced across multiple service
 * calls in a single transaction.
 *
 * To make this value available anywhere in your service call, you might use a Spring Filter
 * class to intercept every call into your REST service and retrieve this information from
 * the incoming HTTP request and store this contextual information in a custom
 * UserContext object. Then, anytime your code needs to access this value in your
 * REST service call, your code can retrieve the UserContext from the ThreadLocal
 * storage variable and read the value.
 */
@Component
public class UserContextFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;


        UserContextHolder.getContext().setAccessToken(httpServletRequest.getHeader(UserContext.AUTHORIZATION_HEADER));

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}

