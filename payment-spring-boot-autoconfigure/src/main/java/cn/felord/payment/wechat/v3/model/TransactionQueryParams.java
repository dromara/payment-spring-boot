package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class TransactionQueryParams {
private String mchId;
private String transactionIdOrOutTradeNo;
}
