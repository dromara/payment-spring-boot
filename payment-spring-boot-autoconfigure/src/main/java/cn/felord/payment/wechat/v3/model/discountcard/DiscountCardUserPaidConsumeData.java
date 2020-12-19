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
package cn.felord.payment.wechat.v3.model.discountcard;

import lombok.Data;

/**
 * 先享卡扣费状态变化通知解密.
 *
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class DiscountCardUserPaidConsumeData {

    /**
     * The Appid.
     */
    private String appid;
    /**
     * The Card id.
     */
    private String cardId;
    /**
     * The Card template id.
     */
    private String cardTemplateId;
    /**
     * The Mchid.
     */
    private String mchid;
    /**
     * The Openid.
     */
    private String openid;
    /**
     * The Out card code.
     */
    private String outCardCode;
    /**
     * The Pay information.
     */
    private PayInformation payInformation;
    /**
     * The State.
     */
    private String state;
    /**
     * The Total amount.
     */
    private Long totalAmount;
    /**
     * The Unfinished reason.
     */
    private String unfinishedReason;

    /**
     * The type Pay information.
     */
    @Data
    public static class PayInformation {

        /**
         * The Pay amount.
         */
        private Long payAmount;
        /**
         * The Pay state.
         */
        private String payState;
        /**
         * The Pay time.
         */
        private String payTime;
        /**
         * The Transaction id.
         */
        private String transactionId;
    }
}
