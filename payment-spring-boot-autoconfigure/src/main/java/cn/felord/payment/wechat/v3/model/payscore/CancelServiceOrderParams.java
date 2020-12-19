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
package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 取消支付分订单请求参数
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class CancelServiceOrderParams {
    /**
     * 商户服务订单号，必填
     */
    private String outOrderNo;
    /**
     * 与传入的商户号建立了支付绑定关系的appid，必填
     */
    private String appid;
    /**
     * 服务ID，必填
     */
    private String serviceId;
    /**
     * 取消原因，最长50个字符，必填
     */
    private String reason;
}
