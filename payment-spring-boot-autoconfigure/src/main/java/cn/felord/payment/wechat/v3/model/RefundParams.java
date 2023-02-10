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
     * 子商户的商户号，由微信支付生成并下发.(服务商退款使用)
     */
    private String subMchid;

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
         * 退款出资账户及金额
         *
         * @since 1.0.11.RELEASE
         */
        private List<RefundForm> form;
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

    /**
     * 退款出资账户及金额
     * <p>
     * 退款需要从指定账户出资时，传递此参数指定出资金额（币种的最小单位，只能为整数）。
     * <p>
     * 同时指定多个账户出资退款的使用场景需要满足以下条件：
     * <ol>
     *  <li>未开通退款支出分离产品功能；</li>
     *  <li>订单属于分账订单，且分账处于待分账或分账中状态。</li>
     * </ol>
     * <p>
     * 参数传递需要满足条件：
     * <ol>
     *   <li>基本账户可用余额出资金额与基本账户不可用余额出资金额之和等于退款金额；</li>
     *   <li>账户类型不能重复。</li>
     * </ol>
     * <p>
     * 上述任一条件不满足将返回错误
     *
     * @author felord.cn
     * @since 1.0.11.RELEASE
     */
    @Data
    public static class RefundForm {
        /**
         * 出资账户类型
         * <p>
         * {@code AVAILABLE}  可用余额
         * {@code UNAVAILABLE} 不可用余额
         */
        private String account;
        /**
         * 对应账户出资金额
         */
        private String amount;

    }
}