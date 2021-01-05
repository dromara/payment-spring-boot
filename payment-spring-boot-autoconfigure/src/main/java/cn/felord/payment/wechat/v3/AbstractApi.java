/*
 *
 *  Copyright 2019-2020 felord.cn
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
 *
 */
package cn.felord.payment.wechat.v3;

import cn.felord.payment.PayException;
import cn.felord.payment.wechat.enumeration.*;
import cn.felord.payment.wechat.v3.model.FundFlowBillParams;
import cn.felord.payment.wechat.v3.model.TradeBillParams;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.RequestEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * The type Abstract api.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public abstract class AbstractApi {
    /**
     * The Mapper.
     */
    private final ObjectMapper mapper;
    /**
     * The Wechat pay client.
     */
    private final WechatPayClient wechatPayClient;
    /**
     * The Tenant id.
     */
    private final String tenantId;


    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public AbstractApi(WechatPayClient wechatPayClient, String tenantId) {
        this.mapper = new ObjectMapper();
        applyObjectMapper(this.mapper);
        this.wechatPayClient = wechatPayClient;
        Assert.hasText(tenantId, "tenantId is required");
        if (!container().getTenantIds().contains(tenantId)) {
            throw new PayException("tenantId is not in wechatMetaContainer");
        }
        this.tenantId = tenantId;
    }

    /**
     * Apply object mapper.
     *
     * @param mapper the mapper
     */
    private void applyObjectMapper(ObjectMapper mapper) {
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule module = new JavaTimeModule();
        mapper.registerModule(module);
    }


    /**
     * Gets mapper.
     *
     * @return the mapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * Client wechat pay client.
     *
     * @return the wechat pay client
     */
    public WechatPayClient client() {
        return wechatPayClient;
    }

    /**
     * Tenant id string.
     *
     * @return the string
     */
    public String tenantId() {
        return tenantId;
    }

    /**
     * Container wechat meta container.
     *
     * @return the wechat meta container
     */
    public WechatMetaContainer container() {
        return wechatPayClient.signatureProvider().wechatMetaContainer();
    }

    /**
     * Wechat meta bean wechat meta bean.
     *
     * @return the wechat meta bean
     */
    public WechatMetaBean wechatMetaBean() {
        return container().getWechatMeta(tenantId);
    }

    /**
     * 构建Post请求对象.
     *
     * @param uri    the uri
     * @param params the params
     * @return the request entity
     */
    protected RequestEntity<?> Post(URI uri, Object params) {
        try {
            return RequestEntity.post(uri).header("Pay-TenantId", tenantId)
                    .body(mapper.writeValueAsString(params));
        } catch (JsonProcessingException e) {
            throw new PayException("wechat app pay json failed");
        }
    }

    /**
     * 构建Get请求对象.
     *
     * @param uri the uri
     * @return the request entity
     */
    protected RequestEntity<?> Get(URI uri) {
        return RequestEntity.get(uri).header("Pay-TenantId", tenantId)
                .build();
    }


    /**
     * 对账单下载。
     *
     * @param link the link
     * @return 对账单内容，有可能为空字符 “”
     */
    protected String billDownload(String link) {
        return this.client().withType(WechatPayV3Type.FILE_DOWNLOAD, link)
                .function((type, downloadUrl) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(downloadUrl)
                            .build()
                            .toUri();
                    return Get(uri);
                })
                .download();
    }


    /**
     * 申请交易账单API
     * <p>
     * 微信支付按天提供交易账单文件，商户可以通过该接口获取账单文件的下载地址。文件内包含交易相关的金额、时间、营销等信息，供商户核对订单、退款、银行到账等情况。
     * <p>
     * 注意：
     * <ul>
     *     <li>微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致；</li>
     *     <li>对账单中涉及金额的字段单位为“元”；</li>
     *     <li>对账单接口只能下载三个月以内的账单。</li>
     *     <li>小微商户不单独提供对账单下载，如有需要，可在调取“下载对账单”API接口时不传sub_mch_id，获取服务商下全量电商二级商户（包括小微商户和非小微商户）的对账单。</li>
     * </ul>
     *
     * @param tradeBillParams tradeBillParams
     * @since 1.0.3.RELEASE
     */
    public final void downloadTradeBill(TradeBillParams tradeBillParams) {
        this.client().withType(WechatPayV3Type.TRADEBILL, tradeBillParams)
                .function((wechatPayV3Type, params) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    LocalDate billDate = params.getBillDate();
                    queryParams.add("bill_date", billDate.format(DateTimeFormatter.ISO_DATE));
                    String subMchid = params.getSubMchid();

                    if (StringUtils.hasText(subMchid)) {
                        queryParams.add("sub_mchid", subMchid);
                    }

                    TradeBillType tradeBillType = Optional.ofNullable(params.getBillType())
                            .orElse(TradeBillType.ALL);
                    queryParams.add("bill_type", tradeBillType.name());
                    TarType tarType = params.getTarType();
                    if (Objects.nonNull(tarType)) {
                        queryParams.add("tar_type", tarType.name());
                    }
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build().toUri();
                    return Get(uri);
                })
                .consumer(response -> this.billDownload(Objects.requireNonNull(response.getBody()).get("download_url").asText()))
                .request();
    }

    /**
     * 申请资金账单API
     * <p>
     * 微信支付按天提供微信支付账户的资金流水账单文件，商户可以通过该接口获取账单文件的下载地址。文件内包含该账户资金操作相关的业务单号、收支金额、记账时间等信息，供商户进行核对。
     * <p>
     * 注意：
     * <ul>
     *     <li>资金账单中的数据反映的是商户微信支付账户资金变动情况；</li>
     *     <li>对账单中涉及金额的字段单位为“元”。</li>
     * </ul>
     *
     * @param fundFlowBillParams fundFlowBillParams
     * @since 1.0.3.RELEASE
     */
    public final void downloadFundFlowBill(FundFlowBillParams fundFlowBillParams) {
        this.client().withType(WechatPayV3Type.FUNDFLOWBILL, fundFlowBillParams)
                .function((wechatPayV3Type, params) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    LocalDate billDate = params.getBillDate();
                    queryParams.add("bill_date", billDate.format(DateTimeFormatter.ISO_DATE));

                    FundFlowAccountType accountType = Optional.ofNullable(params.getAccountType())
                            .orElse(FundFlowAccountType.BASIC);
                    queryParams.add("account_type", accountType.name());
                    TarType tarType = params.getTarType();
                    if (Objects.nonNull(tarType)) {
                        queryParams.add("tar_type", tarType.name());
                    }
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build().toUri();
                    return Get(uri);
                })
                .consumer(response -> this.billDownload(Objects.requireNonNull(response.getBody()).get("download_url").asText()))
                .request();
    }
}
