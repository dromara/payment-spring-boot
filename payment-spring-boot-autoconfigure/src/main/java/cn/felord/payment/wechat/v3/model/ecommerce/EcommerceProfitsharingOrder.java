package cn.felord.payment.wechat.v3.model.ecommerce;

import lombok.Data;

import java.util.List;

@Data
public class EcommerceProfitsharingOrder {
	private String appid;
	private String subMchid;
	private String transactionId;
	private String outOrderNo;
	private List<Receiver> receivers;
	private Boolean finish;
}