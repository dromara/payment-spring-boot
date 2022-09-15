package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

import java.util.List;

@Data
public class WeworkInfo{
	private String corpId;
	private String subCorpId;
	private List<String> weworkPics;
}