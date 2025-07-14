package com.coupon.service;

import com.coupon.entity.CouponTemplate;
import com.coupon.exception.CouponException;
import com.coupon.vo.TemplateRequest;

public interface IBuildTemplateService {

    CouponTemplate buildTemplate(TemplateRequest request) throws CouponException;

}
