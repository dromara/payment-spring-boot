package cn.felord.payment.wechat.v3.model.discountcard;

import cn.felord.payment.wechat.v3.model.payscore.PayScoreUserConfirmConsumeData;
import cn.felord.payment.wechat.v3.model.payscore.PayScoreUserPaidConsumeData;
import lombok.Data;

import java.util.function.Consumer;

/**
 * 支付分回调复合消费器
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class PayScoreConsumer {
    /**
     * The Confirm consume data consumer.
     */
    private Consumer<PayScoreUserConfirmConsumeData> confirmConsumeDataConsumer;
    /**
     * The Paid consume data consumer.
     */
    private Consumer<PayScoreUserPaidConsumeData> paidConsumeDataConsumer;
}
