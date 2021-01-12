/*
 *
 *  Copyright 2019-2020 felord.cn
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
 *
 */
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 代金券生效时间
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CouponAvailableTime {
    /**
     * 领取后有效时间，【单位：分钟】
     * <p>
     * 领取后，券的结束时间为领取N天后，如设置领取后7天有效，那么7月1日领券，在7月7日23:59:59失效（在可用时间内计算失效时间，若券还未到领取后N天，但是已经到了可用结束时间，那么也会过期）
     */
    private Long availableTimeAfterReceive;
    /**
     * 固定时间段可用
     */
    private FixAvailableTime fixAvailableTime;
    /**
     * 领取后N天有效
     * <p>
     * 领取后，券的开始时间为领券后第二天，如7月1日领券，那么在7月2日00:00:00开始。
     * 当设置领取后N天有效时，不可设置固定时间段可用。枚举值：
     *
     * <ul>
     *     <li>true：是</li>
     *     <li>false：否</li>
     * </ul>
     *
     */
    private Boolean secondDayAvailable;
}
