
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

import java.util.List;

/**
 * The type Promotion detail.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class PromotionDetail {

    /**
     * The Amount.
     */
    private Long amount;
    /**
     * The Coupon id.
     */
    private String couponId;
    /**
     * The Currency.
     */
    private String currency;
    /**
     * The Goods detail.
     */
    private List<GoodsDetail> goodsDetail;
    /**
     * The Merchant contribute.
     */
    private Long merchantContribute;
    /**
     * The Name.
     */
    private String name;
    /**
     * The Other contribute.
     */
    private Long otherContribute;
    /**
     * The Scope.
     */
    private String scope;
    /**
     * The Stock id.
     */
    private String stockId;
    /**
     * The Type.
     */
    private String type;
    /**
     * The Wechatpay contribute.
     */
    private Long wechatpayContribute;

    /**
     * The type Goods detail.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class GoodsDetail {

        /**
         * The Goods id.
         */
        private String goodsId;
        /**
         * The Quantity.
         */
        private Long quantity;
        /**
         * The Unit price.
         */
        private Long unitPrice;
        /**
         * The Discount amount.
         */
        private Long discountAmount;
        /**
         * The Goods remark.
         */
        private String goodsRemark;

    }
}
