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
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 批次使用规则
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class StockUseRule {

    /**
     * 总消耗金额，单位：分。
     * max_amount需要等于coupon_amount（面额） * max_coupons（发放总上限）
     */
    private Long maxAmount;
    /**
     * 单天最高消耗金额，单位：分
     */
    private Long maxAmountByDay;
    /**
     * 最大发券数
     */
    private Long maxCoupons;
    /**
     * 单个用户可领个数，每个用户最多60张券
     */
    private Long maxCouponsPerUser;
    /**
     * 是否开启自然人限制
     */
    private Boolean naturalPersonLimit;
    /**
     * api发券防刷
     */
    private Boolean preventApiAbuse;
}
