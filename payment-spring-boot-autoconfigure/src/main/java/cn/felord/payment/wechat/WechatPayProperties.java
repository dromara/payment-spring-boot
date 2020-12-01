package cn.felord.payment.wechat;

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

    /**
     * wechat pay v3 properties.
     */
    @Data
    public static class V3 {
        /**
         * app id for wechat pay is required
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
         * wechat pay certificate Path
         */
        private String certPath;
        /**
         * your pay server domain
         */
        private String domain;
        /**
         * wechat mp binding with mch
         */
        private Mp mp;

    }


    /**
     * wechat  mp for send coupons and notification.
     */
    @Data
    public static class Mp {
        /**
         * app id for wechat pay is required
         */
        private String appId;
        /**
         * app secret for wechat pay is required
         */
        private String appSecret;
    }

}
