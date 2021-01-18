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

import cn.felord.payment.wechat.enumeration.CouponStatus;
import lombok.Data;

/**
 * 根据过滤条件查询用户券API查询参数
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class UserBusiFavorQueryParams {

    /**
     * 用户标识，用户在appid下的唯一标识。
     */
    private String openid;
    /**
     * 与当前调用接口商户号有绑定关系的appid。支持小程序appid与公众号appid。
     */
    private String appid;
    /**
     * 商户券批次号
     */
    private String stockId;
    /**
     * 券状态
     */
    private CouponStatus couponState;
    /**
     * 创建批次的商户号
     */
    private String creatorMerchant;
    /**
     * 批次归属商户号
     */
    private String belongMerchant;
    /**
     * 批次发放商户号
     */
    private String senderMerchant;
    /**
     * 分页页码
     */
    private String offset;
    /**
     * 分页大小
     */
    private String limit;
}
