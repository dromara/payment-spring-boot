package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 取消支付分订单请求参数
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class CancelServiceOrderParams {
    /**
     * 商户服务订单号，必填
     */
    private String outOrderNo;
    /**
     * 与传入的商户号建立了支付绑定关系的appid，必填
     */
    private String appid;
    /**
     * 服务ID，必填
     */
    private String serviceId;
    /**
     * 取消原因，最长50个字符，必填
     */
    private String reason;
}
