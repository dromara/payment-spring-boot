package cn.felord.payment.wechat.v3;

/**
 * 微信支付分API.
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
public class WechatPayScoreApi extends AbstractApi{
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatPayScoreApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

}
