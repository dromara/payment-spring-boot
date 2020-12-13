package cn.felord.payment.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * The type Ali pay properties.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
@ConfigurationProperties("ali.pay")
public class AliPayProperties {
    /**
     * alipay api version 1.0
     */
    @NestedConfigurationProperty
    private V1 v1;


    /**
     * The type V 1.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class V1{
        /**
         * alipay server
         */
        private String serverUrl = "https://openapi.alipay.com/gateway.do";
        /**
         * your app ID
         */
        private String appId;
        /**
         * your app private key, which must be in a single line
         */
        private String  appPrivateKeyPath;
        /**
         * sign type default RSA2
         */
        private String signType = "RSA2";
        /**
         *  data format   only json now
         */
        private String format ="json";
        /**
         * charset  default utf-8
         */
        private String charset ="utf-8";
        /**
         * alipay public cert path
         */
        private String alipayPublicCertPath;
        /**
         * alipay root cert path
         */
        private String alipayRootCertPath;
        /**
         * appCertPublicKey
         */
        private String appCertPublicKeyPath;

    }


}
