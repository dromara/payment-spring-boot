package com.enongm.dianji.payment.alipay.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author Dax
 * @since 14:13
 */
@Data
@ConfigurationProperties("ali.pay")
public class AliPayProperties {
    /**
     * alipay api version 1.0
     */
    @NestedConfigurationProperty
    private V1 v1;


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
         * your app private key
         */
        private String  appPrivateKey;
        /**
         * sign type
         */
        private String signType = "md5";
        /**
         * alipay public cert path
         */
        private String alipayPublicCertPath;
        /**
         * alipay root cert path
         */
        private String alipayRootCertPath;

    }


}
