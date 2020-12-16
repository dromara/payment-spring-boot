
package cn.felord.payment.wechat.v3.model.payscore;


import lombok.Data;

import java.util.List;

/**
 * 微信支付分支付成功回调，收款信息，非0元完结后返回
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class PaymentCollection {

    /**
     * The Details.
     */
    private List<Detail> details;
    /**
     * The Paid amount.
     */
    private Long paidAmount;
    /**
     * The Paying amount.
     */
    private Long payingAmount;
    /**
     * The State.
     */
    private String state;
    /**
     * The Total amount.
     */
    private Long totalAmount;

    /**
     * 收款明细列表
     *
     * @author felord.cn
     * @since 1.0.2.RELEASE
     */
    @Data
    public static class Detail {

        /**
         * The Amount.
         */
        private Long amount;
        /**
         * The Paid time.
         */
        private String paidTime;
        /**
         * The Paid type.
         */
        private String paidType;
        /**
         * The Promotion detail.
         */
        private List<PromotionDetail> promotionDetail;
        /**
         * The Seq.
         */
        private Long seq;
        /**
         * The Transaction id.
         */
        private String transactionId;

    }
}
