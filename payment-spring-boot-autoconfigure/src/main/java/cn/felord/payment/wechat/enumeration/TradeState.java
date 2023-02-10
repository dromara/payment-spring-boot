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
package cn.felord.payment.wechat.enumeration;

/**
 * 微信侧返回交易状态
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public enum TradeState {
    /**
     * 支付成功
     *
     * @since 1.0.0.RELEASE
     */
    SUCCESS,
    /**
     * 转入退款
     *
     * @since 1.0.0.RELEASE
     */
    REFUND,
    /**
     * 未支付
     *
     * @since 1.0.0.RELEASE
     */
    NOTPAY,
    /**
     * 已关闭
     *
     * @since 1.0.0.RELEASE
     */
    CLOSED,
    /**
     * 已撤销（付款码支付）
     *
     * @since 1.0.0.RELEASE
     */
    REVOKED,
    /**
     * 用户支付中（付款码支付）
     *
     * @since 1.0.0.RELEASE
     */
    USERPAYING,
    /**
     * 支付失败(其他原因，如银行返回失败)
     *
     * @since 1.0.0.RELEASE
     */
    PAYERROR,
    /**
     * 已接收，等待扣款
     *
     * @since 1.0.6.RELEASE
     */
    ACCEPT,
}
