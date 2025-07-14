package com.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponTemplateSDK {

    private Integer id;

    private String name;

    private String logo;

    private String desc;

    private String category;

    private Integer productLine;

    private String key;

    private Integer target;

    private TemplateRule templateRule;

}
