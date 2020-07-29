package com.hao.spring.cloud.zuul.util;

import com.netflix.zuul.context.RequestContext;

public class FilterUtils {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN = "tmx-auth-token";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORG_ID = "tmx-org-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    public static String getCorrelationId() {
        RequestContext ctx = RequestContext.getCurrentContext();

        // You first check to see if the tmx-correlation-ID is already
        // set on the HTTP Headers for the incoming request
        if (ctx.getRequest().getHeader(CORRELATION_ID) != null) {
            return ctx.getRequest().getHeader(CORRELATION_ID);
        } else {
            // If it isn’t there, you then check the ZuulRequestHeaders .
            // Zuul doesn’t allow you to directly add or modify the HTTP
            // request headers on an incoming request. If we add the
            // tmx-correlation-id and then try to access it again later in
            // the filter, it won’t be available as part of the
            // ctx.getRequestHeader( ) call. To work around this, you use
            // the FilterUtils getCorrelationId() method.
            return ctx.getZuulRequestHeaders().get(CORRELATION_ID);
        }
    }

    /**
     * when you want to add a value to the HTTP request headers,
     * you use the RequestContext’s addZuulRequestHeader() method.
     * This method will maintain a separate map of HTTP headers that
     * were added while a request was flowing through the filters with your Zuul server.
     * The data contained within the ZuulRequestHeader map will be merged
     * when the target service isinvoked by your Zuul server.
     */
    public static void setCorrelationId(String correlationId) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }

    public static final String getOrgId() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(ORG_ID) != null) {
            return ctx.getRequest().getHeader(ORG_ID);
        } else {
            return ctx.getZuulRequestHeaders().get(ORG_ID);
        }
    }

    public static void setOrgId(String orgId) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(ORG_ID, orgId);
    }

    public static final String getUserId() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(USER_ID) != null) {
            return ctx.getRequest().getHeader(USER_ID);
        } else {
            return ctx.getZuulRequestHeaders().get(USER_ID);
        }
    }

    public static void setUserId(String userId) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(USER_ID, userId);
    }

    public static final String getAuthToken() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getRequest().getHeader(AUTH_TOKEN);
    }

    public static String getServiceId() {
        RequestContext ctx = RequestContext.getCurrentContext();

        // We might not have a service id if we are using a static, non-eureka route.
        if (ctx.get("serviceId") == null)
            return "";
        return ctx.get("serviceId").toString();
    }

}
