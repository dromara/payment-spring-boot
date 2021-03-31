package cn.felord.payment.wechat.v2.model.allocation;

import cn.felord.payment.wechat.v2.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @since 1.0.10.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProfitSharingReturnModel extends BaseModel {

    /**
     * 商户号.
     * <p>
     * 微信支付分配的商户号
     */
    private String mchId;

    /**
     * 公众账号ID.
     * <p>
     * 微信分配的公众账号ID
     */
    private String appid;
    private String orderId;
    private String outOrderNo;
    private String outReturnNo;
    private String returnAccountType;
    private String returnAccount;
    private String returnAmount;
    private String description;

}
