package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

import java.util.List;

/**
 * 微信支付分用户确认订单回调解密.
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class PayScoreUserConfirmConsumeData {

    /**
     * The Appid.
     */
    private String appid;
    /**
     * The Attach.
     */
    private String attach;
    /**
     * The Location.
     */
    private Location location;
    /**
     * The Mchid.
     */
    private String mchid;
    /**
     * The Openid.
     */
    private String openid;
    /**
     * The Order id.
     */
    private String orderId;
    /**
     * The Out order no.
     */
    private String outOrderNo;
    /**
     * The Post discounts.
     */
    private List<PostDiscount> postDiscounts;
    /**
     * The Post payments.
     */
    private List<PostPayment> postPayments;
    /**
     * The Risk fund.
     */
    private RiskFund riskFund;
    /**
     * The Service id.
     */
    private String serviceId;
    /**
     * The Service introduction.
     */
    private String serviceIntroduction;
    /**
     * The State.
     */
    private String state;
    /**
     * The State description.
     */
    private String stateDescription;
    /**
     * The Time range.
     */
    private TimeRange timeRange;
    /**
     * The Total amount.
     */
    private String totalAmount;
}
