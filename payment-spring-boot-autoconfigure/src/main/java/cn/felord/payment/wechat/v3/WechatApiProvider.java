package cn.felord.payment.wechat.v3;

/**
 * @author Dax
 * @since 17:32
 */
public class WechatApiProvider {
    private final WechatPayClient wechatPayClient;

    public WechatApiProvider(WechatPayClient wechatPayClient) {
        this.wechatPayClient = wechatPayClient;
    }

    public WechatMarketingFavorApi  favorApi(String tenantId){
        return new WechatMarketingFavorApi(this.wechatPayClient,tenantId);
    }

    public WechatPayApi payApi(String tenantId){
        return new WechatPayApi(wechatPayClient,tenantId);
    }

}
