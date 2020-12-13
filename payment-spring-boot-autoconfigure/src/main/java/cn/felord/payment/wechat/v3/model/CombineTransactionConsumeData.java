
package cn.felord.payment.wechat.v3.model;

import cn.felord.payment.wechat.v3.model.combine.CombinePayerInfo;
import lombok.Data;

import java.util.List;


/**
 * 合单支付回调解密数据.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CombineTransactionConsumeData {

    /**
     * 合单商户appid.
     */
    private String combineAppid;

    /**
     * 合单商户号.
     */
    private String combineMchid;

    /**
     * 合单商户订单号.
     */
    private String combineOutTradeNo;

    /**
     * 支付者.
     */
    private CombinePayerInfo combinePayerInfo;

    /**
     * 场景信息，合单支付回调只返回device_id
     */
    private SceneInfo sceneInfo;

    /**
     * 合单支付回调子订单.
     */
    private List<SubOrderCallback> subOrders;

    /**
     * 合单支付回调子订单.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class SubOrderCallback {


        /**
         * The Amount.
         */
        private CombineAmount amount;

        /**
         * The Attach.
         */
        private String attach;

        /**
         * The Bank type.
         */
        private String bankType;

        /**
         * The Mchid.
         */
        private String mchid;

        /**
         * The Out trade no.
         */
        private String outTradeNo;

        /**
         * The Sub mchid.
         */
        private String subMchid;

        /**
         * The Success time.
         */
        private String successTime;

        /**
         * The Trade state.
         */
        private String tradeState;

        /**
         * The Trade type.
         */
        private String tradeType;

        /**
         * The Transaction id.
         */
        private String transactionId;

    }

    /**
     * 订单金额信息.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class CombineAmount{
        /**
         * 标价金额，单位为分.
         */
        private Long totalAmount;
        /**
         * 标价币种.
         */
        private String currency;
        /**
         * 现金支付金额.
         */
        private Long payerAmount;
        /**
         * 现金支付币种.
         */
        private String payerCurrency;
    }

}
