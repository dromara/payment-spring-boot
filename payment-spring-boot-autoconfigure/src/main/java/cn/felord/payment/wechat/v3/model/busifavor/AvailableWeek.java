/*
 *  Copyright 2019-2021 felord.cn
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *  Website:
 *       https://felord.cn
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.felord.payment.wechat.v3.model.busifavor;

import lombok.Data;

import java.util.List;

/**
 * 固定周期有效时间段
 * <p>
 * 可以设置多个星期下的多个可用时间段，比如每周二10点到18点，用户自定义字段。
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class AvailableWeek {

    /**
     * 当天可用时间段
     * <p>
     * 可以填写多个时间段，最多不超过2个。
     */
    private List<AvailableDayTimeItem> availableDayTime;
    /**
     * 可用星期数
     * <p>
     * 0代表周日，1代表周一，以此类推
     * <p>
     * 当填写{@link #availableDayTime}时，{@code weekDay}必填
     */
    private List<Integer> weekDay;
}