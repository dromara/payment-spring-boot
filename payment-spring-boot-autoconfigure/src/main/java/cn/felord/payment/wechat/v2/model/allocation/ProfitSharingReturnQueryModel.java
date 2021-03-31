package cn.felord.payment.wechat.v2.model.allocation;

import cn.felord.payment.wechat.v2.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @since 1.0.10.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProfitSharingReturnQueryModel extends BaseModel {

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

    /**
     * 微信分账订单号.
     * <p>
     * 原发起分账请求时，微信返回的微信分账单号，与商户分账单号一一对应。
     * 微信分账单号与商户分账单号二选一填写
     */
    private String orderId;

    /**
     * 商户分账单号.
     * <p>
     * 原发起分账请求时使用的商户系统内部的分账单号。
     * 微信分账单号与商户分账单号二选一填写
     */
    private String outOrderNo;

    /**
     * 商户回退单号.
     * <p>
     * 调用回退接口提供的商户系统内部的回退单号
     */
    private String outReturnNo;

}
