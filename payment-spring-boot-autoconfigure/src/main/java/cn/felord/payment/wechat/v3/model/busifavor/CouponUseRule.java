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

import cn.felord.payment.wechat.enumeration.BusiFavorUseMethod;
import cn.felord.payment.wechat.enumeration.StockType;
import lombok.Data;

/**
 * 商家券核销规则
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class CouponUseRule {

    /**
     * 核销方式
     */
    private BusiFavorUseMethod useMethod;
    /**
     * 换购券使用规则
     *
     * @see StockType#EXCHANGE
     */
    private ExchangeCoupon exchangeCoupon;
    /**
     * 券可核销时间
     */
    private CouponAvailableTime couponAvailableTime;
    /**
     * 核销小程序appid
     *
     * @see BusiFavorUseMethod#MINI_PROGRAMS
     */
    private String miniProgramsAppid;
    /**
     * 核销小程序path
     *
     * @see #miniProgramsAppid
     */
    private String miniProgramsPath;
    /**
     * 固定面额满减券使用规则
     *
     * @see StockType#NORMAL
     */
    private FixedNormalCoupon fixedNormalCoupon;
    /**
     * 折扣券使用规则
     *
     * @see StockType#DISCOUNT
     */
    private DiscountCoupon discountCoupon;

}