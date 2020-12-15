package cn.felord.payment.wechat.enumeration;

/**
 * 微信侧返回交易状态
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public enum TradeState {
    /**
     * 支付成功
     *
     * @since 1.0.0.RELEASE
     */
    SUCCESS,
    /**
     * 转入退款
     *
     * @since 1.0.0.RELEASE
     */
    REFUND,
    /**
     * 未支付
     *
     * @since 1.0.0.RELEASE
     */
    NOTPAY,
    /**
     * 已关闭
     *
     * @since 1.0.0.RELEASE
     */
    CLOSED,
    /**
     * 已撤销（付款码支付）
     *
     * @since 1.0.0.RELEASE
     */
    REVOKED,
    /**
     * 用户支付中（付款码支付）
     *
     * @since 1.0.0.RELEASE
     */
    USERPAYING,
    /**
     * 支付失败(其他原因，如银行返回失败)
     *
     * @since 1.0.0.RELEASE
     */
    PAYERROR,
}
