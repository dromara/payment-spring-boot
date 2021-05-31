package cn.felord.payment.wechat.v3.model.profitsharing;

import lombok.Data;

/**
 * 查询分账结果API-请求参数
 *
 * @author felord.cn
 * @since 1.0.11.RELEASE
 */
@Data
public class QueryOrderParams {
    /**
     * 商户分账单号，必填
     */
    private String outOrderNo;
    /**
     * 微信订单号，必填
     */
    private String transactionId;
}
