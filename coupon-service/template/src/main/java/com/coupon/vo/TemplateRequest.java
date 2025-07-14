package com.coupon.vo;

import com.coupon.constant.CouponCategory;
import com.coupon.constant.DistributeTarget;
import com.coupon.constant.ProductLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRequest {

    private String name;

    private String logo;

    private String desc;

    private String category;

    private Integer productLine;

    private Integer count;

    private Long userId;

    private Integer target;

    private TemplateRule templateRule;

    public boolean validate() {
        boolean stringValid = StringUtils.isNotEmpty(name) &&
                StringUtils.isNotEmpty(logo) &&
                StringUtils.isNotEmpty(desc);

        boolean enumValid = CouponCategory.of(category) != null &&
                ProductLine.of(productLine) != null &&
                DistributeTarget.of(target) != null;

        boolean numValid = count > 0 && userId > 0;

        return stringValid && enumValid && numValid && templateRule.validate();
    }

}
