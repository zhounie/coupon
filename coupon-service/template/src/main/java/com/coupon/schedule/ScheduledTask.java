package com.coupon.schedule;

import com.coupon.dao.CouponTemplateDao;
import com.coupon.entity.CouponTemplate;
import com.coupon.vo.TemplateRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ScheduledTask {

    private final CouponTemplateDao couponTemplateDao;


    @Autowired
    public ScheduledTask(CouponTemplateDao couponTemplateDao) {
        this.couponTemplateDao = couponTemplateDao;
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void offlineCouponTemplate() {
        log.info("offlineCouponTemplate");
        List<CouponTemplate> couponTemplates = couponTemplateDao.findAllByExpired(false);
        if(CollectionUtils.isEmpty(couponTemplates)) {
            log.info("Done To expire couponTemplate.");
            return;
        }
        Date cur = new Date();
        List<CouponTemplate> expiredTemplates = new ArrayList<>(couponTemplates.size());
        couponTemplates.forEach(t -> {
            TemplateRule rule = t.getRule();
            if (rule.getExpiration().getDeadline() < cur.getTime()) {
                t.setExpired(true);
                expiredTemplates.add(t);
            }
        });
        if (!CollectionUtils.isEmpty(expiredTemplates)) {
            log.info("expire couponTemplate num: {}", couponTemplateDao.saveAll(expiredTemplates));
        }
        log.info("Done To expire couponTemplate.");
    }
}
