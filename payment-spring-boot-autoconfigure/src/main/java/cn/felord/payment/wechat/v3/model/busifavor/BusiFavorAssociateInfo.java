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
 * 商家券关联订单信息API请求参数
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class BusiFavorAssociateInfo {

    /**
     * 批次号
     */
    private String stockId;
    /**
     * 券code
     */
    private String couponCode;
    /**
     * 关联的商户订单号
     */
    private String outTradeNo;
    /**
     * 商户请求单号
     * @see BusiFavorCreateParams#getOutRequestNo()
     */
    private String outRequestNo;
}
