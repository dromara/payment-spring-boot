package cn.felord.payment.wechat.v3.model.specmch;

import cn.felord.payment.wechat.enumeration.IdDocType;
import cn.felord.payment.wechat.enumeration.ContactType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ContactInfo{
	private ContactType contactType;
	private String contactName;
	private IdDocType contactIdDocType;
	private String contactIdNumber;
	private String contactIdDocCopy;
	private String contactIdDocCopyBack;
	private LocalDate contactPeriodBegin;
	private String contactPeriodEnd;
	private String businessAuthorizationLetter;
	private String openid;
	private String mobilePhone;
	private String contactEmail;
}