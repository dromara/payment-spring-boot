package cn.felord.payment.wechat.v2.model.allocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @since 1.0.10.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProfitSharingRemoveReceiverSModel extends BaseProfitSharingReceiverModel {

    private String receiver;

}
