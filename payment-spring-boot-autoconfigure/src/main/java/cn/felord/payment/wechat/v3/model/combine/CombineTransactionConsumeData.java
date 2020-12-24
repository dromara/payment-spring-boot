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
 */
package cn.felord.payment.wechat.v3.model.combine;

import cn.felord.payment.wechat.enumeration.TradeState;
import cn.felord.payment.wechat.enumeration.TradeType;
import cn.felord.payment.wechat.v3.model.SceneInfo;
import lombok.Data;

import java.util.List;


/**
 * 合单支付回调解密数据.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CombineTransactionConsumeData {

    /**
     * 合单商户appid，即合单发起方的appid
     */
    private String combineAppid;

    /**
     * 合单商户号.
     */
    private String combineMchid;

    /**
     * 合单商户订单号.
     */
    private String combineOutTradeNo;

    /**
     * 支付者.
     */
    private CombinePayerInfo combinePayerInfo;

    /**
     * 场景信息，合单支付回调只返回device_id
     */
    private SceneInfo sceneInfo;

    /**
     * 合单支付回调子订单.
     */
    private List<SubOrderCallback> subOrders;

    /**
     * 合单支付回调子订单.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class SubOrderCallback {


        /**
         * 订单金额信息
         */
        private CombineAmount amount;

        /**
         * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
         */
        private String attach;

        /**
         * 付款银行类型，参见<a target= "_blank" href= "https://pay.weixin.qq.com/wiki/doc/apiv3/terms_definition/chapter1_1_3.shtml#part-4">开户银行对照表</a>
         */
        private String bankType;

        /**
         * 子单发起方商户号，必须与发起方Appid有绑定关系。（即电商平台mchid）
         */
        private String mchid;

        /**
         * 子单商户侧订单号
         */
        private String outTradeNo;

        /**
         * 二级商户商户号，由微信支付生成并下发。
         * 服务商子商户的商户号，被合单方。直连商户不用传二级商户号。
         */
        private String subMchid;

        /**
         * 支付完成时间
         */
        private String successTime;

        /**
         * 交易状态
         */
        private TradeState tradeState;

        /**
         * 交易类型
         */
        private TradeType tradeType;

        /**
         * 微信支付侧订单号
         */
        private String transactionId;

    }

    /**
     * 订单金额信息.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class CombineAmount {
        /**
         * 标价金额，单位为分.
         */
        private Long totalAmount;
        /**
         * 标价币种.
         */
        private String currency;
        /**
         * 现金支付金额.
         */
        private Long payerAmount;
        /**
         * 现金支付币种.
         */
        private String payerCurrency;
    }

}
