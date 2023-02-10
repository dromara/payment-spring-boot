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

import cn.felord.payment.wechat.v3.SignatureProvider;
import lombok.Data;

/**
 * 微信的响应签名校验参数
 *
 * @author felord.cn
 * @see SignatureProvider#responseSignVerify(ResponseSignVerifyParams) SignatureProvider#responseSignVerify(ResponseSignVerifyParams)SignatureProvider#responseSignVerify(ResponseSignVerifyParams)
 * @since 1.0.0.RELEASE
 */
@Data
public class ResponseSignVerifyParams {
    /**
     * response.headers['Wechatpay-Serial']    当前使用的微信平台证书序列号
     */
    private String wechatpaySerial;
    /**
     * response.headers['Wechatpay-Signature']   微信平台签名
     */
    private String wechatpaySignature;
    /**
     * response.headers['Wechatpay-Timestamp']  微信服务器的时间戳
     */
    private String wechatpayTimestamp;
    /**
     * response.headers['Wechatpay-Nonce']   微信服务器提供的随机串
     */
    private String wechatpayNonce;
    /**
     * response.body 微信服务器的响应体
     */
    private String body;
}
