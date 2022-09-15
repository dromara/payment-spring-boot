package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

@Data
public class ApplymentParams {
	private String businessCode;
	private ContactInfo contactInfo;
	private SubjectInfo subjectInfo;
	private BusinessInfo businessInfo;
	private SettlementInfo settlementInfo;
	private BankAccountInfo bankAccountInfo;
}