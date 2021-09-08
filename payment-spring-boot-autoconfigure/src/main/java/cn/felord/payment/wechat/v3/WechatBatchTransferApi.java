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

import cn.felord.payment.wechat.enumeration.FundFlowAccountType;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.batchtransfer.*;
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
import java.util.Optional;
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
        X509WechatCertificateInfo certificate = signatureProvider.getCertificate(this.wechatMetaBean().getTenantId());
        final X509Certificate x509Certificate = certificate.getX509Certificate();
        List<CreateBatchTransferParams.TransferDetailListItem> encrypted = transferDetailList.stream()
                .peek(transferDetailListItem -> {
                    String userName = transferDetailListItem.getUserName();
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
                    Map<String, String> pathParams = new HashMap<>(2);
                    pathParams.put("batch_id", params.getBatchIdOrOutBatchNo());
                    pathParams.put("detail_id", params.getDetailIdOrOutDetailNo());

                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(pathParams)
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
                    Map<String, String> pathParams = new HashMap<>(2);
                    pathParams.put("batch_id", params.getBatchIdOrOutBatchNo());
                    pathParams.put("detail_id", params.getDetailIdOrOutDetailNo());

                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(pathParams)
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

    /**
     * 转账明细电子回单受理API
     * <p>
     * 受理转账明细电子回单接口，商户通过该接口可以申请受理转账明细单电子回单服务。
     * <p>
     * 返回的下载链接可调用{@link #downloadBillResponse(String, String)}下载文件
     *
     * @param params the params
     * @return the wechat response entity
     * @since 1.0.11.RELEASE
     */
    public WechatResponseEntity<ObjectNode> transferElectronic(TransferDetailElectronicParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.BATCH_TRANSFER_ELECTRONIC, params)
                .function((type, transferDetailElectronic) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, transferDetailElectronic);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询转账明细电子回单受理结果API
     * <p>
     * 查询转账明细电子回单受理结果接口，商户通过该接口可以查询电子回单受理进度信息，
     * 包括电子回单据信息，电子回单文件的hash值，电子回单文件的下载地址等。
     * <p>
     * 返回的下载链接可调用{@link #downloadBillResponse(String, String)}下载文件
     *
     * @param params the params
     * @return the wechat response entity
     * @since 1.0.11.RELEASE
     */
    public WechatResponseEntity<ObjectNode> queryTransferElectronicResult(TransferDetailElectronicParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.BATCH_TRANSFER_ELECTRONIC_DETAIL, params)
                .function((type, transferDetailElectronic) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    queryParams.add("accept_type", transferDetailElectronic.getAcceptType().name());
                    queryParams.add("out_batch_no", transferDetailElectronic.getOutBatchNo());
                    queryParams.add("out_detail_no", transferDetailElectronic.getOutDetailNo());

                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build()
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询账户实时余额API
     * <p>
     * 商户通过此接口可以查询本商户号的账号余额情况。
     *
     * @param accountType the account type
     * @return the wechat response entity
     * @since 1.0.11.RELEASE
     */
    public WechatResponseEntity<ObjectNode> queryFundBalance(FundFlowAccountType accountType) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.BATCH_TRANSFER_FUND_BALANCE, accountType)
                .function((type, flowAccountType) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(flowAccountType.name())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询账户日终余额API
     * <p>
     * 通过此接口可以查询本商户号指定日期当天24点的账户余额。
     *
     * @param queryDayBalanceParams the transfer day balance params
     * @return the wechat response entity
     * @since 1.0.11.RELEASE
     */
    public WechatResponseEntity<ObjectNode> queryDayFundBalance(QueryDayBalanceParams queryDayBalanceParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.BATCH_TRANSFER_FUND_DAY_BALANCE, queryDayBalanceParams)
                .function((type, params) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParam("date", params.getDate().toString())
                            .build()
                            .expand(params.getAccountType().name())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 商户银行来账查询API
     * <p>
     * 商户通过本接口查询指定日期内本商户银行来账记录列表。
     * 列表内包含本商户银行来账相关的业务单号、金额、完成时间等信息，用于查询和核对。
     * <p>
     * 注意：
     * <ol>
     *  <li>如需检索，请在前端缓存所有银行来账记录数据并自行完成检索功能；</li>
     *  <li>调用该接口前，商户需提前开通“来账识别”产品权限；</li>
     *  <li>本接口对可查询的商户范围有所规定，仅支持对本商户进行查询；</li>
     *  <li>本接口仅提供近90天内的银行来账记录查询，且一次只能查询一天，商户需确保查询记录日期在此范围内；</li>
     *  <li>本接口返回的记录字段后续可能会有所扩充，商户需做好接口兼容准备；</li>
     *  <li>单商户单接口最大请求频率不超过50TPS。</li>
     * </ol>
     *
     * @param queryIncomeRecordParams the transfer day balance params
     * @return the wechat response entity
     * @since 1.0.11.RELEASE
     */
    public WechatResponseEntity<ObjectNode> queryIncomeRecords(QueryIncomeRecordParams queryIncomeRecordParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.BATCH_TRANSFER_FUND_INCOME_RECORDS, queryIncomeRecordParams)
                .function((type, params) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    queryParams.add("account_type", params.getAccountType().name());
                    queryParams.add("date", params.getDate().toString());
                    queryParams.add("offset", Optional.ofNullable(params.getOffset()).orElse(0).toString());
                    queryParams.add("limit", params.getLimit().toString());
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build()
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }
}
