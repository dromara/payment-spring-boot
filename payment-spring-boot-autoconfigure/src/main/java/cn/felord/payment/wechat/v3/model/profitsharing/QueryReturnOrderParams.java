package cn.felord.payment.wechat.v3.model.profitsharing;

import lombok.Data;

/**
 * 直连商户-查询分账回退结果API-请求参数
 *
 * @author felord.cn
 * @since 1.0.11.RELEASE
 */
@Data
public class QueryReturnOrderParams {
    /**
     * 商户回退单号，必填
     */
    private String outReturnNo;
    /**
     * 商户分账单号，必填
     */
    private String outOrderNo;
}
