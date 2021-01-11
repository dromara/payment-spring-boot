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
     * The Id.
     */
    private String id;
    /**
     * The Create time.
     */
    private String createTime;
    /**
     * The Event type.
     */
    private String eventType;
    /**
     * The Resource type.
     */
    private String resourceType;
    /**
     * The Summary.
     */
    private String summary;
    /**
     * The Resource.
     */
    private Resource resource;


    /**
     * The type Resource.
     */
    @Data
    public static class Resource {
        /**
         * The Algorithm.
         */
        private String algorithm;
        /**
         * The Ciphertext.
         */
        private String ciphertext;
        /**
         * The Associated data.
         */
        private String associatedData;
        /**
         * The Nonce.
         */
        private String nonce;
        /**
         * The Original type.
         */
        private String originalType;
    }

}
