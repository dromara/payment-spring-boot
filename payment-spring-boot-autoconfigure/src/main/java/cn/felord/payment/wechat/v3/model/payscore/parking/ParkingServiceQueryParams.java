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

package cn.felord.payment.wechat.v3.model.payscore.parking;

import cn.felord.payment.wechat.enumeration.PlateColor;
import lombok.Data;

/**
 * 查询车牌服务开通信息API参数
 *
 * @author felord.cn
 * @since 1.0.13.RELEASE
 */
@Data
public class ParkingServiceQueryParams {
    /**
     * 应用ID，必传
     * <p>
     * appid是商户在微信申请公众号或移动应用成功后分配的账号ID，登录平台为mp.weixin.qq.com或open.weixin.qq.com
     */
    private String appid;
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
     * 用户标识，必传
     * <p>
     * 用户在商户对应{@code appid}下的唯一标识
     */
    private String openid;

}
