package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

import java.util.List;

@Data
public class AppInfo{
	private String appAppid;
	private String appSubAppid;
	private List<String> appPics;
}