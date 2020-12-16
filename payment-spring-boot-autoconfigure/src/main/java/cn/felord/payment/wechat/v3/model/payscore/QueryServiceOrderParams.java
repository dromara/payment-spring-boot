package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 查询支付分订单请求参数
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class QueryServiceOrderParams {
    /**
     * 商户服务订单号，同{@link QueryServiceOrderParams#queryId} 二选一，而且不能同时为null
     */
    private String outOrderNo;
    /**
     * 回跳查询ID，同{@link QueryServiceOrderParams#outOrderNo} 二选一，而且不能同时为null
     */
    private String queryId;
    /**
     * 服务ID，必填
     */
    private String serviceId;
}
