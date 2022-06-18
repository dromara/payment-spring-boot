package cn.felord.payment.wechat.v3.model.specmch;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IdCardInfo{
	private String idCardCopy;
	private String idCardNational;
	private String idCardName;
	private String idCardNumber;
	private String idCardAddress;
	private LocalDate cardPeriodBegin;
	private LocalDate cardPeriodEnd;
}