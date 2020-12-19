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

import java.util.List;

/**
 * 微信支付先享卡用户守约状态变化通知解密
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class DiscountCardAgreementEndConsumeData {
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
     * The State.
     */
    private String state;
    /**
     * The Time range.
     */
    private TimeRange timeRange;
    /**
     * The Total amount.
     */
    private Long totalAmount;
    /**
     * The Unfinished reason.
     */
    private String unfinishedReason;

    /**
     * The type Objective.
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
         * The Objective completion records.
         */
        private List<ObjectiveCompletionRecord> objectiveCompletionRecords;
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
     * The type Reward.
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
        private String countType;
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
         * The Reward usage records.
         */
        private List<RewardUsageRecord> rewardUsageRecords;
        /**
         * The Unit.
         */
        private String unit;

    }
}
