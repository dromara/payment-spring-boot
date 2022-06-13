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

package cn.felord.payment.wechat.v3.model.payscore.parking;

import cn.felord.payment.wechat.v3.model.Payer;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 订单支付结果通知API回调结果
 *
 * @author felord.cn
 * @since 1.0.13.RELEASE
 */
@Data
public class TransParkingCallback {
    /**
     * 调用接口提交的应用ID
     */
    private String appid;
    /**
     * 调用接口提交的商户号
     */
    private String spMchid;
    /**
     * 调用接口提交的商户服务订单号
     */
    private String outTradeNo;
    /**
     * 微信支付系统生成的订单号。
     */
    private String transactionId;
    /**
     * 商户自定义字段，用户交易账单中对扣费服务的描述。
     */
    private String description;
    /**
     * 订单成功创建时返回
     */
    private OffsetDateTime createTime;
    /**
     * 交易状态
     */
    private ParkingTradeState tradeState;
    /**
     * 交易状态描述
     */
    private String tradeStateDescription;
    /**
     * 支付完成时间
     */
    private OffsetDateTime successTime;
    /**
     * 付款银行类型，参见<a target= "_blank" href= "https://pay.weixin.qq.com/wiki/doc/apiv3/terms_definition/chapter1_1_3.shtml#part-4">开户银行对照表</a>
     */
    private String bankType;
    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用，实际情况下只有支付完成状态才会返回该字段。
     */
    private String attach;
    /**
     * 用户是否已还款
     * <p>
     * 枚举值：
     * <ul>
     *     <li>Y：用户已还款</li>
     *     <li>N：用户未还款</li>
     * </ul>
     * <p>
     * 注意：使用此字段前需先确认bank_type字段值为BPA以及 trade_state字段值为SUCCESS。
     */
    private String userRepaid;
    /**
     * 交易场景值，目前支持PARKING，车场停车场景
     */
    private String tradeScene;
    /**
     * trade_scene为PARKING时，返回停车场景信息
     */
    private ParkingInfo parkingInfo;
    /**
     * 支付者信息
     */
    private Payer payer;
    /**
     * 订单金额信息
     */
    private Amount amount;
    /**
     * 优惠功能信息
     */
    private PromotionDetail promotionDetail;

    /**
     * 交易状态
     */
    public enum ParkingTradeState {
        /**
         * 支付成功
         */
        SUCCESS,
        /**
         * 已接收，等待扣款
         */
        ACCEPT,
        /**
         * 支付失败(其他原因，如银行返回失败)
         */
        PAY_FAIL,
        /**
         * 转入退款
         */
        REFUND
    }

    /**
     * 订单金额信息
     */
    @Data
    public static class Amount {
        /**
         * 订单总金额，单位为分，只能为整数
         */
        private Integer total;
        /**
         * 订单折扣
         */
        private Integer discountTotal;
        /**
         * 用户实际支付金额，单位为分，只能为整数
         */
        private Integer payerTotal;
        /**
         * 符合ISO 4217标准的三位字母代码，目前只支持人民币：CNY
         */
        private String currency;
    }

    /**
     * 优惠功能信息
     */
    @Data
    public static class PromotionDetail {
        /**
         * 立减优惠id
         */
        private String promotionId;
        /**
         * 优惠券ID
         */
        private String couponId;
        /**
         * 优惠名称
         */
        private String name;
        /**
         * 优惠范围
         */
        private String scope;
        /**
         * 优惠类型
         * <p>
         * 枚举值：
         * <ul>
         *     <li>CASH：充值型代金券</li>
         *     <li>NOCASH：免充值型代金券</li>
         * </ul>
         */
        private String type;
        /**
         * 优惠券面额
         */
        private Integer amount;
        /**
         * 活动ID
         */
        private String activityId;
        /**
         * 特指由微信支付商户平台创建的优惠，出资金额等于本项优惠总金额，单位为分。
         */
        private Integer wechatpayContribute;
        /**
         * 特指商户自己创建的优惠，出资金额等于本项优惠总金额，单位为分。
         */
        private Integer merchantContribute;
        /**
         * 其他出资方出资金额，单位为分。
         */
        private Integer otherContribute;
        /**
         * 优惠币种，境内商户号仅支持人民币。
         */
        private String currency;
    }

}
