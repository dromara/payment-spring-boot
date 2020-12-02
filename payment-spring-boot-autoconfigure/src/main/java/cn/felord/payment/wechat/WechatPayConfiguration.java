package cn.felord.payment.wechat;


import cn.felord.payment.wechat.oauth2.OAuth2AuthorizationRequestRedirectProvider;
import cn.felord.payment.wechat.v3.*;
import cn.felord.payment.wechat.v3.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Wechat pay configuration.
 */
@Configuration
@ConditionalOnProperty(prefix = "wechat.pay", name = "v3.app-id")
@EnableConfigurationProperties(WechatPayProperties.class)
public class WechatPayConfiguration {
    private static final String CERT_ALIAS = "Tenpay Certificate";

    /**
     * 微信支付公私钥 以及序列号等元数据.
     *
     * @param wechatPayProperties the wechat pay properties
     * @return the wechat cert bean
     */
    @Bean
    WechatMetaBean wechatMetaBean(WechatPayProperties wechatPayProperties) {

        String certPath = wechatPayProperties.getV3().getCertPath();
        String mchId = wechatPayProperties.getV3().getMchId();
        WechatMetaBean wechatMetaBean = new KeyPairFactory().createPKCS12(certPath, CERT_ALIAS, mchId);
        wechatMetaBean.setWechatPayProperties(wechatPayProperties);
        return wechatMetaBean;
    }

    /**
     * 微信支付V3签名工具.
     *
     * @param wechatMetaBean the wechat meta bean
     * @return the signature provider
     */
    @Bean
    SignatureProvider signatureProvider(WechatMetaBean wechatMetaBean) {
        return new SignatureProvider(wechatMetaBean);
    }


    /**
     * 微信支付V3 客户端.
     *
     * @param signatureProvider the signature provider
     * @return the wechat pay service
     */
    @Bean
    public WechatPayClient wechatPayService(SignatureProvider signatureProvider) {
        return new WechatPayClient(signatureProvider);
    }

    /**
     * 微信支付API.
     *
     * @param wechatPayClient the wechat pay v 3 client
     * @param wechatMetaBean  the wechat meta bean
     * @return the wechat pay v 3 api
     */
    @Bean
    public WechatPayApi wechatPayApi(WechatPayClient wechatPayClient, WechatMetaBean wechatMetaBean) {
        return new WechatPayApi(wechatPayClient, wechatMetaBean);
    }

    /**
     * 微信营销API.
     *
     * @param wechatPayClient the wechat pay client
     * @param wechatMetaBean  the wechat meta bean
     * @return the wechat marketing api
     */
    @Bean
    public WechatMarketingApi wechatMarketingApi(WechatPayClient wechatPayClient, WechatMetaBean wechatMetaBean) {
        return new WechatMarketingApi(wechatPayClient, wechatMetaBean);
    }

    /**
     * 微信支付回调工具.
     *
     * @param signatureProvider the signature provider
     * @return the wechat pay callback
     */
    @Bean
    public WechatPayCallback wechatPayCallback(SignatureProvider signatureProvider) {
        return new WechatPayCallback(signatureProvider);
    }


    /**
     * 公众号授权工具用于获取用户openId，需要配置{@link WechatPayProperties.Mp}.
     *
     * @param wechatPayProperties the wechat pay properties
     * @return the o auth 2 authorization request redirect provider
     */
    @Bean
    @ConditionalOnProperty(prefix = "wechat.pay", name = "v3.mp.app-id")
    public OAuth2AuthorizationRequestRedirectProvider oAuth2Provider(WechatPayProperties wechatPayProperties) {
        WechatPayProperties.Mp mp = wechatPayProperties.getV3().getMp();
        return new OAuth2AuthorizationRequestRedirectProvider(mp.getAppId(), mp.getAppSecret());
    }

}
