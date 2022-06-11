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
 * 退款订单的商品信息
 *
 * @author felord.cn
 * @since 1.0.6.RELEASE
 */
@Data
public class RefundGoodsDetail {
    /**
     * 商户侧商品编码
     */
    private String merchantGoodsId;
    /**
     * 微信侧商品编码
     */
    private String wechatpayGoodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品单价金额，单位为分。
     */
    private Integer unitPrice;
    /**
     * 商品退款金额，单位为分。
     */
    private Integer refundAmount;
    /**
     * 单品的退款数量。
     */
    private Integer refundQuantity;
}
