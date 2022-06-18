package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BusinessLicenseInfo{
	private String licenseCopy;
	private String licenseNumber;
	private String merchantName;
	private String legalPerson;
	private String licenseAddress;
	private LocalDate periodBegin;
	private LocalDate periodEnd;
}