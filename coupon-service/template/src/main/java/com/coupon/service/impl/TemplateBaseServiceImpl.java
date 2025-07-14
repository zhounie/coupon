package com.coupon.service.impl;

import com.coupon.dao.CouponTemplateDao;
import com.coupon.entity.CouponTemplate;
import com.coupon.exception.CouponException;
import com.coupon.service.ITemplateBaseService;
import com.coupon.vo.CouponTemplateSDK;
import com.coupon.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TemplateBaseServiceImpl implements ITemplateBaseService {

    private final CouponTemplateDao couponTemplateDao;

    public TemplateBaseServiceImpl(CouponTemplateDao couponTemplateDao) {
        this.couponTemplateDao = couponTemplateDao;
    }

    @Override
    public CouponTemplate buildTemplateInfo(Integer id) throws CouponException {

        Optional<CouponTemplate> couponTemplateOptional = couponTemplateDao.findById(id);
        if (!couponTemplateOptional.isPresent()) {
            throw new CouponException("Template is not found: " + id);
        }

        return couponTemplateOptional.get();
    }

    @Override
    public List<CouponTemplateSDK> findAllUsableTemplate() {

        List<CouponTemplate> templates = couponTemplateDao.findAllByAvailableAndExpired(true, false);

        return templates.stream().map(this::templateToTemplateSDK).collect(Collectors.toList());
    }

    @Override
    public Map<Integer, CouponTemplateSDK> findIdsToTemplateSDK(Collection<Integer> ids) {

        List<CouponTemplate> templates = couponTemplateDao.findAllById(ids);

        return templates.stream().map(this::templateToTemplateSDK)
                .collect(Collectors.toMap(
                        CouponTemplateSDK::getId,
                        Function.identity()
                ));
    }

    private CouponTemplateSDK templateToTemplateSDK(CouponTemplate couponTemplate) {
        return new CouponTemplateSDK(
                couponTemplate.getId(),
                couponTemplate.getName(),
                couponTemplate.getLogo(),
                couponTemplate.getDesc(),
                couponTemplate.getCategory().getCode(),
                couponTemplate.getProductLine().getCode(),
                couponTemplate.getKey(),
                couponTemplate.getTarget().getCode(),
                couponTemplate.getRule()
        );
    }
}
