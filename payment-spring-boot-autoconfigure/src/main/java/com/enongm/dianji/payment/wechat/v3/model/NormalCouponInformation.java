
package com.enongm.dianji.payment.wechat.v3.model;

import lombok.Data;

/**
 * 普通满减券面额、门槛信息
 */
@Data
public class NormalCouponInformation {

    private Long couponAmount;
    private Long transactionMinimum;

}
