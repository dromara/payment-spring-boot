/*
 *  Copyright 2019-2022 felord.cn
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *  Website:
 *       https://felord.cn
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.felord.payment.wechat.v3.model.ecommerce;

import cn.felord.payment.wechat.enumeration.ContactType;
import cn.felord.payment.wechat.enumeration.IdDocType;
import cn.felord.payment.wechat.v3.model.specmch.FinanceInstitutionInfo;
import lombok.Data;

import java.util.List;

/**
 * 二级商户进件申请API请求参数
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
@Data
public class EcommerceApplymentParams{
	private String outRequestNo;
	private String organizationType;
	private Boolean financeInstitution;
	private EcommerceBusinessLicenseInfo businessLicenseInfo;
	private FinanceInstitutionInfo financeInstitutionInfo;
	private ContactType idHolderType;
	private IdDocType idDocType;
	private String authorizeLetterCopy;
	private EcommerceIdCardInfo idCardInfo;
	private EcommerceIdDocInfo idDocInfo;
	private Boolean owner;
	private List<UboInfoListItem> uboInfoList;
	private EcommerceAccountInfo accountInfo;
	private EcommerceContactInfo contactInfo;
	private SalesSceneInfo salesSceneInfo;
	private EcommerceSettlementInfo settlementInfo;
	private String merchantShortname;
	private String qualifications;
	private String businessAdditionPics;
	private String businessAdditionDesc;

}