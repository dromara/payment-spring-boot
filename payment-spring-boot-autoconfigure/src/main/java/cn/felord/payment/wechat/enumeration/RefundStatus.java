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

package cn.felord.payment.wechat.enumeration;

/**
 *  退款成功事件.
 *
 * @author felord.cn
 * @since 1.0.6.RELEASE
 */
public enum RefundStatus {
    /**
     * 退款异常事件.
     *
     * @since 1.0.6.RELEASE
     */
    ABNORMAL("REFUND.ABNORMAL"),

    /**
     * 退款关闭事件.
     *
     * @since 1.0.6.RELEASE
     */
    CLOSED("REFUND.CLOSED"),
    /**
     * 支付成功事件.
     *
     * @since 1.0.0.RELEASE
     */
    TRANSACTION("TRANSACTION.SUCCESS");
    /**
     * The Event.
     */
    private final String refundStatus;

    /**
     * Instantiates a new Event type.
     *
     * @param refundStatus the event
     */
    RefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatus() {
        return refundStatus;
    }
}
