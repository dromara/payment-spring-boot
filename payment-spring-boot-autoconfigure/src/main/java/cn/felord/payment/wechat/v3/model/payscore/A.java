
package cn.felord.payment.wechat.v3.model.payscore;


import lombok.Data;

import java.util.List;

@Data
public class A {

    private String appid;
    private String attach;
    private Location location;
    private Boolean needUserConfirm;
    private String notifyUrl;
    private String openid;
    private String outOrderNo;
    private List<PostDiscount> postDiscounts;
    private List<PostPayment> postPayments;
    private RiskFund riskFund;
    private String serviceId;
    private String serviceIntroduction;
    private TimeRange timeRange;
}
