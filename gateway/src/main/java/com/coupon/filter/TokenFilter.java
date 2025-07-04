package com.coupon.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class TokenFilter extends AbstractPreZuulFilter {

    @Override
    protected Object cRun() {

        HttpServletRequest request = context.getRequest();
        log.info(
                String.format(
                    "%s request %s",
                    request.getMethod(),
                    request.getRequestURL().toString()
                )
        );

        Object token = request.getParameter("token");

        if (token == null) {
            log.error("token is null");
            return fail(401, "token is null");
        }

        return success();
    }

    @Override
    public int filterOrder() {
        return 1;
    }

}
