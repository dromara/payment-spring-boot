package cn.felord.payment.wechat.v3;

/**
 * The type Wechat api provider.
 *
 * @author Dax
 * @since 17 :32
 */
public class WechatApiProvider {
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
     */
    public WechatMarketingFavorApi  favorApi(String tenantId){
        return new WechatMarketingFavorApi(this.wechatPayClient,tenantId);
    }

    /**
     * 支付.
     *
     * @param tenantId the tenant id
     * @return the wechat pay api
     */
    public WechatPayApi payApi(String tenantId){
        return new WechatPayApi(wechatPayClient,tenantId);
    }

    /**
     * 回调.
     *
     * @param tenantId the tenant id
     * @return the wechat pay callback
     */
    public WechatPayCallback callback(String tenantId){
        return new WechatPayCallback(wechatPayClient.signatureProvider(),tenantId);
    }

}
