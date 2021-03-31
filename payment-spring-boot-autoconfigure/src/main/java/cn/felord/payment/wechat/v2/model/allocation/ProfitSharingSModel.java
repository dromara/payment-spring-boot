package cn.felord.payment.wechat.v2.model.allocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @since 1.0.10.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProfitSharingSModel extends BaseProfitSharingModel {

    /**
     * 分账接收方列表，不超过50个json对象，不能设置分账方作为分账接受方。
     */
    private String receivers;

}
