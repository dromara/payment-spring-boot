
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 减至优惠限定字段，仅减至优惠场景有返回
 */
@Data
public class DiscountTo {

    private Long cutToPrice;
    private Long maxPrice;

}
