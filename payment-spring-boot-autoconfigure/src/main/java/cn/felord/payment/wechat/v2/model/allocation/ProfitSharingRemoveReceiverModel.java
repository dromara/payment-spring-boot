package cn.felord.payment.wechat.v2.model.allocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @since 1.0.10.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProfitSharingRemoveReceiverModel extends BaseProfitSharingReceiverModel {

    private Receiver receiver;

    @Data
    public static class Receiver {

        private Type type;
        private String account;

    }

}
