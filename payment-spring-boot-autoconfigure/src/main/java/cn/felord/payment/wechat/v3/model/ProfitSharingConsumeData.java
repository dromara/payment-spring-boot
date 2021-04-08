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

import cn.felord.payment.wechat.v2.model.allocation.Receiver;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 微信支付分账通知参数
 *
 * @author wangzecheng
 * @since 1.0.10.RELEASE
 */
@Data
public class ProfitSharingConsumeData {

    /**
     * 直连商户号.
     * <p>
     * 直连模式分账发起和出资商户
     */
    private String mchid;

    /**
     * 微信订单号.
     * <p>
     * 微信支付订单号
     */
    private String transactionId;

    /**
     * 微信分账/回退单号.
     */
    private String orderId;

    /**
     * 商户分账/回退单号.
     * <p>
     * 分账方系统内部的分账/回退单号
     */
    private String outOrderNo;

    /**
     * 分账接收方.
     * <p>
     * 分账接收方对象
     */
    private Receiver receiver;

    /**
     * 成功时间.
     * <p>
     * Rfc3339标准
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
    private LocalDateTime successTime;

}
