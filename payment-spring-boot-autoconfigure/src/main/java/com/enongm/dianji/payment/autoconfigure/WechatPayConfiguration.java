package com.enongm.dianji.payment.autoconfigure;


import com.enongm.dianji.payment.wechat.KeyPairFactory;
import com.enongm.dianji.payment.wechat.WechatPayV3Service;
import com.enongm.dianji.payment.wechat.v2.WechatPayV2Service;
import com.enongm.dianji.payment.wechat.v3.SignatureProvider;
import com.enongm.dianji.payment.wechat.v3.model.WechatMetaBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Wechat pay configuration.
 */
@Configuration
@ConditionalOnProperty(prefix = "wechat.pay",havingValue = "v3")
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
     * 微信支付V2 只实现V3支付没有的支付业务。
     *
     * @param wechatPayProperties the wechat pay properties
     * @return the wechat pay v 2 service
     */
    @Bean
    public WechatPayV2Service wechatPayV2Service(WechatPayProperties wechatPayProperties) {
        return new WechatPayV2Service(wechatPayProperties);
    }

    /**
     * 微信支付V3 全量支持.
     *
     * @param signatureProvider the signature provider
     * @return the wechat pay service
     */
    @Bean
    public WechatPayV3Service wechatPayService(SignatureProvider signatureProvider) {
        return new WechatPayV3Service(signatureProvider);
    }
}
