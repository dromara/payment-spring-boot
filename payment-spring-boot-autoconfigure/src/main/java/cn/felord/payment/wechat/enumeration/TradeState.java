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
     */
    SUCCESS,
    /**
     * 转入退款
     */
    REFUND,
    /**
     * 未支付
     */
    NOTPAY,
    /**
     * 已关闭
     */
    CLOSED,
    /**
     * 已撤销（付款码支付）
     */
    REVOKED,
    /**
     * 用户支付中（付款码支付）
     */
    USERPAYING,
    /**
     * 支付失败(其他原因，如银行返回失败)
     */
    PAYERROR,
}
