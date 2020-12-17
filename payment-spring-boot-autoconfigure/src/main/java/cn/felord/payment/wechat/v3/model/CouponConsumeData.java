
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 微信代金券核销通知参数
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CouponConsumeData {

    /**
     * The Available begin time.
     */
    private String availableBeginTime;
    /**
     * The Available end time.
     */
    private String availableEndTime;
    /**
     * The Consume information.
     */
    private ConsumeInformation consumeInformation;
    /**
     * The Coupon id.
     */
    private String couponId;
    /**
     * The Coupon name.
     */
    private String couponName;
    /**
     * The Coupon type.
     */
    private CouponType couponType;
    /**
     * The Create time.
     */
    private String createTime;
    /**
     * The Description.
     */
    private String description;
    /**
     * The Discount to.
     */
    private DiscountTo discountTo;
    /**
     * The No cash.
     */
    private Boolean noCash;
    /**
     * The Normal coupon information.
     */
    private NormalCouponInformation normalCouponInformation;
    /**
     * The Singleitem.
     */
    private Boolean singleitem;
    /**
     * The Singleitem discount off.
     */
    private SingleitemDiscountOff singleitemDiscountOff;
    /**
     * The Status.
     */
    private String status;
    /**
     * The Stock creator mchid.
     */
    private String stockCreatorMchid;
    /**
     * The Stock id.
     */
    private String stockId;

    /**
     * 券类型
     *
     * @since 1.0.2.RELEASE
     */
    public enum CouponType{
        /**
         * 满减券
         */
        NORMAL,
        /**
         * 减至券
         */
        CUT_TO
    }
}
