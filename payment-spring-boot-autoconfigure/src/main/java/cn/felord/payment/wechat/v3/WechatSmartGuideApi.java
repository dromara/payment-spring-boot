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

package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.smartguide.PartnerAssignParams;
import cn.felord.payment.wechat.v3.model.smartguide.PartnerSmartGuidesParams;
import cn.felord.payment.wechat.v3.model.smartguide.PartnerSmartGuidesQueryParams;
import cn.felord.payment.wechat.v3.model.smartguide.SmartGuidesModifyParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信V3服务商或者直连商户-经营能力-支付即服务
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
public class WechatSmartGuideApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatSmartGuideApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 服务人员注册API
     * <p>
     * 用于开发者为商户注册服务人员使用。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> register(PartnerSmartGuidesParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.SMART_GUIDES, params)
                .function((wechatPayV3Type, smartGuidesParams) -> {
                    SignatureProvider signatureProvider = this.client().signatureProvider();
                    X509WechatCertificateInfo certificate = signatureProvider.getCertificate(this.wechatMetaBean().getTenantId());
                    final X509Certificate x509Certificate = certificate.getX509Certificate();
                    smartGuidesParams.setName(signatureProvider.encryptRequestMessage(smartGuidesParams.getName(), x509Certificate));
                    smartGuidesParams.setMobile(signatureProvider.encryptRequestMessage(smartGuidesParams.getMobile(), x509Certificate));
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("Wechatpay-Serial", certificate.getWechatPaySerial());
                    return Post(uri, smartGuidesParams, httpHeaders);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 服务人员分配API
     * <p>
     * 用于开发者在顾客下单后为顾客分配服务人员使用。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> assign(PartnerAssignParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.SMART_GUIDES_ASSIGN, params)
                .function((wechatPayV3Type, assignParams) -> {

                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(assignParams.getGuideId())
                            .toUri();
                    Map<String, String> map = new HashMap<>(2);
                    map.put("sub_mchid", assignParams.getSubMchid());
                    map.put("out_trade_no", assignParams.getOutTradeNo());
                    return Post(uri, map);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 服务人员查询API
     * <p>
     * 用于商户开发者查询已注册的服务人员ID等信息。
     * <p>
     * 成功返回后请自行使用{@link SignatureProvider#decryptResponseMessage(String, String)}解密敏感字段。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> query(PartnerSmartGuidesQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.SMART_GUIDES_GET, params)
                .function((wechatPayV3Type, smartGuidesQueryParams) -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("Pay-TenantId",this.tenantId());
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    String subMchid = smartGuidesQueryParams.getSubMchid();
                    if (subMchid != null) {
                        queryParams.add("sub_mchid", subMchid);
                    }
                    queryParams.add("store_id", String.valueOf(smartGuidesQueryParams.getStoreId()));
                    String userid = smartGuidesQueryParams.getUserid();
                    if (userid != null) {
                        queryParams.add("userid", userid);
                    }
                    String mobile = smartGuidesQueryParams.getMobile();
                    if (mobile != null) {
                        SignatureProvider signatureProvider = this.client().signatureProvider();
                        X509WechatCertificateInfo certificate = signatureProvider.getCertificate(this.wechatMetaBean().getTenantId());
                        final X509Certificate x509Certificate = certificate.getX509Certificate();
                        queryParams.add("mobile", signatureProvider.encryptRequestMessage(mobile, x509Certificate));
                        httpHeaders.add("Wechatpay-Serial", certificate.getWechatPaySerial());
                    }
                    String workId = smartGuidesQueryParams.getWorkId();
                    if (workId != null) {
                        queryParams.add("work_id", workId);
                    }
                    Integer offset = smartGuidesQueryParams.getOffset();
                    if (offset != null) {
                        queryParams.add("offset", String.valueOf(offset));
                    }
                    Integer limit = smartGuidesQueryParams.getLimit();
                    if (limit != null) {
                        queryParams.add("limit", String.valueOf(limit));
                    }
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build()
                            .toUri();
                    return RequestEntity.get(uri).headers(httpHeaders)
                            .build();
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 服务人员信息更新API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> modify(SmartGuidesModifyParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.SMART_GUIDES_MODIFY, params)
                .function((wechatPayV3Type, smartGuidesParams) -> {
                    SignatureProvider signatureProvider = this.client().signatureProvider();
                    X509WechatCertificateInfo certificate = signatureProvider.getCertificate(this.wechatMetaBean().getTenantId());
                    final X509Certificate x509Certificate = certificate.getX509Certificate();
                    smartGuidesParams.setName(signatureProvider.encryptRequestMessage(smartGuidesParams.getName(), x509Certificate));
                    smartGuidesParams.setMobile(signatureProvider.encryptRequestMessage(smartGuidesParams.getMobile(), x509Certificate));
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("Wechatpay-Serial", certificate.getWechatPaySerial());
                    return Patch(uri, smartGuidesParams, httpHeaders);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

}
