package com.coupon.service;

import com.coupon.entity.CouponTemplate;

public interface IAsyncService {

    void asyncConstructCouponByTemplate(CouponTemplate couponTemplate);

}
