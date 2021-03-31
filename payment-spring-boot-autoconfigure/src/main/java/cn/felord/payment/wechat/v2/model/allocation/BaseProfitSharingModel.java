package cn.felord.payment.wechat.v2.model.allocation;

import cn.felord.payment.wechat.v2.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @since 1.0.10.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseProfitSharingModel extends BaseModel {


    /**
     * 商户号.
     * <p>
     * 微信支付分配的商户号
     */
    private String mchId;
    private String appid;
    private String transactionId;
    private String outOrderNo;

}
