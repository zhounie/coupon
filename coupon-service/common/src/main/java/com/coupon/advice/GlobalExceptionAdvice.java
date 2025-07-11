package com.coupon.advice;


import com.coupon.exception.CouponException;
import com.coupon.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = CouponException.class)
    public CommonResponse<String> handlerCouponException(
        HttpServletRequest req,
        CouponException exception
    ) {
        CommonResponse<String> response = new CommonResponse<>(500, "business error");
        response.setData(exception.getMessage());

        return response;
    }

}
