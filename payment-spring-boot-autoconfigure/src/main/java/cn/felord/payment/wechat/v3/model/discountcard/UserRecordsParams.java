
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

}
