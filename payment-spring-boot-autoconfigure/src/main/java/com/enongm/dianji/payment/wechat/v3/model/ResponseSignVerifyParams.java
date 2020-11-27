package com.enongm.dianji.payment.wechat.v3.model;

import lombok.Data;

/**
 * 微信的响应签名校验参数
 *
 * @author Dax
 * @see com.enongm.dianji.payment.wechat.v3.SignatureProvider#responseSignVerify(String, String, String, String, String)
 * @since 16:32
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
