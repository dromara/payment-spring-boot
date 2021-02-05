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
import cn.felord.payment.wechat.v3.model.batchtransfer.CreateBatchTransferParams;
import cn.felord.payment.wechat.v3.model.batchtransfer.QueryBatchTransferDetailParams;
import cn.felord.payment.wechat.v3.model.batchtransfer.QueryBatchTransferParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 批量转账到零钱
 *
 * @author felord.cn
 * @since 1.0.6.RELEASE
 */
public class WechatBatchTransferApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatBatchTransferApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 发起批量转账API
     *
     * @param createBatchTransferParams the batchTransferParams
     * @return the wechat response entity
     * @since 1.0.6.RELEASE
     */
    public WechatResponseEntity<ObjectNode> batchTransfer(CreateBatchTransferParams createBatchTransferParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.BATCH_TRANSFER_REQ, createBatchTransferParams)
                .function(this::batchTransferFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> batchTransferFunction(WechatPayV3Type type, CreateBatchTransferParams createBatchTransferParams) {
        List<CreateBatchTransferParams.TransferDetailListItem> transferDetailList = createBatchTransferParams.getTransferDetailList();

        SignatureProvider signatureProvider = this.client().signatureProvider();
        final X509WechatCertificateInfo certificate = signatureProvider.getCertificate();
        List<CreateBatchTransferParams.TransferDetailListItem> encrypted = transferDetailList.stream()
                .peek(transferDetailListItem -> {
                    String userName = transferDetailListItem.getUserName();
                    X509Certificate x509Certificate = certificate.getX509Certificate();
                    String encryptedUserName = signatureProvider.encryptRequestMessage(userName, x509Certificate);
                    transferDetailListItem.setUserName(encryptedUserName);
                    String userIdCard = transferDetailListItem.getUserIdCard();
                    if (StringUtils.hasText(userIdCard)) {
                        String encryptedUserIdCard = signatureProvider.encryptRequestMessage(userIdCard, x509Certificate);
                        transferDetailListItem.setUserIdCard(encryptedUserIdCard);
                    }
                }).collect(Collectors.toList());

        createBatchTransferParams.setTransferDetailList(encrypted);
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Wechatpay-Serial", certificate.getWechatPaySerial());
        return Post(uri, createBatchTransferParams, httpHeaders);
    }

    /**
     * 微信批次单号查询批次单API
     *
     * @param queryBatchTransferParams the queryBatchTransferParams
     * @return the wechat response entity
     * @since 1.0.6.RELEASE
     */
    public WechatResponseEntity<ObjectNode> queryBatchByBatchId(QueryBatchTransferParams queryBatchTransferParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.BATCH_TRANSFER_BATCH_ID, queryBatchTransferParams)
                .function((type, params) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    queryParams.add("need_query_detail", params.getNeedQueryDetail().toString());
                    queryParams.add("offset", params.getOffset().toString());
                    queryParams.add("limit", params.getLimit().toString());
                    queryParams.add("detail_status", params.getDetailStatus().name());

                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build()
                            .expand(params.getCode())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 微信明细单号查询明细单API
     *
     * @param queryBatchTransferDetailParams the queryBatchTransferDetailParams
     * @return the wechat response entity
     * @since 1.0.6.RELEASE
     */
    public WechatResponseEntity<ObjectNode> queryBatchDetailByWechat(QueryBatchTransferDetailParams queryBatchTransferDetailParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.BATCH_TRANSFER_DETAIL_WECHAT, queryBatchTransferDetailParams)
                .function((type, params) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    queryParams.add("batch_id", params.getBatchIdOrOutBatchNo());
                    queryParams.add("detail_id", params.getDetailIdOrOutDetailNo());

                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(queryParams)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 微信批次单号查询批次单API
     *
     * @param queryBatchTransferParams the queryBatchTransferParams
     * @return the wechat response entity
     * @since 1.0.6.RELEASE
     */
    public WechatResponseEntity<ObjectNode> queryBatchByOutBatchNo(QueryBatchTransferParams queryBatchTransferParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.BATCH_TRANSFER_OUT_BATCH_NO, queryBatchTransferParams)
                .function((type, params) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    queryParams.add("need_query_detail", params.getNeedQueryDetail().toString());
                    queryParams.add("offset", params.getOffset().toString());
                    queryParams.add("limit", params.getLimit().toString());
                    queryParams.add("detail_status", params.getDetailStatus().name());

                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build()
                            .expand(params.getCode())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 商家明细单号查询明细单API
     *
     * @param queryBatchTransferDetailParams the queryBatchTransferDetailParams
     * @return the wechat response entity
     * @since 1.0.6.RELEASE
     */
    public WechatResponseEntity<ObjectNode> queryBatchDetailByMch(QueryBatchTransferDetailParams queryBatchTransferDetailParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.BATCH_TRANSFER_DETAIL_MCH, queryBatchTransferDetailParams)
                .function((type, params) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    queryParams.add("out_batch_no", params.getBatchIdOrOutBatchNo());
                    queryParams.add("out_detail_no", params.getDetailIdOrOutDetailNo());

                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(queryParams)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 转账电子回单申请受理API
     *
     * @param outBatchNo the outBatchNo
     * @return the wechat response entity
     * @since 1.0.6.RELEASE
     */
    public WechatResponseEntity<ObjectNode> receiptBill(String outBatchNo) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.BATCH_TRANSFER_BILL_RECEIPT, outBatchNo)
                .function((type, batchNo) -> {
                    Map<String, String> body = new HashMap<>(1);
                    body.put("out_batch_no", outBatchNo);
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, body);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询并下载转账电子回单API
     *
     * @param outBatchNo the outBatchNo
     * @return the wechat response entity
     * @since 1.0.6.RELEASE
     */
    public ResponseEntity<Resource> downloadBill(String outBatchNo) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.BATCH_TRANSFER_DOWNLOAD_BILL, outBatchNo)
                .function((type, batchNo) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(batchNo)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        String downloadUrl = wechatResponseEntity.getBody().get("download_url").asText();
        Assert.hasText(downloadUrl, "download url has no text");
        return this.billResource(downloadUrl);
    }
}
