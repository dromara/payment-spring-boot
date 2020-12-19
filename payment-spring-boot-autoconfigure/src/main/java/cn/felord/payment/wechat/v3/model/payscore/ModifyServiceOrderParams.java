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

import java.util.List;

/**
 *  修改微信支付分订单金额请求参数.
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class ModifyServiceOrderParams {

    /**
     * 商户服务订单号，必填
     * <p>
     * 商户系统内部服务订单号（不是交易单号），要求此参数只能由数字、大小写字母_-|*组成，且在同一个商户号下唯一。详见[商户订单号]。
     */
    private String outOrderNo;
    /**
     * 与传入的商户号建立了支付绑定关系的appid，必填
     */
    private String appid;
    /**
     * 服务ID，必填
     * <p>
     * 该服务ID有本接口对应产品的权限。需要与创建订单时保持一致。
     */
    private String serviceId;
    /**
     * 后付费项目，必填
     */
    private List<PostPayment> postPayments;
    /**
     * 后付费商户优惠，选填
     */
    private List<PostDiscount> postDiscounts;
    /**
     * 总金额，单位分，必填
     *
     * 不能超过完结订单时候的总金额，只能为整数，详见 <a target= "_blank" href= "https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=4_2">支付金额</a>。此参数需满足：总金额 =（修改后付费项目1…+修改后完结付费项目n）-（修改 后付费商户优惠项目1…+修改后付费商户优惠项目n）
     */
    private Long totalAmount;
    /**
     * 取消原因，最长50个字符，必填
     */
    private String reason;

}
