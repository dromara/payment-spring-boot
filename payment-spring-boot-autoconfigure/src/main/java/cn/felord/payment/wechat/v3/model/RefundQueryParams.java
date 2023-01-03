package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 微信支付服务商退款查询API请求参数.
 *
 * @author zhongying
 * @since 1.0.15-SNAPSHOT
 */
@Data
public class RefundQueryParams {

    /**
     * 子商户id
     */
    private String subMchid;

    /**
     * 退款单号
     */
    private String refundOrderNo;

}
