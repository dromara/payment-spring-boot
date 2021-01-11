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

/**
 * 微信代金券核销通知参数
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CouponConsumeData {

    /**
     * The Available begin time.
     */
    private String availableBeginTime;
    /**
     * The Available end time.
     */
    private String availableEndTime;
    /**
     * The Consume information.
     */
    private ConsumeInformation consumeInformation;
    /**
     * The Coupon id.
     */
    private String couponId;
    /**
     * The Coupon name.
     */
    private String couponName;
    /**
     * The Coupon type.
     */
    private CouponType couponType;
    /**
     * The Create time.
     */
    private String createTime;
    /**
     * The Description.
     */
    private String description;
    /**
     * The Discount to.
     */
    private DiscountTo discountTo;
    /**
     * The No cash.
     */
    private Boolean noCash;
    /**
     * The Normal coupon information.
     */
    private NormalCouponInformation normalCouponInformation;
    /**
     * The Singleitem.
     */
    private Boolean singleitem;
    /**
     * The Singleitem discount off.
     */
    private SingleitemDiscountOff singleitemDiscountOff;
    /**
     * The Status.
     */
    private String status;
    /**
     * The Stock creator mchid.
     */
    private String stockCreatorMchid;
    /**
     * The Stock id.
     */
    private String stockId;

    /**
     * 券类型
     *
     * @since 1.0.2.RELEASE
     */
    public enum CouponType{
        /**
         * 满减券
         */
        NORMAL,
        /**
         * 减至券
         */
        CUT_TO
    }
}
