package com.enongm.dianji.payment.wechat.v2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Dax
 * @since 15:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayToWechatModel extends BaseModel {
    private String deviceInfo;
    private String partnerTradeNo;
    private String openid;
    private String checkName = "NO_CHECK";
    private String reUserName;
    private String amount;
    private String desc;
    private String spbillCreateIp;

}
