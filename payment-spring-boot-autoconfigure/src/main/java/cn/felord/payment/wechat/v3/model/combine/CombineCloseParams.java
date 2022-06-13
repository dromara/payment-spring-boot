/*
 *
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
 *
 */
package cn.felord.payment.wechat.v3.model.combine;

import lombok.Data;

import java.util.List;

/**
 * 合单支付 关单参数.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CombineCloseParams {
    /**
     * 合单商户appid，必填
     */
    private String combineAppid;
    /**
     * 合单商户订单号，必填，商户侧需要保证同一商户下唯一
     */
    private String combineOutTradeNo;
    /**
     * 子单信息，必填，最多50单
     */
    private List<ClosingSubOrder> subOrders;

    /**
     * 关单-子单信息，最多50单.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    @Data
    public static class ClosingSubOrder {
        /**
         * 子单发起方商户号，必填，必须与发起方appid有绑定关系。
         */
        private String mchid;

        /**
         * 子单商户订单号，必填，商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
         */
        private String outTradeNo;

        /**
         * 二级商户商户号，由微信支付生成并下发。
         * <p>
         * 服务商子商户的商户号，被合单方。
         * <p>
         * 直连商户不用传二级商户号。
         */
        private String subMchid;

    }
}
