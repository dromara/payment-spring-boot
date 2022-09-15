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
 * 创建停车入场API回调结果
 *
 * @author felord.cn
 * @since 1.0.13.RELEASE
 */
@Data
public class ParkingCallback {

    /**
     * 调用接口提交的商户号
     */
    private String spMchid;
    /**
     * 停车入场id
     * <p>
     * 微信支付分停车服务为商户分配的入场id，商户通过入场通知接口获取入场id
     */
    private String parkingId;
    /**
     * 商户侧入场标识id，在同一个商户号下唯一
     */
    private String outParkingNo;
    /**
     * 车牌号
     * <p>
     * 仅包括省份+车牌，不包括特殊字符，示例值：粤B888888
     */
    private String plateNumber;
    /**
     * 车牌颜色
     */
    private PlateColor plateColor;
    /**
     * 入场时间
     * <p>
     * 格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE
     */
    private OffsetDateTime startTime;
    /**
     * 停车场名称
     */
    private String parkingName;
    /**
     * 免费时长
     * <p>
     * 单位：秒
     */
    private Integer freeDuration;
    /**
     * 本次入场车牌的服务状态
     * <ul>
     *     <li>NORMAL：正常状态，可以使用车主服务</li>
     *     <li>BLOCKED：不可用状态，暂时不可以使用车主服务</li>
     * </ul>
     */
    private String parkingState;
    /**
     * 不可用服务状态描述，返回车牌状态为{@code BLOCKED}，会返回该字段，描述具体的原因
     * <p>
     * 不可用状态：
     * <ul>
     *     <li>PAUSE：已暂停车主服务</li>
     *     <li>OVERDUE：已授权签约但欠费，不能提供服务，商户提示用户进行还款</li>
     *     <li>REMOVE：用户移除车牌导致车牌不可用。请跳转到授权/开通接口</li>
     * </ul>
     */
    private String blockedStateDescription;
    /**
     * 状态变更时间
     * <p>
     * 格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE
     */
    private OffsetDateTime stateUpdateTime;

}
