package com.coupon.service.impl;

import com.coupon.constant.Constant;
import com.coupon.dao.CouponTemplateDao;
import com.coupon.entity.CouponTemplate;
import com.coupon.service.IAsyncService;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AsyncServiceImpl implements IAsyncService {

    private final CouponTemplateDao couponTemplateDao;

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public AsyncServiceImpl(CouponTemplateDao couponTemplateDao, StringRedisTemplate stringRedisTemplate) {
        this.couponTemplateDao = couponTemplateDao;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Async("getAsyncExecutor")
    @Override
    @SuppressWarnings("all")
    public void asyncConstructCouponByTemplate(CouponTemplate couponTemplate) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        Set<String> couponCodes = buildCouponCode(couponTemplate);

        String redisKey = String.format("%s%s", Constant.RedisPrefix.COUPON_TEMPLATE, couponTemplate.getId().toString());

        log.info("Push CouponCode to Redis: {}", stringRedisTemplate.opsForList().rightPushAll(redisKey, couponCodes));

        couponTemplate.setAvailable(true);

        couponTemplateDao.save(couponTemplate);

        stopwatch.stop();
        log.info("Construct CouponCode By Template Cost: {}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @SuppressWarnings("all")
    private Set<String> buildCouponCode(CouponTemplate couponTemplate) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Set<String> result = new HashSet<>(couponTemplate.getCount());
        String prefix4 = couponTemplate.getProductLine().getCode().toString() +
                couponTemplate.getCategory().getCode();
        String date = new SimpleDateFormat("yyyyMMdd").format(couponTemplate.getCreateTime());

        for (int i = 0; i < couponTemplate.getCount(); i++) {
            result.add(prefix4 + buildCouponCodeSuffix14(date));
        }
        while(result.size() < couponTemplate.getCount()) {
            result.add(prefix4 + buildCouponCodeSuffix14(date));
        }

        assert result.size() == couponTemplate.getCount();

        stopwatch.stop();

        log.info("Build Coupon Code Cost: {}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        return result;
    }

    private String buildCouponCodeSuffix14(String date) {
        char[] bases = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        List<Character> chars = date.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(chars);
        String mid6 = chars.stream().map(Object::toString).collect(Collectors.joining());
        String suffix8 = RandomStringUtils.random(1, bases) + RandomStringUtils.randomNumeric(7);

        return mid6 + suffix8;
    }
}
