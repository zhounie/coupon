package com.coupon.service;

import com.coupon.entity.CouponTemplate;
import com.coupon.exception.CouponException;
import com.coupon.vo.CouponTemplateSDK;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ITemplateBaseService {

    CouponTemplate buildTemplateInfo(Integer id) throws CouponException;

    List<CouponTemplateSDK> findAllUsableTemplate();

    Map<Integer, CouponTemplateSDK> findIdsToTemplateSDK(Collection<Integer> ids);

}
