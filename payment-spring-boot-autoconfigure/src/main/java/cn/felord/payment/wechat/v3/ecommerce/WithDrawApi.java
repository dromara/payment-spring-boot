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

import cn.felord.payment.wechat.enumeration.TarType;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.AbstractApi;
import cn.felord.payment.wechat.v3.WechatPayClient;
import cn.felord.payment.wechat.v3.WechatResponseEntity;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceErrorBillParams;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceMchReserveParams;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceOutRequestNo;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceReserveParams;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceWithdraw;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 电商收付通-商户提现
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
public class WithDrawApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WithDrawApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 二级商户预约提现API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> ecommerceReserve(EcommerceReserveParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_WITHDRAW, params)
                .function((wechatPayV3Type, refundParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, refundParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 二级商户查询预约提现状态API-微信支付预约提现单号查询
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryByWithdrawId(EcommerceWithdraw params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_WITHDRAW_ID, params)
                .function((type, ecommerceWithdraw) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParam("sub_mchid", ecommerceWithdraw.getSubMchid())
                            .build()
                            .expand(ecommerceWithdraw.getWithdrawId())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 二级商户查询预约提现状态API-商户预约提现单号查询
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryByOutRequestNo(EcommerceOutRequestNo params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_WITHDRAW_OUT_REQUEST_NO, params)
                .function((type, ecommerceWithdraw) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParam("sub_mchid", ecommerceWithdraw.getSubMchid())
                            .build()
                            .expand(ecommerceWithdraw.getOutRequestNo())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 电商平台预约提现API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> ecommerceMchReserve(EcommerceMchReserveParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_MERCHANT_WITHDRAW, params)
                .function((wechatPayV3Type, refundParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, refundParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 电商平台查询预约提现状态API-微信支付预约提现单号查询
     *
     * @param withdrawId the withdraw id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryMchByWithdrawId(String withdrawId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_MERCHANT_WITHDRAW_ID, withdrawId)
                .function((type, id) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(withdrawId)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 电商平台查询预约提现状态API-商户预约提现单号查询
     *
     * @param outRequestNo the out request no
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryMchByOutRequestNo(String outRequestNo) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_WITHDRAW_MERCHANT_OUT_REQUEST_NO, outRequestNo)
                .function((type, no) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(no)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 按日下载提现异常文件API
     * <p>
     * 电商服务商按日查询并下载提现状态为异常的提现单，提现异常包括提现失败和银行退票。
     *
     * @param params the params
     * @return the response entity
     */
    public ResponseEntity<Resource> downloadErrorBill(EcommerceErrorBillParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();

        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_WITHDRAW_ERROR_BILL, params)
                .function((wechatPayV3Type, errorBillParams) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    LocalDate billDate = params.getBillDate();
                    queryParams.add("bill_date", billDate.format(DateTimeFormatter.ISO_DATE));
                    TarType tarType = params.getTarType();
                    if (Objects.nonNull(tarType)) {
                        queryParams.add("tar_type", tarType.name());
                    }
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build()
                            .expand("NO_SUCC")
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        String downloadUrl = Objects.requireNonNull(wechatResponseEntity.getBody())
                .get("download_url")
                .asText();

        String ext = Objects.equals(TarType.GZIP, params.getTarType()) ? ".gzip" : ".txt";
        String filename = "errorbill-" + params.getBillDate().toString() + ext;
        return downloadBillResponse(downloadUrl, filename);
    }
}
