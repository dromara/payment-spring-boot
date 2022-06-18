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

package cn.felord.payment.wechat.v3.model.payscore.parking;

import cn.felord.payment.wechat.enumeration.PlateColor;
import lombok.Data;

import java.time.OffsetDateTime;
/**
 * 停车场景信息
 *
 * @author felord.cn
 * @since 1.0.13.RELEASE
 */
@Data
public class ParkingInfo {
    /**
     * 停车入场id，必传
     * <p>
     * 微信支付分停车服务为商户分配的入场id，商户通过入场通知接口获取入场id
     */
    private String parkingId;
    /**
     * 车牌号，必传
     * <p>
     * 仅包括省份+车牌，不包括特殊字符，示例值：粤B888888
     */
    private String plateNumber;
    /**
     * 车牌颜色，必传
     */
    private PlateColor plateColor;
    /**
     * 入场时间，必传
     * <p>
     * 格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE
     */
    private OffsetDateTime startTime;
    /**
     * 出场时间，必传
     * <p>
     * 格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE
     */
    private OffsetDateTime endTime;
    /**
     * 停车场名称，必传
     */
    private String parkingName;
    /**
     * 计费时长，必传
     * <p>
     * 单位：秒
     */
    private Integer chargingDuration;
    /**
     * 停车场设备id，必传
     */
    private String deviceId;
}
