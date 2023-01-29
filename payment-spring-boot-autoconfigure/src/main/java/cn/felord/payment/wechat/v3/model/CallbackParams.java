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
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 微信支付回调请求参数.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CallbackParams {
    /**
     * 通知Id
     */
    private String id;
    /**
     * 通知创建时间
     */
    private String createTime;
    /**
     * 通知类型
     * @see cn.felord.payment.wechat.v3.WechatPayCallback
     */
    private String eventType;
    /**
     * 通知数据类型
     */
    private String resourceType;
    /**
     * 回调摘要
     */
    private String summary;
    /**
     * 通知数据
     */
    private Resource resource;


    /**
     * 通知数据
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class Resource {
        /**
         * 对开启结果数据进行加密的加密算法，目前只支持AEAD_AES_256_GCM。
         */
        private String algorithm;
        /**
         * Base64编码后的开启/停用结果数据密文。
         */
        private String ciphertext;
        /**
         * 附加数据。
         */
        private String associatedData;
        /**
         * 加密使用的随机串。
         */
        private String nonce;
        /**
         * 原始回调类型。
         */
        private String originalType;
    }

}
