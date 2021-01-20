package cn.felord.payment.wechat.v2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author felord.cn
 * @since 1.0.5.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TransferModel extends BaseModel{
	private String mchAppid;
	private String mchid;
	private String deviceInfo;
	private String partnerTradeNo;
	private String openid;
	private String checkName;
	private String reUserName;
	private String amount;
	private String desc;
	private String spbillCreateIp;
}
