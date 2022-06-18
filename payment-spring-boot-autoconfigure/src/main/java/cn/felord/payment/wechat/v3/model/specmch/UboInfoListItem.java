package cn.felord.payment.wechat.v3.model.specmch;

import cn.felord.payment.wechat.enumeration.IdDocType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UboInfoListItem{
	private IdDocType uboIdDocType;
	private String uboIdDocCopy;
	private String uboIdDocCopyBack;
	private String uboIdDocName;
	private String uboIdDocNumber;
	private String uboIdDocAddress;
	private LocalDate uboPeriodBegin;
	private LocalDate uboPeriodEnd;
}