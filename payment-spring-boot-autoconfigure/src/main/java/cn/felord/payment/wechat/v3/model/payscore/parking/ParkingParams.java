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
 * 创建停车入场API参数
 *
 * @author felord.cn
 * @since 1.0.13.RELEASE
 */
@Data
public class ParkingParams {
    /**
     * 商户入场id，必传
     * <p>
     * 商户侧入场标识id，在同一个商户号下唯一
     */
    private String outParkingNo;
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
     * 回调通知url，必传
     * <p>
     * 接受入场状态变更回调通知的url，注意回调url只接受https
     */
    private String notifyUrl;
    /**
     * 入场时间，必传
     * <p>
     * 格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE
     */
    private OffsetDateTime startTime;
    /**
     * 停车场名称，必传
     */
    private String parkingName;
    /**
     * 免费时长，必传
     * <p>
     * 单位：秒
     */
    private Integer freeDuration;

}
