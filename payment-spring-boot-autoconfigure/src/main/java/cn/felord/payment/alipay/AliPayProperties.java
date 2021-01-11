/*
 *
 *  Copyright 2019-2020 felord.cn
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
 *
 */
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
    public static class V1 {
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
        private String appPrivateKeyPath;
        /**
         * sign type default RSA2
         */
        private String signType = "RSA2";
        /**
         * data format   only json now
         */
        private String format = "json";
        /**
         * charset  default utf-8
         */
        private String charset = "utf-8";
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
