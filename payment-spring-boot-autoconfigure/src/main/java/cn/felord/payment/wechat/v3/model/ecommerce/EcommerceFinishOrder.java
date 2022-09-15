package cn.felord.payment.wechat.v3.model.ecommerce;

import lombok.Data;

@Data
public class EcommerceFinishOrder{
	private String subMchid;
	private String transactionId;
	private String outOrderNo;
	private String description;
}