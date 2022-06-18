/*
 *  Copyright 2019-2021 felord.cn
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

package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.specmch.ApplymentParams;
import cn.felord.payment.wechat.v3.model.specmch.BankAccountInfo;
import cn.felord.payment.wechat.v3.model.specmch.ContactInfo;
import cn.felord.payment.wechat.v3.model.specmch.SubMchModifyParams;
import cn.felord.payment.wechat.v3.model.specmch.UboInfoListItem;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * 微信V3服务商-商户进件-特约商户进件
 * <p>
 * 参见<a href="https://pay.weixin.qq.com/wiki/doc/apiv3_partner/open/pay/chapter7_1_1.shtml">产品介绍</a>
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
public class WechatPartnerSpecialMchApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatPartnerSpecialMchApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 提交申请单API
     * <p>
     * 服务商（银行、支付机构、电商平台不可用）使用该接口提交商家资料，帮助商家入驻成为微信支付的特约商户。
     * 流程指引请看 <a href="https://pay.weixin.qq.com/wiki/doc/apiv3_partner/open/pay/chapter7_1_2.shtml#part-6">特约商户进件流程</a>
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> apply(ApplymentParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.SPEC_MCH_APPLY_PARTNER, params)
                .function((wechatPayV3Type, applymentParams) -> {
                    SignatureProvider signatureProvider = this.client().signatureProvider();
                    X509WechatCertificateInfo certificate = signatureProvider.getCertificate(this.wechatMetaBean().getTenantId());
                    final X509Certificate x509Certificate = certificate.getX509Certificate();
                    ApplymentParams applyRequestParams = this.convert(applymentParams, signatureProvider, x509Certificate);
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("Wechatpay-Serial", certificate.getWechatPaySerial());
                    return Post(uri, applyRequestParams, httpHeaders);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 通过业务申请编号查询申请状态
     *
     * @param businessCode the business code
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryByBusinessCode(String businessCode) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.SPEC_MCH_APPLY_QUERY_BUSINESS_CODE, businessCode)
                .function((type, id) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(id)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 通过申请单号查询申请状态
     *
     * @param applymentId the applyment id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryByApplymentId(String applymentId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.SPEC_MCH_APPLY_QUERY_APPLYMENT_ID, applymentId)
                .function((type, id) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(id)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 修改结算账号API
     * <p>
     * 服务商/电商平台（不包括支付机构、银行），可使用本接口，修改其进件且已签约特约商户/二级商户的结算银行账户。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> modify(SubMchModifyParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.SPEC_MCH_SUB_MODIFY, params)
                .function((type, subMchModifyParams) -> {
                    SignatureProvider signatureProvider = this.client().signatureProvider();
                    X509WechatCertificateInfo certificate = signatureProvider.getCertificate(this.wechatMetaBean().getTenantId());
                    final X509Certificate x509Certificate = certificate.getX509Certificate();
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(subMchModifyParams.getSubMchid())
                            .toUri();

                    subMchModifyParams.setSubMchid(null);
                    subMchModifyParams.setAccountNumber(signatureProvider.encryptRequestMessage(subMchModifyParams.getAccountNumber(), x509Certificate));
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("Wechatpay-Serial", certificate.getWechatPaySerial());
                    return Post(uri, subMchModifyParams, httpHeaders);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询结算账户API
     * <p>
     * 服务商/电商平台（不包括支付机构、银行），可使用本接口，查询其进件且已签约特约商户/二级商户的结算账户信息（敏感信息掩码）。
     * 该接口可用于核实是否成功修改结算账户信息、及查询系统汇款验证结果。
     *
     * @param subMchid the sub mchid
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> querySettlement(String subMchid) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.SPEC_MCH_SUB_SETTLEMENT, subMchid)
                .function((type, id) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(id)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private ApplymentParams convert(ApplymentParams applymentParams, SignatureProvider signatureProvider, final X509Certificate x509Certificate) {

        ContactInfo contactInfo = applymentParams.getContactInfo();
        contactInfo.setContactName(signatureProvider.encryptRequestMessage(contactInfo.getContactName(), x509Certificate));
        String contactIdNumber = contactInfo.getContactIdNumber();
        if (contactIdNumber != null) {
            contactInfo.setContactIdNumber(signatureProvider.encryptRequestMessage(contactIdNumber, x509Certificate));
        }
        String openid = contactInfo.getOpenid();
        if (openid != null) {
            contactInfo.setOpenid(signatureProvider.encryptRequestMessage(openid, x509Certificate));
        }
        contactInfo.setMobilePhone(signatureProvider.encryptRequestMessage(contactInfo.getMobilePhone(), x509Certificate));
        contactInfo.setContactEmail(signatureProvider.encryptRequestMessage(contactInfo.getContactEmail(), x509Certificate));

        List<UboInfoListItem> uboInfoList = applymentParams.getSubjectInfo().getUboInfoList();
        if (!CollectionUtils.isEmpty(uboInfoList)) {
            uboInfoList.forEach(uboInfoListItem -> {
                uboInfoListItem.setUboIdDocName(signatureProvider.encryptRequestMessage(uboInfoListItem.getUboIdDocName(), x509Certificate));
                uboInfoListItem.setUboIdDocNumber(signatureProvider.encryptRequestMessage(uboInfoListItem.getUboIdDocNumber(), x509Certificate));
                uboInfoListItem.setUboIdDocAddress(signatureProvider.encryptRequestMessage(uboInfoListItem.getUboIdDocAddress(), x509Certificate));
            });
        }

        BankAccountInfo bankAccountInfo = applymentParams.getBankAccountInfo();

        bankAccountInfo.setAccountName(signatureProvider.encryptRequestMessage(bankAccountInfo.getAccountName(), x509Certificate));
        bankAccountInfo.setAccountNumber(signatureProvider.encryptRequestMessage(bankAccountInfo.getAccountNumber(), x509Certificate));

        return applymentParams;
    }

}
