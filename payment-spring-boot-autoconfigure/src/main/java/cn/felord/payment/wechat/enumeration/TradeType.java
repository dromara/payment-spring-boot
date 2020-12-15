package cn.felord.payment.wechat.enumeration;

/**
 * 微信侧返回交易类型
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public enum TradeType {
    /**
     * 公众号支付
     *
     * @since 1.0.0.RELEASE
     */
    JSAPI,
    /**
     * 扫码支付
     *
     * @since 1.0.0.RELEASE
     */
    NATIVE,
    /**
     * APP支付
     *
     * @since 1.0.0.RELEASE
     */
    APP,
    /**
     * 付款码支付
     *
     * @since 1.0.0.RELEASE
     */
    MICROPAY,
    /**
     * H5支付
     *
     * @since 1.0.0.RELEASE
     */
    MWEB,
    /**
     * 刷脸支付
     *
     * @since 1.0.0.RELEASE
     */
    FACEPAY,
}
