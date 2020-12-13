
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

import java.util.List;

/**
 * The type Fix available time.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class FixAvailableTime {

    /**
     * The Available week day.
     */
    private List<Long> availableWeekDay;
    /**
     * The Begin time.
     */
    private Long beginTime;
    /**
     * The End time.
     */
    private Long endTime;

}
