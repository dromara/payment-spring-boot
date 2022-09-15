package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

@Data
public class BusinessInfo{
	private String merchantShortname;
	private String servicePhone;
	private SalesInfo salesInfo;
}