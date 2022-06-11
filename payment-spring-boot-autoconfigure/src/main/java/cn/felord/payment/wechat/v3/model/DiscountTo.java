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
 *  微信代金券核销通知参数-减至优惠限定字段，仅减至优惠场景有返回
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class DiscountTo {

    /**
     * 减至后优惠单价，单位：分。
     */
    private Long cutToPrice;
    /**
     * 可享受优惠的最高价格，单位：分。
     */
    private Long maxPrice;

}
