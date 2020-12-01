
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

import java.util.List;

@Data
public class FixAvailableTime {

    private List<Long> availableWeekDay;
    private Long beginTime;
    private Long endTime;

}
