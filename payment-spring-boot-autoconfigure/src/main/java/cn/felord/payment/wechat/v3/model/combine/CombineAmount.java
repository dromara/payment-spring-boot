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
package cn.felord.payment.wechat.v3.model.combine;


import lombok.Data;

/**
 * 合单支付订单金额信息.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CombineAmount {
    /**
     * 符合ISO 4217标准的三位字母代码，必填，人民币：CNY 。
     *
     * @since 1.0.0.RELEASE
     */
    private String currency = "CNY";
    /**
     * 子单金额，单位为分，必填
     * <p>
     * 境外场景下，标价金额要超过商户结算币种的最小单位金额，例如结算币种为美元，则标价金额必须大于1美分
     *
     * @since 1.0.0.RELEASE
     */
    private Long totalAmount;
}
