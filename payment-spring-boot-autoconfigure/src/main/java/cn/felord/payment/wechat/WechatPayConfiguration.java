package cn.felord.payment.wechat;


import cn.felord.payment.wechat.oauth2.OAuth2AuthorizationRequestRedirectProvider;
import cn.felord.payment.wechat.v3.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

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
    WechatMetaContainer wechatMetaContainer(WechatPayProperties wechatPayProperties) {

        Map<String, WechatPayProperties.V3> v3Map = wechatPayProperties.getV3();
        WechatMetaContainer container = new WechatMetaContainer();
        v3Map.keySet().forEach(tenantId -> {
            WechatPayProperties.V3 v3 = v3Map.get(tenantId);
            String certPath = v3.getCertPath();
            String mchId = v3.getMchId();
            WechatMetaBean wechatMetaBean = new KeyPairFactory().createPKCS12(certPath, CERT_ALIAS, mchId);
            wechatMetaBean.setV3(v3);
            wechatMetaBean.setTenantId(tenantId);
            container.addWechatMeta(tenantId, wechatMetaBean);
            container.addTenant(tenantId);
        });
        return container;
    }

    /**
     * 微信支付V3签名工具.
     *
     * @param wechatMetaContainer the wechat meta container
     * @return the signature provider
     */
    @Bean
    SignatureProvider signatureProvider(WechatMetaContainer wechatMetaContainer) {
        return new SignatureProvider(wechatMetaContainer);
    }


    /**
     * 微信支付V3 客户端.
     *
     * @param signatureProvider the signature provider
     * @return the wechat pay service
     */
    @Bean
    public WechatPayClient wechatPayClient(SignatureProvider signatureProvider) {
        return new WechatPayClient(signatureProvider);
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
    public OAuth2AuthorizationRequestRedirectProvider oAuth2Provider(WechatPayProperties wechatPayProperties) {
        Map<String, WechatPayProperties.V3> v3Map = wechatPayProperties.getV3();
        return new OAuth2AuthorizationRequestRedirectProvider(v3Map);
    }

}
