/*
 *
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
 *
 */
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 代金券发放接口请求参数
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class StocksSendParams {
    /**
     * 批次号 必须为代金券（全场券或单品券）批次号，不支持立减与折扣。
     */
    private String stockId;
    /**
     * 用户openid 该openid需要与接口传入中的appid有对应关系。
     */
    private String openid;
    /**
     * 商户单据号
     */
    private String outRequestNo;
    /**
     * 公众账号ID
     */
    private String appid;
    /**
     * 创建批次的商户号
     */
    private String stockCreatorMchid;
    /**
     * 指定面额发券场景，券面额，其他场景不需要填，单位：分。
     * 校验规则：仅在发券时指定面额及门槛的场景才生效，常规发券场景请勿传入该信息。
     */
    private Long couponValue;
    /**
     * 指定面额发券批次门槛，其他场景不需要，单位：分。
     * 校验规则：仅在发券时指定面额及门槛的场景才生效，常规发券场景请勿传入该信息。
     */
    private Long couponMinimum;
}
