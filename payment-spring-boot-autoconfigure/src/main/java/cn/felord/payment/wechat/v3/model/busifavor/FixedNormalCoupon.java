package cn.felord.payment.wechat.v3.model.busifavor;

import lombok.Data;

/**
 * 商家券核销规则-固定面额满减券使用规则
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class FixedNormalCoupon {

    /**
     * 优惠金额，单位：分。
     * <p>
     * 特殊规则：取值范围 1 ≤ transactionMinimum ≤ 10000000
     */
    private Integer discountAmount;
    /**
     * 消费门槛，单位：分。
     * <p>
     * 特殊规则：取值范围 1 ≤ transactionMinimum ≤ 10000000
     */
    private Integer transactionMinimum;
}