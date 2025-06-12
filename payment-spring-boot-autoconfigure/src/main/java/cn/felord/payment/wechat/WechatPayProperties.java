/*
 *  Copyright 2019-2022 felord.cn
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *  Website:
 *       https://felord.cn
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.felord.payment.wechat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Wechat pay properties.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
@ConfigurationProperties("wechat.pay")
public class WechatPayProperties {
    /**
     * wechat pay V3 properties
     */
    private Map<String, V3> v3 = new HashMap<>();

    /**
     * wechat pay v3 properties.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
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
         * wechat pay absolute certificate Path
         */
        private String certAbsolutePath;
        /**
         * your pay server domain
         */
        private String domain;


        private Boolean enableWechatPayPublic=false;

        /**
         * wechat pay public key id
         */
        private String wechatPayPublicKeyId;

        /**
         * see <a href="https://pay.weixin.qq.com/doc/v3/merchant/4012154180#4.1-%E8%8E%B7%E5%8F%96%E5%BE%AE%E4%BF%A1%E6%94%AF%E4%BB%98%E5%85%AC%E9%92%A5">
         *     </a>
         * wechat pay public key
         */
        private String wechatPayPublicKeyPath;

        private String wechatPayPublicKeyAbsolutePath;

        /**
         *
         * see <a href="https://pay.weixin.qq.com/doc/v3/merchant/4012154180">
         *     Indicate whether to switch from the platform certificate to the WeChat Pay public key
         *     </a>
         */
        private Boolean switchVerifySignMethod = false;

    }
}
