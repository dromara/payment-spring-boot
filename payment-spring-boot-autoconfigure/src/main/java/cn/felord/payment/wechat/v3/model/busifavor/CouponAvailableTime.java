/*
 *  Copyright 2019-2022 felord.cn
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

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 商家券核销规则-券可核销时间
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class CouponAvailableTime {

    /**
     * 领取后N天开始生效
     * <p>
     * 日期区间内，用户领券后需等待x天开始生效。例如领券后当天开始生效则无需填写，领券后第2天开始生效填1，以此类推……
     * <p>
     * 用户在有效期开始前领取商家券，则从有效期第1天开始计算天数，用户在有效期内领取商家券，则从领取当天开始计算天数。无论用户何时领取商家券，商家券在活动有效期结束后均不可用。
     * <p>
     * 需配合{@link #availableDayAfterReceive} 一同填写，不可单独填写。
     */
    private Integer waitDaysAfterReceive;
    /**
     * 生效后N天内有效
     * <p>
     * 日期区间内，券生效后x天内有效。例如生效当天内有效填1，生效后2天内有效填2，以此类推……
     * <p>
     * 注意，用户在有效期开始前领取商家券，则从有效期第1天开始计算天数，用户在有效期内领取商家券，则从领取当天开始计算天数，无论用户何时领取商家券，商家券在活动有效期结束后均不可用。
     * <p>
     * 可配合{@link  #waitDaysAfterReceive}一同填写，也可单独填写。单独填写时，有效期内领券后立即生效，生效后x天内有效。
     */
    private Integer availableDayAfterReceive;
    /**
     * 批次开始时间 rfc 3339   yyyy-MM-ddTHH:mm:ss+TIMEZONE
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime availableBeginTime;
    /**
     * 批次结束时间 rfc 3339  yyyy-MM-ddTHH:mm:ss+TIMEZONE
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime availableEndTime;
    /**
     * 固定周期有效时间段
     */
    private AvailableWeek availableWeek;
    /**
     * 无规律的有效时间段
     */
    private List<IrregularyAvaliableTimeItem> irregularyAvaliableTime;


}