package com.enongm.dianji.payment.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * The type Wechat pay properties.
 */
@Data
@ConfigurationProperties("wechat.pay")
public class WechatPayProperties {
    /**
     * wechat pay V3 properties
     */
    @NestedConfigurationProperty
    private V3 v3;

    @Data
    public static class V3 {
        /**
         * set as true in develop mode
         */
        private boolean sandboxMode;
        /**
         * set as true in partner mode
         */
        private boolean partnerMode;
        /**
         *  app id for wechat pay is required
         */
        private String appId;
        /**
         * app secret for wechat pay is required
         */
        private String appSecret;
        /**
         * app V3 secret is required by wechat pay V3
         */
        private String appV3Secret;
        /**
         * mchId for wechat pay is required
         */
        private String mchId;
        /**
         * partnerKey for wechat pay is  optional
         */
        private String partnerKey;
        /**
         *  wechat pay certificate Path
         */
        private String certPath;
        /**
         * your pay server domain
         */
        private String domain;
    }

}
