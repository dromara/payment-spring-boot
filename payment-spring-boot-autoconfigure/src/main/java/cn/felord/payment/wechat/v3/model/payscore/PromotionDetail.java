
package cn.felord.payment.wechat.v3.model.payscore;


import lombok.Data;

import java.util.List;

/**
 * 微信支付分支付成功回调，优惠功能
 *
 * 注：针对2020年5月27日10:00:00以后完结的订单生效
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
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
     */
    @Data
    public static class GoodsDetail {

        /**
         * The Discount amount.
         */
        private Long discountAmount;
        /**
         * The Goods id.
         */
        private String goodsId;
        /**
         * The Goods remark.
         */
        private String goodsRemark;
        /**
         * The Quantity.
         */
        private Long quantity;
        /**
         * The Unit price.
         */
        private Long unitPrice;

    }
}
