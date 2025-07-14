package com.coupon.service.impl;

import com.coupon.dao.CouponTemplateDao;
import com.coupon.entity.CouponTemplate;
import com.coupon.exception.CouponException;
import com.coupon.service.IAsyncService;
import com.coupon.service.IBuildTemplateService;
import com.coupon.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BuildTemplateServiceImpl implements IBuildTemplateService {

    private final IAsyncService asyncService;

    private final CouponTemplateDao couponTemplateDao;

    @Autowired
    public BuildTemplateServiceImpl(IAsyncService asyncService, CouponTemplateDao couponTemplateDao) {
        this.asyncService = asyncService;
        this.couponTemplateDao = couponTemplateDao;
    }

    @Override
    public CouponTemplate buildTemplate(TemplateRequest request) throws CouponException {

        if (!request.validate()) {
            throw new CouponException("Build template error");
        }

        if (couponTemplateDao.findByName(request.getName()) != null) {
            throw new CouponException("Build template exists");
        }

        CouponTemplate couponTemplate = requestToTemplate(request);

        couponTemplate = couponTemplateDao.save(couponTemplate);

        asyncService.asyncConstructCouponByTemplate(couponTemplate);

        return couponTemplate;
    }

    private CouponTemplate requestToTemplate(TemplateRequest request) {

        return new CouponTemplate(
                request.getName(),
                request.getLogo(),
                request.getDesc(),
                request.getCategory(),
                request.getProductLine(),
                request.getCount(),
                request.getUserId(),
                request.getTarget(),
                request.getTemplateRule()
        );
    }
}
