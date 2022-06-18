package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

import java.util.List;

@Data
public class SettlementInfo{
	private String settlementId;
	private String qualificationType;
	private List<String> qualifications;
	private String activitiesId;
	private String activitiesRate;
	private List<String> activitiesAdditions;
}