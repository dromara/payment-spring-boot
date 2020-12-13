
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CouponAvailableTime {
    /**
     * The Available time after receive.
     */
    private Long availableTimeAfterReceive;
    /**
     * The Fix available time.
     */
    private FixAvailableTime fixAvailableTime;
    /**
     * The Second day available.
     */
    private Boolean secondDayAvailable;
}
