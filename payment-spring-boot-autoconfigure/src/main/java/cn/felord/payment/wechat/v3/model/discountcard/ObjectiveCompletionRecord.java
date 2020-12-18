package cn.felord.payment.wechat.v3.model.discountcard;

import cn.felord.payment.wechat.enumeration.StrategyType;
import lombok.Data;

/**
 * The type Objective completion record.
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class ObjectiveCompletionRecord {

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
