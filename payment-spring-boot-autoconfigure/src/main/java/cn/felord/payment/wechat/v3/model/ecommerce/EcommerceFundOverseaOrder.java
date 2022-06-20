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

package cn.felord.payment.wechat.v3.model.ecommerce;

import lombok.Data;

import java.util.List;

/**
 * The type Ecommerce fun oversea order.
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
@Data
public class EcommerceFundOverseaOrder {
    private String outOrderId;
    private String subMchid;
    private String transactionId;
    private Integer amount;
    private String foreignCurrency;
    private List<GoodsInfo> goodsInfo;
    private SellerInfo sellerInfo;
    private ExpressInfo expressInfo;
    private PayeeInfo payeeInfo;


    /**
     * The type Goods info.
     */
    @Data
    public static class GoodsInfo {
        private String goodsName;
        private String goodsCategory;
        private Integer goodsUnitPrice;
        private Integer goodsQuantity;
    }

    /**
     * The type Seller info.
     */
    @Data
    public static class SellerInfo {
        private String overseaBusinessName;
        private String overseaShopName;
        private String sellerId;
    }

    /**
     * The type Express info.
     */
    @Data
    public static class ExpressInfo {
        private String courierNumber;
        private String expressCompanyName;
    }

    /**
     * The type Payee info.
     */
    @Data
    public static class PayeeInfo {
        private String payeeId;
    }
}
