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
 * 完结支付分订单请求参数
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class CompleteServiceOrderParams {

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
     * 该服务ID有本接口对应产品的权限。
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
     * <p>
     * 不能超过完结订单时候的总金额，只能为整数，详见 <a target= "_blank" href= "https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=4_2">支付金额</a>。此参数需满足：总金额 =（修改后付费项目1…+修改后完结付费项目n）-（修改 后付费商户优惠项目1…+修改后付费商户优惠项目n）
     */
    private Long totalAmount;
    /**
     * 服务时间段，条件选填
     * <p>
     * 服务时间范围，创建订单未填写服务结束时间，则完结的时候，服务结束时间必填
     * 如果传入，用户侧则显示此参数。
     */
    private TimeRange timeRange;
    /**
     * 服务位置，选填
     */
    private CompleteLocation location;
    /**
     * 微信支付服务分账标记，选填
     * <p>
     * 完结订单分账接口标记。分账开通流程，详见 <a target = "_blank" href = "https://pay.weixin.qq.com/wiki/doc/api/allocation.php?chapter=26_2">分账</a>
     * false：不分账，默认：false
     * true：分账。
     */
    private Boolean profitSharing = Boolean.TRUE;
    /**
     * 订单优惠标记，选填
     * <p>
     * 代金券或立减金优惠的参数，说明详见代金券或立减金优惠
     */
    private String goods_tag;


    /**
     * 服务位置信息
     * <p>
     * 如果传入，用户侧则显示此参数。
     *
     * @author felord.cn
     * @since 1.0.2.RELEASE
     */
    @Data
    public static class CompleteLocation {

        /**
         * 预计服务结束地点，条件选填。
         * <p>
         * 结束使用服务的地点，不超过50个字符，超出报错处理 。 创建订单传入了【服务开始地点】，此项才能填写
         * 【建议】
         * 1、预计结束地点为空时，实际结束地点与开始地点相同，不填写
         * 2、预计结束地点不为空时，实际结束地点与预计结束地点相同，不填写
         */
        private String endLocation;
    }
}
