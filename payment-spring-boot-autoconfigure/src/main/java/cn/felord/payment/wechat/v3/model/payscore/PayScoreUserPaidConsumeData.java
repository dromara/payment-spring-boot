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
package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

import java.util.List;

/**
 * 微信支付分支付成功回调解密
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class PayScoreUserPaidConsumeData {
    /**
     * The Appid.
     */
    private String appid;
    /**
     * The Attach.
     */
    private String attach;
    /**
     * The Collection.
     */
    private PaymentCollection collection;
    /**
     * The Location.
     */
    private Location location;
    /**
     * The Mchid.
     */
    private String mchid;
    /**
     * The Need collection.
     */
    private Boolean needCollection;
    /**
     * The Notify url.
     */
    private String notifyUrl;
    /**
     * The Openid.
     */
    private String openid;
    /**
     * The Order id.
     */
    private String orderId;
    /**
     * The Out order no.
     */
    private String outOrderNo;
    /**
     * The Post discounts.
     */
    private List<PostDiscount> postDiscounts;
    /**
     * The Post payments.
     */
    private List<PostPayment> postPayments;
    /**
     * The Risk fund.
     */
    private RiskFund riskFund;
    /**
     * The Service id.
     */
    private String serviceId;
    /**
     * The Service introduction.
     */
    private String serviceIntroduction;
    /**
     * The State.
     */
    private String state;
    /**
     * The Time range.
     */
    private TimeRange timeRange;
    /**
     * stateDescription
     */
    private String stateDescription;
    /**
     * The Total amount.
     */
    private String totalAmount;
}
