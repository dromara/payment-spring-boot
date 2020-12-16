package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

import java.util.List;

/**
 * 创单结单合并API请求参数
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class DirectCompleteServiceOrderParams {
    /**
     * The Appid.
     */
    private String appid;
    /**
     * The Attach.
     */
    private String attach;
    /**
     * The Goods tag.
     */
    private String goodsTag;
    /**
     * The Location.
     */
    private Location location;
    /**
     * The Notify url.
     */
    private String notifyUrl;
    /**
     * The Openid.
     */
    private String openid;
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
     * The Profit sharing.
     */
    private Boolean profitSharing;
    /**
     * The Service id.
     */
    private String serviceId;
    /**
     * The Service introduction.
     */
    private String serviceIntroduction;
    /**
     * The Time range.
     */
    private TimeRange timeRange;
    /**
     * The Total amount.
     */
    private Long totalAmount;
}
