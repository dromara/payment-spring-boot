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

package cn.felord.payment.wechat.v3.model.busifavor;

import lombok.Data;

/**
 * 营销补差付款API请求参数
 *
 * @author felord.cn
 * @since 1.0.13.RELEASE
 */
@Data
public class BusiFavorSubsidyParams {
    /**
     * 商家券批次号，必传
     */
    private String stockId;
    /**
     * 商家券Code，必传
     */
    private String couponCode;
    /**
     * 微信支付订单号，必传
     */
    private String transactionId;
    /**
     * 营销补差扣款商户号，必传
     */
    private String payerMerchant;
    /**
     * 营销补差入账商户号，必传
     */
    private String payeeMerchant;
    /**
     * 补差付款金额，必传
     */
    private Integer amount;
    /**
     * 补差付款描述，必传
     */
    private String description;
    /**
     * 业务请求唯一单号，必传
     */
    private String outSubsidyNo;
}
