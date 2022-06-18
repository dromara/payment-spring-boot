package cn.felord.payment.wechat.v3.model.specmch;

import cn.felord.payment.wechat.enumeration.IdDocType;
import cn.felord.payment.wechat.enumeration.ContactType;
import lombok.Data;

@Data
public class IdentityInfo{
	private ContactType idHolderType;
	private IdDocType idDocType;
	private String authorizeLetterCopy;
	private IdCardInfo idCardInfo;
	private IdDocInfo idDocInfo;
	private Boolean owner;
}