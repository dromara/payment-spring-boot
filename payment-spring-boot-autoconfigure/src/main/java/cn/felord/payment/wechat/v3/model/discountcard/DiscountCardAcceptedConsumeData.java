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

import cn.felord.payment.wechat.enumeration.ContractStatus;
import lombok.Data;

import java.util.List;

/**
 * 用户领取微信先享卡通知解密
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class DiscountCardAcceptedConsumeData {

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
     * The Create time.
     */
    private String createTime;
    /**
     * The Mchid.
     */
    private String mchid;
    /**
     * The Objectives.
     */
    private List<Objective> objectives;
    /**
     * The Openid.
     */
    private String openid;
    /**
     * The Out card code.
     */
    private String outCardCode;
    /**
     * The Rewards.
     */
    private List<Reward> rewards;
    /**
     * The Sharer openid.
     */
    private String sharerOpenid;
    /**
     * The State.
     */
    private ContractStatus state;
    /**
     * The Time range.
     */
    private TimeRange timeRange;

    /**
     * 用户先享卡目标列表
     *
     * @author felord.cn
     * @since 1.0.2.RELEASE
     */
    @Data
    public static class Objective {

        /**
         * The Count.
         */
        private Long count;
        /**
         * The Description.
         */
        private String description;
        /**
         * The Name.
         */
        private String name;
        /**
         * The Objective id.
         */
        private String objectiveId;
        /**
         * The Unit.
         */
        private String unit;

    }

    /**
     * 用户先享卡优惠列表
     *
     * @author felord.cn
     * @since 1.0.2.RELEASE
     */
    @Data
    public static class Reward {

        /**
         * The Amount.
         */
        private Long amount;
        /**
         * The Count.
         */
        private Long count;
        /**
         * The Count type.
         */
        private CountType countType;
        /**
         * The Description.
         */
        private String description;
        /**
         * The Name.
         */
        private String name;
        /**
         * The Reward id.
         */
        private String rewardId;
        /**
         * The Unit.
         */
        private String unit;

    }

    /**
     * The type Time range.
     *
     * @author felord.cn
     * @since 1.0.2.RELEASE
     */
    @Data
    public static class TimeRange {
        /**
         * The Betin time.
         */
        private String betinTime;
        /**
         * The End time.
         */
        private String endTime;
    }

    /**
     * 优惠数量的类型标识
     *
     * @author felord.cn
     * @since 1.0.2.RELEASE
     */
    public enum CountType{
        /**
         * 不限数量
         */
        COUNT_UNLIMITED,
        /**
         * 有限数量
         */
        COUNT_LIMIT
    }
}
