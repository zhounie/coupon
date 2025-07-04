package com.coupon.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public abstract class AbstractZuulFilter extends ZuulFilter {

    RequestContext context;

    private final static String NEXT = "next";

    @Override
    public boolean shouldFilter() {

        RequestContext requestContext = RequestContext.getCurrentContext();

        return (boolean) requestContext.getOrDefault(NEXT, true);
    }

    @Override
    public Object run() throws ZuulException {

        context = RequestContext.getCurrentContext();

        return cRun();
    }

    protected abstract Object cRun();

    Object fail(int code, String msg) {
        context.set(NEXT, false);
        context.setSendZuulResponse(false);
        context.getResponse().setContentType("text/html;charset=UTF-8");
        context.setResponseStatusCode(code);
        context.setResponseBody(
                String.format("{\"result\": \"%s!\"}", msg)
        );
        return null;
    }

    Object success() {
        context.set(NEXT, true);
        return null;
    }

}
