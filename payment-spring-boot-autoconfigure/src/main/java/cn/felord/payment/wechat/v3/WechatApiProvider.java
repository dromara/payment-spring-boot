package cn.felord.payment.wechat.v3;

/**
 * 微信支付工具.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class WechatApiProvider {
    /**
     * The Wechat pay client.
     */
    private final WechatPayClient wechatPayClient;

    /**
     * Instantiates a new Wechat api provider.
     *
     * @param wechatPayClient the wechat pay client
     */
    public WechatApiProvider(WechatPayClient wechatPayClient) {
        this.wechatPayClient = wechatPayClient;
    }

    /**
     * 代金券.
     *
     * @param tenantId the tenant id
     * @return the wechat marketing favor api
     * @since 1.0.0.RELEASE
     */
    public WechatMarketingFavorApi favorApi(String tenantId) {
        return new WechatMarketingFavorApi(this.wechatPayClient, tenantId);
    }

    /**
     * 普通支付-直连模式.
     *
     * @param tenantId the tenant id
     * @return the wechat pay api
     * @since 1.0.0.RELEASE
     */
    public WechatDirectPayApi directPayApi(String tenantId) {
        return new WechatDirectPayApi(wechatPayClient, tenantId);
    }

    /**
     * 合单支付.
     *
     * @param tenantId the tenant id
     * @return the wechat combine pay api
     * @since 1.0.1.RELEASE
     */
    public WechatCombinePayApi combinePayApi(String tenantId) {
        return new WechatCombinePayApi(wechatPayClient, tenantId);
    }

    /**
     * 微信支付分.
     *
     * @param tenantId the tenant id
     * @return the wechat pay score api
     * @since 1.0.2.RELEASE
     */
    public WechatPayScoreApi payScoreApi(String tenantId) {
        return new WechatPayScoreApi(wechatPayClient, tenantId);
    }

    /**
     * 回调.
     *
     * 需要处理白名单、幂等性问题。
     *
     * @param tenantId the tenant id
     * @return the wechat pay callback
     * @since 1.0.0.RELEASE
     */
    public WechatPayCallback callback(String tenantId) {
        return new WechatPayCallback(wechatPayClient.signatureProvider(), tenantId);
    }

}
