package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

import java.util.List;

@Data
public class BizStoreInfo{
	private String bizStoreName;
	private String bizAddressCode;
	private String bizStoreAddress;
	private List<String> storeEntrancePic;
	private List<String> indoorPic;
	private String bizSubAppid;
}