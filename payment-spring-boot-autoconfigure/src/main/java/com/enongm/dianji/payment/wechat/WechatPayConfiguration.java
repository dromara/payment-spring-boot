package com.enongm.dianji.payment.wechat;


import com.enongm.dianji.payment.wechat.oauth2.OAuth2AuthorizationRequestRedirectProvider;
import com.enongm.dianji.payment.wechat.v3.*;
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
    public WechatPayV3Client wechatPayService(SignatureProvider signatureProvider) {
        return new WechatPayV3Client(signatureProvider);
    }

    /**
     * Wechat pay v3 api.
     *
     * @param wechatPayV3Client the wechat pay v 3 client
     * @param wechatMetaBean    the wechat meta bean
     * @return the wechat pay v 3 api
     */
    @Bean
    public WechatPayApi wechatPayApi(WechatPayV3Client wechatPayV3Client, WechatMetaBean wechatMetaBean) {
        return new WechatPayApi(wechatPayV3Client,wechatMetaBean);
    }

    /**
     * 公众号授权工具用于获取用户openId，需要配置{@link WechatPayProperties.Mp}.
     *
     * @param wechatPayProperties the wechat pay properties
     * @return the o auth 2 authorization request redirect provider
     */
    @Bean
    @ConditionalOnProperty(prefix = "wechat.pay", name = "v3.mp.app-id")
    public OAuth2AuthorizationRequestRedirectProvider oAuth2Provider(WechatPayProperties wechatPayProperties){
        WechatPayProperties.Mp mp = wechatPayProperties.getV3().getMp();
        return new OAuth2AuthorizationRequestRedirectProvider(mp.getAppId(), mp.getAppSecret());
    }

}
