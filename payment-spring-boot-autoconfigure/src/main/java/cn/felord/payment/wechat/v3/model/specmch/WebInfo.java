package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

@Data
public class WebInfo{
	private String domain;
	private String webAuthorisation;
	private String webAppid;
}