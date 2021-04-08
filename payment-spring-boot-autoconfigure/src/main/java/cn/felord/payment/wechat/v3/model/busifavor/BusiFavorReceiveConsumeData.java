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
package cn.felord.payment.wechat.v3.model.busifavor;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商家券领券事件回调通知解密
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class BusiFavorReceiveConsumeData {

    /**
     * 业务细分事件类型
     * <p>
     * 枚举值：
     * EVENT_TYPE_BUSICOUPON_SEND：商家券用户领券通知
     */
    private String eventType;
    /**
     * 券code
     */
    private String couponCode;
    /**
     * 批次号
     */
    private String stockId;
    /**
     * 发放时间 rfc 3339  yyyy-MM-ddTHH:mm:ss+TIMEZONE
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+8")
    private LocalDateTime sendTime;
    /**
     * 微信用户在appid下的唯一标识。
     */
    private String openid;
    /**
     * 微信用户在同一个微信开放平台账号下的唯一用户标识，
     * unionid获取方式请参见 <a target= "_blank" href= "https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/union-id.html">《UnionID机制说明》</a> 文档。
     */
    private String unionid;
    /**
     * 发券商户号
     */
    private String sendMerchant;
    /**
     * 发放渠道
     */
    private SendChannel sendChannel;
    /**
     * 发券附加信息，仅在支付有礼、扫码领券（营销馆）、会员有礼发放渠道，才有该信息
     */
    private AttachInfo attachInfo;


    /**
     * 发放渠道
     *
     * @author felord.cn
     * @since 1.0.4.RELEASE
     */
    public enum SendChannel {

        /**
         * 小程序
         */
        BUSICOUPON_SEND_CHANNEL_MINIAPP,
        /**
         * API
         */
        BUSICOUPON_SEND_CHANNEL_API,
        /**
         * 支付有礼
         */
        BUSICOUPON_SEND_CHANNEL_PAYGIFT,
        /**
         * H5
         */
        BUSICOUPON_SEND_CHANNEL_H5,

        /**
         * 面对面
         */
        BUSICOUPON_SEND_CHANNEL_FTOF,
        /**
         * 会员卡活动
         */
        BUSICOUPON_SEND_CHANNEL_MEMBER_CARD_ACT,
        /**
         * 扫码领券（营销馆）
         */
        BUSICOUPON_SEND_CHANNEL_HALL
    }

    /**
     * 商家券领券事件回调通知解密-发券附加信息
     * <p>
     * 仅在支付有礼、扫码领券（营销馆）、会员有礼发放渠道，才有该信息
     *
     * @author felord.cn
     * @since 1.0.4.RELEASE
     */
    @Data
    public static class AttachInfo {

        /**
         * 交易订单编号
         * <p>
         * 仅在支付有礼渠道，才有该信息，对应支付有礼曝光支付订单编号信息
         */
        private String transactionId;
        /**
         * 支付有礼活动编号对应{@link SendChannel#BUSICOUPON_SEND_CHANNEL_PAYGIFT}
         * <p>
         * 营销馆活动ID对应{@link SendChannel#BUSICOUPON_SEND_CHANNEL_HALL}
         * <p>
         * 二选一，且只在对应场景下出现
         */
        private String actCode;
        /**
         * 仅在扫码领券（营销馆）渠道，才有该信息，对应领券的营销馆 馆ID信息
         */
        private String hallCode;
        /**
         * 仅在扫码领券（营销馆）渠道，才有该信息，对应领券的营销馆所属商户号信息
         */
        private Integer hallBelongMchId;
        /**
         * 仅在会员卡活动渠道，才有该信息，对应会员卡Card_ID信息
         */
        private String cardId;
        /**
         * 仅在会员卡活动渠道，才有该信息，对应用户卡包会员卡卡Code信息
         */
        private String code;
        /**
         * 仅在会员卡活动渠道，才有该信息，对应会员有礼活动ID信息
         */
        private String activityId;
    }
}
