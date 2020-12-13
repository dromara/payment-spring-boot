
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

import java.util.List;

/**
 * 核销规则.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CouponUseRule {


    private List<String> availableItems;

    private List<String> availableMerchants;

    private Boolean combineUse;

    private CouponAvailableTime couponAvailableTime;

    private FixedNormalCoupon fixedNormalCoupon;

    private List<String> goodsTag;

    private String tradeType;
}
