package cn.felord.payment.wechat.v2.model.allocation;

import cn.felord.payment.wechat.v2.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @since 1.0.10.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProfitSharingQueryModel extends BaseModel {

    /**
     * 商户号.
     * <p>
     * 微信支付分配的商户号
     */
    private String mchId;

    /**
     * 微信订单号.
     * <p>
     * 微信支付订单号
     */
    private String transactionId;

    /**
     * 商户分账单号.
     * <p>
     * 查询分账结果，输入申请分账时的商户分账单号； 查询分账完结执行的结果，输入发起分账完结时的商户分账单号
     */
    private String outOrderNo;

}
