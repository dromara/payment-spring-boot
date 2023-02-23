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
package cn.felord.payment.wechat.v3.model;

import cn.felord.payment.wechat.enumeration.TradeState;
import cn.felord.payment.wechat.enumeration.TradeType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 支付成功通知解密
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class TransactionConsumeData {

    /**
     * 订单金额
     */
    private Amount amount;
    /**
     * 直连模式应用ID，服务商模式请解析spAppid
     */
    private String appid;
    /**
     * 直连模式商户号，服务商模式请解析spMchid
     */
    private String mchid;
    /**
     * 服务商模式-服务商APPID
     */
    private String spAppid;
    /**
     * 服务商模式-服务商户号
     */
    private String spMchid;
    /**
     * 服务商模式-子商户appid
     */
    private String subAppid;
    /**
     * 服务商模式-子商户商户id
     */
    private String subMchid;
    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     */
    private String attach;
    /**
     * 银行类型，采用字符串类型的银行标识。银行标识请参考 <a target= "_blank" href= "https://pay.weixin.qq.com/wiki/doc/apiv3/terms_definition/chapter1_1_3.shtml#part-6">《银行类型对照表》</a>
     */
    private String bankType;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 支付者信息
     */
    private Payer payer;
    /**
     * 优惠功能，享受优惠时返回该字段。
     */
    private List<PromotionDetail> promotionDetail;
    /**
     * 支付场景信息描述
     */
    private SceneInfo sceneInfo;
    /**
     * 支付完成时间 YYYY-MM-DDTHH:mm:ss+TIMEZONE
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime successTime;
    /**
     * 在 1.0.0.RELEASE 直接返回了枚举字符串，1.0.2.RELEASE 中变更为枚举
     *
     * @since 1.0.0.RELEASE
     */
    private TradeState tradeState;
    /**
     * 交易状态描述
     */
    private String tradeStateDesc;
    /**
     * 交易类型
     * <p>
     * 在 1.0.0.RELEASE 直接返回了枚举字符串，1.0.2.RELEASE 中变更为枚举
     *
     * @since 1.0.0.RELEASE
     */
    private TradeType tradeType;
    /**
     * 微信支付订单号
     */
    private String transactionId;


    /**
     * 支付者信息
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class Payer {
        /**
         * 用户在直连商户appid下的唯一标识。
         */
        private String openid;
        /**
         * 用户在服务商appid下的唯一标识。
         */
        private String spOpenid;
        /**
         * 用户在子商户appid下的唯一标识。
         */
        private String subOpenid;
    }

    /**
     * 支付场景信息描述
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class SceneInfo {
        /**
         * 商户端设备号（门店号或收银设备ID）。
         */
        private String deviceId;
    }

    /**
     * 订单金额
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class Amount {
        /**
         * 订单总金额，单位为分。
         */
        private Integer total;
        /**
         * 用户支付金额，单位为分。
         */
        private Integer payerTotal;
        /**
         * CNY：人民币，境内商户号仅支持人民币。
         */
        private String currency;
        /**
         * 用户支付币种
         */
        private String payerCurrency;
    }


}
