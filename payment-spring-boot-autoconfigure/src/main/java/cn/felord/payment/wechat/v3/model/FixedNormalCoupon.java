
package cn.felord.payment.wechat.v3.model;

import lombok.Data;


/**
 * 固定面额满减券使用规则,	stock_type为NORMAL时必填
 *
 * 满transactionMinimum 减少 couponAmount
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class FixedNormalCoupon {

    /**
     * 面额，单位分
     */
    private Long couponAmount;
    /**
     * 门槛   满M元可用
     */
    private Long transactionMinimum;

}
