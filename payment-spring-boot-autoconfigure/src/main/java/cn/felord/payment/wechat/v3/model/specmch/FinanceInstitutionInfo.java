package cn.felord.payment.wechat.v3.model.specmch;

import cn.felord.payment.wechat.enumeration.FinanceType;
import lombok.Data;

import java.util.List;

@Data
public class FinanceInstitutionInfo{
	private List<String> financeLicensePics;
	private FinanceType financeType;
}