
package com.enongm.dianji.payment.wechat.v3.model;

import lombok.Data;

@Data
public class CouponAvailableTime {
    private Long availableTimeAfterReceive;
    private FixAvailableTime fixAvailableTime;
    private Boolean secondDayAvailable;
}
