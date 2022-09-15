package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

import java.util.List;

@Data
public class AccountCertInfo{
	private String settlementCertPic;
	private String relationCertPic;
	private List<String> otherCertPics;
}