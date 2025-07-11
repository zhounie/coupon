package com.coupon.entity;

import com.coupon.constant.CouponCategory;
import com.coupon.constant.ProductLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon_template")
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
    private CouponCategory category;

    @Column(name="product_line", nullable = false)
    private ProductLine productLine;

    @Column(name="coupon_count", nullable = false)
    private Integer count;

    @CreatedDate
    @Column(name="create_time", nullable = false)
    private Date createTime;

}
