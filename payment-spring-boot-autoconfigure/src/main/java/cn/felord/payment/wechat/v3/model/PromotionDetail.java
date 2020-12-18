
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
 * The type Promotion detail.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class PromotionDetail {

    /**
     * The Amount.
     */
    private Long amount;
    /**
     * The Coupon id.
     */
    private String couponId;
    /**
     * The Currency.
     */
    private String currency;
    /**
     * The Goods detail.
     */
    private List<GoodsDetail> goodsDetail;
    /**
     * The Merchant contribute.
     */
    private Long merchantContribute;
    /**
     * The Name.
     */
    private String name;
    /**
     * The Other contribute.
     */
    private Long otherContribute;
    /**
     * The Scope.
     */
    private String scope;
    /**
     * The Stock id.
     */
    private String stockId;
    /**
     * The Type.
     */
    private String type;
    /**
     * The Wechatpay contribute.
     */
    private Long wechatpayContribute;

    /**
     * The type Goods detail.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class GoodsDetail {

        /**
         * The Goods id.
         */
        private String goodsId;
        /**
         * The Quantity.
         */
        private Long quantity;
        /**
         * The Unit price.
         */
        private Long unitPrice;
        /**
         * The Discount amount.
         */
        private Long discountAmount;
        /**
         * The Goods remark.
         */
        private String goodsRemark;

    }
}
