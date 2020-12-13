
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 普通满减券面额、门槛信息
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class NormalCouponInformation {

    private Long couponAmount;
    private Long transactionMinimum;

}
