package cn.felord.payment.wechat.v3.model.ecommerce;

import lombok.Data;

@Data
public class EcommerceSubsidiesCreateParams{
	private String subMchid;
	private String transactionId;
	private String outSubsidyNo;
	private Integer amount;
	private String description;
	private String refundId;
}