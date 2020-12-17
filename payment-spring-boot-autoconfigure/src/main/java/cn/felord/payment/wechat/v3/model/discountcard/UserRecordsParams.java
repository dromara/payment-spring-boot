
package cn.felord.payment.wechat.v3.model.discountcard;

import lombok.Data;

import java.util.List;

/**
 * 增加用户记录请求参数
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class UserRecordsParams {

    /**
     * The Out card code.
     */
    private String outCardCode;
    /**
     * The Card template id.
     */
    private String cardTemplateId;
    /**
     * The Objective completion records.
     */
    private List<ObjectiveCompletionRecord> objectiveCompletionRecords;
    /**
     * The Reward usage records.
     */
    private List<RewardUsageRecord> rewardUsageRecords;

    /**
     * The type Objective completion record.
     *
     * @author felord.cn
     * @since 1.0.2.RELEASE
     */
    @Data
    public static class ObjectiveCompletionRecord {

        /**
         * The Completion count.
         */
        private Long completionCount;
        /**
         * The Completion time.
         */
        private String completionTime;
        /**
         * The Completion type.
         */
        private StrategyType completionType;
        /**
         * The Description.
         */
        private String description;
        /**
         * The Objective completion serial no.
         */
        private String objectiveCompletionSerialNo;
        /**
         * The Objective id.
         */
        private String objectiveId;
        /**
         * The Remark.
         */
        private String remark;

    }

    /**
     * The type Reward usage record.
     *
     * @author felord.cn
     * @since 1.0.2.RELEASE
     */
    @Data
    public static class RewardUsageRecord {

        /**
         * The Amount.
         */
        private Long amount;
        /**
         * The Description.
         */
        private String description;
        /**
         * The Remark.
         */
        private String remark;
        /**
         * The Reward id.
         */
        private String rewardId;
        /**
         * The Reward usage serial no.
         */
        private String rewardUsageSerialNo;
        /**
         * The Usage count.
         */
        private Long usageCount;
        /**
         * The Usage time.
         */
        private String usageTime;
        /**
         * The Usage type.
         */
        private StrategyType usageType;

    }

    /**
     * 目标完成类型、优惠使用类型
     *
     * @author felord.cn
     * @since 1.0.2.RELEASE
     */
    public enum StrategyType {
        /**
         * 增加数量，表示用户发生了履约行为
         */
        INCREASE,
        /**
         * 减少数量，表示取消用户的履约行为（例如用户取消购买、退货退款等）
         */
        DECREASE
    }
}
