package cn.felord.payment.wechat.v3.model.discountcard;

import lombok.Data;

/**
 * 先享卡扣费状态变化通知解密.
 *
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class DiscountCardUserPaidConsumeData {

    /**
     * The Appid.
     */
    private String appid;
    /**
     * The Card id.
     */
    private String cardId;
    /**
     * The Card template id.
     */
    private String cardTemplateId;
    /**
     * The Mchid.
     */
    private String mchid;
    /**
     * The Openid.
     */
    private String openid;
    /**
     * The Out card code.
     */
    private String outCardCode;
    /**
     * The Pay information.
     */
    private PayInformation payInformation;
    /**
     * The State.
     */
    private String state;
    /**
     * The Total amount.
     */
    private Long totalAmount;
    /**
     * The Unfinished reason.
     */
    private String unfinishedReason;

    /**
     * The type Pay information.
     */
    @Data
    public static class PayInformation {

        /**
         * The Pay amount.
         */
        private Long payAmount;
        /**
         * The Pay state.
         */
        private String payState;
        /**
         * The Pay time.
         */
        private String payTime;
        /**
         * The Transaction id.
         */
        private String transactionId;
    }
}
