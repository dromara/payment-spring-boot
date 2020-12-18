package cn.felord.payment.wechat.v3.model.discountcard;

import lombok.Data;

import java.util.function.Consumer;

/**
 * 先享卡回调消费复合消费器
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class DiscountCardConsumer {
   /**
    * The Accepted consume data consumer.
    */
   private Consumer<DiscountCardAcceptedConsumeData> acceptedConsumeDataConsumer;
   /**
    * The Agreement end consume data consumer.
    */
   private Consumer<DiscountCardAgreementEndConsumeData> agreementEndConsumeDataConsumer;
   /**
    * The Card user paid consume data consumer.
    */
   private Consumer<DiscountCardUserPaidConsumeData> cardUserPaidConsumeDataConsumer;
}
