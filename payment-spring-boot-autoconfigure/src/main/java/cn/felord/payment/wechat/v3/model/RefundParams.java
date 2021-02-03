/*
 *
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
 *
 */
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

import java.util.List;

/**
 * 退款请求参数
 *
 * @author felord.cn
 * @since 1.0.6.RELEASE
 */
@Data
public class RefundParams {
    /**
     * 微信支付订单号，同{@link RefundParams#outTradeNo} 二选一
     */
    private String transactionId;
    /**
     * 商户订单号，同{@link RefundParams#transactionId} 二选一
     */
    private String outTradeNo;
    /**
     * 商户退款单号
     */
    private String outRefundNo;
    /**
     * 退款原因
     */
    private String reason;
    /**
     * 退款结果回调url
     */
    private String notifyUrl;
    /**
     * 退款资金来源，若传递此参数则使用对应的资金账户退款，否则默认使用未结算资金退款（仅对老资金流商户适用）。
     * 枚举值：
     * <ul>
     *     <li>AVAILABLE：可用余额账户</li>
     * </ul>
     */
    private String fundsAccount;
    /**
     * 退款订单金额信息
     */
    private RefundAmount amount;
    /**
     * 退款订单的商品信息
     */
    private List<RefundGoodsDetail> goodsDetail;

    /**
     * 退款订单金额信息
     *
     * @author felord.cn
     * @since 1.0.6.RELEASE
     */
    @Data
    public static class RefundAmount {
        /**
         * 原订单金额，币种的最小单位，只能为整数，不能超过原订单支付金额。
         */
        private Integer total;
        /**
         * 符合ISO 4217标准的三位字母代码，目前只支持人民币：CNY。
         */
        private String currency = "CNY";
        /**
         * 退款金额，币种的最小单位，只能为整数，不能超过原订单支付金额。
         */
        private Integer refund;
    }

}