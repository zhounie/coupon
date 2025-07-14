package com.coupon.entity;

import com.coupon.constant.CouponCategory;
import com.coupon.constant.DistributeTarget;
import com.coupon.constant.ProductLine;
import com.coupon.converter.CouponCategoryConverter;
import com.coupon.converter.DistributeTargetConverter;
import com.coupon.converter.ProductLineConverter;
import com.coupon.converter.TemplateRuleConverter;
import com.coupon.serialization.CouponTemplateSerialize;
import com.coupon.vo.TemplateRule;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon_template")
@JsonSerialize(using = CouponTemplateSerialize.class)
public class CouponTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Integer id;

    @Column(name="available", nullable = false)
    private Boolean available;

    @Column(name="expired", nullable = false)
    private Boolean expired;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="logo", nullable = false)
    private String logo;

    @Column(name="intro", nullable = false)
    private String desc;

    @Column(name="category", nullable = false)
    @Convert(converter = CouponCategoryConverter.class)
    private CouponCategory category;

    @Column(name="product_line", nullable = false)
    @Convert(converter = ProductLineConverter.class)
    private ProductLine productLine;

    @Column(name="coupon_count", nullable = false)
    private Integer count;

    @CreatedDate
    @UpdateTimestamp
    @CreatedBy
    @Column(name="create_time", nullable = false)
    private Date createTime;

    @Column(name="user_id", nullable = false)
    private Long userId;

    @Column(name="template_key", nullable = false)
    private String key;

    @Column(name="target", nullable = false)
    @Convert(converter =  DistributeTargetConverter.class)
    private DistributeTarget target;

    @Column(name="rule", nullable = false)
    @Convert(converter = TemplateRuleConverter.class)
    private TemplateRule rule;

    public CouponTemplate(
            String name,
            String logo,
            String desc,
            String category,
            Integer productLine,
            Integer count,
            Long userId,
            Integer target,
            TemplateRule rule
    ) {
        this.available = false;
        this.expired = false;
        this.name = name;
        this.logo = logo;
        this.desc = desc;
        this.category = CouponCategory.of(category);
        this.productLine = ProductLine.of(productLine);
        this.count = count;
        this.userId = userId;
        this.key = productLine.toString() + category + new SimpleDateFormat("yyyyMMdd").format(new Date());
        this.target = DistributeTarget.of(target);
        this.rule = rule;
    }

}
