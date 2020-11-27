
package com.enongm.dianji.payment.wechat.v3.model;

import lombok.Data;

/**
 * 微信代金券核销通知参数
 */
@Data
public class CouponConsumeData {


    private String availableBeginTime;
    private String availableEndTime;
    private ConsumeInformation consumeInformation;
    private String couponId;
    private String couponName;
    private String couponType;
    private String createTime;
    private String description;
    private DiscountTo discountTo;
    private boolean noCash;
    private NormalCouponInformation normalCouponInformation;
    private boolean singleitem;
    private SingleitemDiscountOff singleitemDiscountOff;
    private String status;
    private String stockCreatorMchid;
    private String stockId;


}
