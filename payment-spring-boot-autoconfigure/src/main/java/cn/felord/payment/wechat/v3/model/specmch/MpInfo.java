package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

import java.util.List;

@Data
public class MpInfo{
	private String mpAppid;
	private String mpSubAppid;
	private List<String> mpPics;
}