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
package cn.felord.payment.wechat.v3.model.discountcard;

import cn.felord.payment.wechat.enumeration.ContractStatus;
import cn.felord.payment.wechat.enumeration.CountType;
import cn.felord.payment.wechat.enumeration.UnfinishedReason;
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
     * 应用appid需要绑定微信商户平台
     */
    private String appid;
    /**
     * 先享卡ID，唯一标识一个先享卡
     */
    private String cardId;
    /**
     * 先享卡模板ID，唯一定义此资源的标识。创建模板后可获得
     */
    private String cardTemplateId;
    /**
     * 创建先享卡的时间
     */
    private String createTime;
    /**
     * 商户号
     */
    private String mchid;
    /**
     * 用户先享卡目标列表
     */
    private List<Objective> objectives;
    /**
     * 用户标识，用户在{@code appid}下的唯一标识
     */
    private String openid;
    /**
     * 商户领卡号，商户在请求领卡预受理接口时传入的领卡请求号，同一个商户号下必须唯一，要求32个字符内，只能是数字、大小写字母_-|*
     */
    private String outCardCode;
    /**
     * 用户先享卡优惠列表
     */
    private List<Reward> rewards;
    /**
     * 先享卡的守约状态
     */
    private ContractStatus state;
    /**
     * 先享卡约定时间期限
     */
    private TimeRange timeRange;
    /**
     * 享受优惠总金额，单位为 “分”
     */
    private Long totalAmount;
    /**
     * 未完成约定原因
     */
    private UnfinishedReason unfinishedReason;

    /**
     * 目标列表属性
     *
     * @author felord.cn
     * @since 1.0.2.RELEASE
     */
    @Data
    public static class Objective {

        /**
         * 目标数量
         * <p>
         * 履约目标需要完成的数量，必须大于0。
         */
        private Long count;
        /**
         * 目标描述
         */
        private String description;
        /**
         * 目标名称
         */
        private String name;
        /**
         * 用户先享卡目标完成纪录
         */
        private List<ObjectiveCompletionRecord> objectiveCompletionRecords;
        /**
         * 目标id
         */
        private String objectiveId;
        /**
         * 目标单位
         * <p>
         * 示例值：次
         */
        private String unit;

    }

    /**
     * 先享卡约定时间期限
     *
     * @author felord.cn
     * @since 1.0.2.RELEASE
     */
    @Data
    public static class TimeRange {
        /**
         * 开始时间
         */
        private String betinTime;
        /**
         * 结束时间
         */
        private String endTime;
    }

    /**
     * 优惠列表属性
     *
     * @author felord.cn
     * @since 1.0.2.RELEASE
     */
    @Data
    public static class Reward {

        /**
         * 优惠金额
         * <p>
         * 1、优惠金额，此项优惠对应的优惠总金额，单位：分，必须大于0。
         * 2、此项优惠已享累计金额≤创建模板时配置的此项奖励的奖励金额，
         * 例如：优惠为【满10元减3元优惠券4张】时，用户一次消费使用了2张优惠券，优惠金额为本次优惠总金额6元，优惠数量为本次使用优惠的优惠券数量2张
         */
        private Long amount;
        /**
         * 优惠数量
         */
        private Long count;
        /**
         * 优惠数量类型
         */
        private CountType countType;
        /**
         * 优惠描述
         */
        private String description;
        /**
         * 优惠名称
         */
        private String name;
        /**
         * 优惠ID
         */
        private String rewardId;
        /**
         * 优惠单位，例如 “个”
         */
        private String unit;
        /**
         * 优惠使用记录列表
         */
        private List<RewardUsageRecord> rewardUsageRecords;

    }

}
