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

import cn.felord.payment.wechat.enumeration.RefundStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 微信支付退款结果通知解密
 *
 * @author felord.cn
 * @since 1.0.6.RELEASE
 */
@Data
public class RefundConsumeData {
    /**
     * 直连商户号
     */
    private String mchid;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 微信订单号
     */
    private String transactionId;
    /**
     * 商户退款单号
     */
    private String outRefundNo;
    /**
     * 微信退款单号
     */
    private String refundId;
    /**
     * 退款状态
     */
    private RefundStatus refundStatus;
    /**
     * 退款成功时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
    private LocalDateTime successTime;
    /**
     * 退款入账账户
     */
    private String userReceivedAccount;
    /**
     * 金额信息
     */
    private Amount amount;


    /**
     * 微信支付退款金额信息
     *
     * @author felord.cn
     * @since 1.0.6.RELEASE
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
