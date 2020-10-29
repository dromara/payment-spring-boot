package com.enongm.dianji.payment.wechat.v2;

import lombok.Data;

/**
 * @author Dax
 * @since 15:28
 */
@Data
public class WechatResponseBody {

    private String returnCode;
    private String returnMsg;
    private String mchAppid;
    private String mchid;
    private String deviceInfo;
    private String nonceStr;
    private String resultCode;
    private String errCode;
    private String errCodeDes;
    private String partnerTradeNo;
    private String paymentNo;
    private String paymentTime;
}
