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

package cn.felord.payment.wechat.v3.ecommerce;

import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.AbstractApi;
import cn.felord.payment.wechat.v3.SignatureProvider;
import cn.felord.payment.wechat.v3.WechatPartnerSpecialMchApi;
import cn.felord.payment.wechat.v3.WechatPayClient;
import cn.felord.payment.wechat.v3.WechatResponseEntity;
import cn.felord.payment.wechat.v3.X509WechatCertificateInfo;
import cn.felord.payment.wechat.v3.model.ecommerce.AccountInfo;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceApplymentParams;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceContactInfo;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceIdCardInfo;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceIdDocInfo;
import cn.felord.payment.wechat.v3.model.ecommerce.UboInfo;
import cn.felord.payment.wechat.v3.model.specmch.SubMchModifyParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.cert.X509Certificate;

/**
 * 电商收付通-商户进件
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
class ApplymentApi extends AbstractApi {

    private final WechatPartnerSpecialMchApi wechatPartnerSpecialMchApi;

    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    ApplymentApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
        this.wechatPartnerSpecialMchApi = new WechatPartnerSpecialMchApi(wechatPayClient, tenantId);
    }

    /**
     * 二级商户进件申请API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> apply(EcommerceApplymentParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_APPLYMENT, params)
                .function((wechatPayV3Type, applymentParams) -> {
                    SignatureProvider signatureProvider = this.client().signatureProvider();
                    X509WechatCertificateInfo certificate = signatureProvider.getCertificate(this.wechatMetaBean().getTenantId());
                    final X509Certificate x509Certificate = certificate.getX509Certificate();
                    EcommerceApplymentParams applyRequestParams = this.convert(applymentParams, signatureProvider, x509Certificate);
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
     * 通过申请单ID查询申请状态API
     *
     * @param applymentId the applyment id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryByApplymentId(String applymentId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_APPLYMENT_ID, applymentId)
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
     * 通过业务申请编号查询申请状态API
     *
     * @param outRequestNo the outRequestNo
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryByBusinessCode(String outRequestNo) {

        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_APPLYMENT_OUT_REQUEST_NO, outRequestNo)
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
     *
     * @param params the params
     * @return wechat response entity
     * @see WechatPartnerSpecialMchApi#modify(SubMchModifyParams)
     */
    public WechatResponseEntity<ObjectNode> modify(SubMchModifyParams params) {
        return this.wechatPartnerSpecialMchApi.modify(params);
    }

    /**
     * 查询结算账户API
     * <p>
     * 服务商/电商平台（不包括支付机构、银行），可使用本接口，查询其进件且已签约特约商户/二级商户的结算账户信息（敏感信息掩码）。
     * 该接口可用于核实是否成功修改结算账户信息、及查询系统汇款验证结果。
     *
     * @param subMchid the sub mchid
     * @return the wechat response entity
     * @see WechatPartnerSpecialMchApi#querySettlement(String)
     */
    public WechatResponseEntity<ObjectNode> querySettlement(String subMchid) {
        return this.wechatPartnerSpecialMchApi.querySettlement(subMchid);
    }

    private EcommerceApplymentParams convert(EcommerceApplymentParams applymentParams, SignatureProvider signatureProvider, X509Certificate x509Certificate) {
        EcommerceIdCardInfo idCardInfo = applymentParams.getIdCardInfo();
        if (idCardInfo != null) {
            idCardInfo.setIdCardName(signatureProvider.encryptRequestMessage(idCardInfo.getIdCardName(), x509Certificate));
            idCardInfo.setIdCardNumber(signatureProvider.encryptRequestMessage(idCardInfo.getIdCardNumber(), x509Certificate));
        }
        EcommerceIdDocInfo idDocInfo = applymentParams.getIdDocInfo();
        if (idDocInfo != null) {
            idDocInfo.setIdDocName(signatureProvider.encryptRequestMessage(idDocInfo.getIdDocName(), x509Certificate));
            idDocInfo.setIdDocNumber(signatureProvider.encryptRequestMessage(idDocInfo.getIdDocNumber(), x509Certificate));
        }
        UboInfo uboInfo = applymentParams.getUboInfo();
        if (uboInfo != null) {
            UboInfo.IdCardInfo cardInfo = uboInfo.getIdCardInfo();
            if (cardInfo != null) {
                cardInfo.setIdCardName(signatureProvider.encryptRequestMessage(cardInfo.getIdCardName(), x509Certificate));
                cardInfo.setIdCardNumber(signatureProvider.encryptRequestMessage(cardInfo.getIdCardNumber(), x509Certificate));
            }
            UboInfo.IdDocInfo docInfo = uboInfo.getIdDocInfo();
            if (docInfo != null) {
                docInfo.setIdDocName(signatureProvider.encryptRequestMessage(docInfo.getIdDocName(), x509Certificate));
                docInfo.setIdDocNumber(signatureProvider.encryptRequestMessage(docInfo.getIdDocNumber(), x509Certificate));
            }
        }
        AccountInfo accountInfo = applymentParams.getAccountInfo();
        if (accountInfo != null) {
            accountInfo.setAccountName(signatureProvider.encryptRequestMessage(accountInfo.getAccountName(), x509Certificate));
            accountInfo.setAccountNumber(signatureProvider.encryptRequestMessage(accountInfo.getAccountNumber(), x509Certificate));
        }
        EcommerceContactInfo contactInfo = applymentParams.getContactInfo();
        contactInfo.setContactName(signatureProvider.encryptRequestMessage(contactInfo.getContactName(), x509Certificate));
        contactInfo.setContactIdCardNumber(signatureProvider.encryptRequestMessage(contactInfo.getContactIdCardNumber(), x509Certificate));
        String contactEmail = contactInfo.getContactEmail();
        if (contactEmail != null) {
            contactInfo.setContactEmail(signatureProvider.encryptRequestMessage(contactEmail, x509Certificate));
        }
        return applymentParams;
    }

}
