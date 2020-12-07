package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * @author Dax
 * @since 17:42
 */
@Data
public class TransactionQueryParams {
private String mchId;
private String transactionIdOrOutTradeNo;
}
