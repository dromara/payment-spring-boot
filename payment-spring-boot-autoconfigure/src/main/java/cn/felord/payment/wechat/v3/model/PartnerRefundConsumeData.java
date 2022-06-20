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
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
@Data
public class PartnerRefundConsumeData {
    private String transactionId;
    private Amount amount;
    private String outTradeNo;
    private String refundStatus;
    private String outRefundNo;
    private String refundAccount;
    private String spMchid;
    private String subMchid;
    private String successTime;
    private String userReceivedAccount;
    private String refundId;

    /**
     * 微信支付退款金额信息
     *
     * @author felord.cn
     * @since 1.0.14.RELEASE
     */
    @Data
    public static class Amount {
        /**
         * 订单金额，单位为分
         */
        private Integer total;
        /**
         * 退款金额，单位为分
         */
        private Integer refund;
        /**
         * 用户实际支付金额，单位为分
         */
        private Integer payerTotal;
        /**
         * 退款给用户的金额，单位为分，不包含所有优惠券金额
         */
        private Integer payerRefund;
    }
}
