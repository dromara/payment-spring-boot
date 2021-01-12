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

import cn.felord.payment.wechat.v3.WechatDirectPayApi;
import lombok.Data;

/**
 * 微信支付订单号查询API请求参数.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class TransactionQueryParams {
    /**
     * 商户id
     */
    private String mchId;
    /**
     * <ul>
     *     <li>调用 {@link WechatDirectPayApi#queryTransactionByOutTradeNo(TransactionQueryParams)} 传递【商户侧订单号】</li>
     *     <li>调用 {@link WechatDirectPayApi#queryTransactionById(TransactionQueryParams)} (TransactionQueryParams)} 传递【微信支付订单号】</li>
     * </ul>
     */
    private String transactionIdOrOutTradeNo;
}
