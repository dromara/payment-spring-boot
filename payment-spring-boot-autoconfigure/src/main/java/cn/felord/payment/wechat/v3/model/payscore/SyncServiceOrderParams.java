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
 * 同步服务订单信息请求参数.
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class SyncServiceOrderParams {

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
     * 该服务ID有本接口对应产品的权限。与订单要保持一致。
     */
    private String serviceId;
    /**
     * 场景类型，必填，场景类型为“Order_Paid”，字符串表示“订单收款成功” 。
     */
    private String type = "Order_Paid";

    /**
     * 内容信息详情，场景类型为Order_Paid时，为必填项。
     */
    private SyncDetail detail;


    /**
     * 内容信息详情
     */
    @Data
    public static class SyncDetail{
        /**
         * 收款成功时间
         * <p>
         * 支付成功时间，支持两种格式：yyyyMMddHHmmss和yyyyMMdd
         * ● 传入20091225091010表示2009年12月25日9点10分10秒。
         * ● 传入20091225默认认为时间为2009年12月25日0点0分0秒。
         * 用户通过其他方式付款成功的实际时间需满足条件：服务开始时间＜调用商户完结订单接口的时间＜用户通过其他方式付款成功的实际时间≤商户调用支付分订单同步接口的时间。
         * 【服务开始时间】
         * 1、当完结订单有填写【实际服务开始时间】时，【服务开始时间】=完结订单【实际服务开始时间】。
         * 2、当完结订单未填写【实际服务开始时间】时，【服务开始时间】=创建订单【服务开始时间】
         * 场景类型为Order_Paid时，必填。
         * 支持两种格式：yyyyMMddHHmmss和yyyyMMdd
         * ● 传入20091225091010表示2009年12月25日9点10分10秒。
         * ● 传入20091225表示时间为2009年12月25日23点59分59秒。
         * 注意：微信支付分会根据此时间更新用户侧的守约记录、负面记录信息；因此请务必如实填写用户实际付款成功时间，以免造成不必要的客诉。
         */
        private String paidTime;
    }
}
