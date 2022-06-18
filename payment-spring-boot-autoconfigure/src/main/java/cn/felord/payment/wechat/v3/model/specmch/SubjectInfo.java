package cn.felord.payment.wechat.v3.model.specmch;

import cn.felord.payment.wechat.enumeration.SubjectType;
import lombok.Data;

import java.util.List;

@Data
public class SubjectInfo{
	private SubjectType subjectType;
	private Boolean financeInstitution;
	private BusinessLicenseInfo businessLicenseInfo;
	private CertificateInfo certificateInfo;
	private String certificateLetterCopy;
	private FinanceInstitutionInfo financeInstitutionInfo;
	private IdentityInfo identityInfo;
	private List<UboInfoListItem> uboInfoList;
}