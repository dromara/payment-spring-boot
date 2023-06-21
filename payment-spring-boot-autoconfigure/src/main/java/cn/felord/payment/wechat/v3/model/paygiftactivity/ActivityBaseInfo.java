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

package cn.felord.payment.wechat.v3.model.paygiftactivity;

import cn.felord.payment.wechat.enumeration.CouponBgColor;
import cn.felord.payment.wechat.enumeration.DeliveryPurpose;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * The type Activity base info.
 *
 * @author dax
 * @since 1.0.19.RELEASE
 */
@Data
public class ActivityBaseInfo {
    private String activityName;
    private String activitySecondTitle;
    private String merchantLogoUrl;
    private String backgroundColor;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime endTime;
    private AvailablePeriods availablePeriods;
    private String outRequestNo;
    private DeliveryPurpose deliveryPurpose;
    private String miniProgramsPath;

    /**
     * Sets background color.
     *
     * @param backgroundColor the background color
     */
    void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Sets background color.
     *
     * @param backgroundColor the background color
     */
    public void setBackgroundColor(CouponBgColor backgroundColor) {
        this.backgroundColor = backgroundColor.getValue();
    }

}
