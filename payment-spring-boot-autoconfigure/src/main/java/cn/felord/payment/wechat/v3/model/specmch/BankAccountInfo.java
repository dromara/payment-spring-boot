package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

@Data
public class BankAccountInfo{
	private String bankAccountType;
	private String accountName;
	private String accountBank;
	private String bankAddressCode;
	private String bankBranchId;
	private String bankName;
	private String accountNumber;
	private AccountCertInfo accountCertInfo;
}