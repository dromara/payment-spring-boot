package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

import java.util.List;

@Data
public class SalesInfo{
	private List<String> salesScenesType;
	private BizStoreInfo bizStoreInfo;
	private MpInfo mpInfo;
	private MiniProgramInfo miniProgramInfo;
	private AppInfo appInfo;
	private WebInfo webInfo;
	private WeworkInfo weworkInfo;
}