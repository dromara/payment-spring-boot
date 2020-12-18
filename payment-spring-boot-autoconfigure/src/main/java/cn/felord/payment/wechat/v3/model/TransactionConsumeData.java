
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

import lombok.Data;

import java.util.List;

/**
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class TransactionConsumeData {

    /**
     * The Amount.
     */
    private Amount amount;
    /**
     * The Appid.
     */
    private String appid;
    /**
     * The Attach.
     */
    private String attach;
    /**
     * The Bank type.
     */
    private String bankType;
    /**
     * The Mchid.
     */
    private String mchid;
    /**
     * The Out trade no.
     */
    private String outTradeNo;
    /**
     * The Payer.
     */
    private Payer payer;
    /**
     * The Promotion detail.
     */
    private List<PromotionDetail> promotionDetail;
    /**
     * The Scene info.
     */
    private SceneInfo sceneInfo;
    /**
     * The Success time.
     */
    private String successTime;
    /**
     * The Trade state.
     */
    private String tradeState;
    /**
     * The Trade state desc.
     */
    private String tradeStateDesc;
    /**
     * The Trade type.
     */
    private String tradeType;
    /**
     * The Transaction id.
     */
    private String transactionId;


    /**
     * The type Payer.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class Payer {
        /**
         * The Openid.
         */
        private String openid;
    }

    /**
     * The type Scene info.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class SceneInfo {
        /**
         * The Device id.
         */
        private String deviceId;
    }

    /**
     * The type Amount.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class Amount {
        /**
         * The Total.
         */
        private int total;
        /**
         * The Payer total.
         */
        private int payerTotal;
        /**
         * The Currency.
         */
        private String currency;
        /**
         * The Payer currency.
         */
        private String payerCurrency;
    }


}
