
/*
 *
 *  Copyright 2019-2020 felord.cn
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

import java.util.List;

/**
 * 核销规则.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CouponUseRule {


    /**
     * The Available items.
     */
    private List<String> availableItems;

    /**
     * The Available merchants.
     */
    private List<String> availableMerchants;

    /**
     * The Combine use.
     */
    private Boolean combineUse;

    /**
     * The Coupon available time.
     */
    private CouponAvailableTime couponAvailableTime;

    /**
     * The Fixed normal coupon.
     */
    private FixedNormalCoupon fixedNormalCoupon;

    /**
     * The Goods tag.
     */
    private List<String> goodsTag;

    /**
     * The Trade type.
     */
    private String tradeType;
}
