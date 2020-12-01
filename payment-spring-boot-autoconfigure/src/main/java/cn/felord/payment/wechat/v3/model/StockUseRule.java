package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 批次使用规则
 *
 * @author Dax
 * @since 15:08
 */
@Data
public class StockUseRule {

    /**
     * 总消耗金额，单位：分。
     * max_amount需要等于coupon_amount（面额） * max_coupons（发放总上限）
     */
    private Long maxAmount;
    /**
     * 单天最高消耗金额，单位：分
     */
    private Long maxAmountByDay;
    /**
     * 最大发券数
     */
    private Long maxCoupons;
    /**
     * 单个用户可领个数，每个用户最多60张券
     */
    private Long maxCouponsPerUser;
    /**
     * 是否开启自然人限制
     */
    private Boolean naturalPersonLimit;
    /**
     * api发券防刷
     */
    private Boolean preventApiAbuse;
}
