package cn.felord.payment.wechat.v3.model.discountcard;

import cn.felord.payment.wechat.enumeration.StrategyType;
import lombok.Data;

/**
 * The type Reward usage record.
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class RewardUsageRecord {

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
