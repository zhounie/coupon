package com.coupon.filter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class RateLimiterFilter extends AbstractPreZuulFilter {

    RateLimiter rateLimiter =  RateLimiter.create(2.0);

    @Override
    protected Object cRun() {

        HttpServletRequest request = context.getRequest();

        if (rateLimiter.tryAcquire()) {
            log.info("get rate token success");
            return success();
        }
        log.error("get rate token fail, {}", request.getRequestURI());
        return fail(402, "get rate token fail");
    }

    @Override
    public int filterOrder() {
        return 2;
    }
}
