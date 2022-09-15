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
import cn.felord.payment.wechat.v3.WechatPayClient;
import cn.felord.payment.wechat.v3.WechatResponseEntity;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceFundOverseaOrder;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceFundOverseaParams;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceFundOverseaResultParams;
import cn.felord.payment.wechat.v3.model.profitsharing.PartnerProfitsharingBillParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 电商收付通跨境付款
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
public class FundOverseaApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    FundOverseaApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 查询订单剩余可出境余额API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryBalance(EcommerceFundOverseaParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_OVERSEA_BALANCE, params)
                .function((type, fundOverseaParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParam("sub_mchid", fundOverseaParams.getSubMchid())
                            .build()
                            .expand(fundOverseaParams.getTransactionId())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 申请资金出境API
     *
     * @param order the order
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> apply(EcommerceFundOverseaOrder order) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_OVERSEA_ORDERS, order)
                .function((wechatPayV3Type, overseaOrder) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, overseaOrder);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询出境结果API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryOrder(EcommerceFundOverseaResultParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_OVERSEA_ORDERS_RESULT, params)
                .function((type, overseaResultParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParam("sub_mchid", overseaResultParams.getSubMchid())
                            .queryParam("transaction_id", overseaResultParams.getTransactionId())
                            .build()
                            .expand(overseaResultParams.getOutOrderId())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 获取购付汇账单文件下载链接API
     *
     * @param billParams the bill params
     * @return the response entity
     */
    public ResponseEntity<Resource> downloadMerchantBills(PartnerProfitsharingBillParams billParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_OVERSEA_BILLS, billParams)
                .function(((wechatPayV3Type, params) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .queryParam("bill_date", params.getBillDate().format(DateTimeFormatter.ISO_DATE))
                            .queryParam("sub_mchid", params.getSubMchid())
                            .build()
                            .toUri();
                    return Get(uri);
                })).consumer(wechatResponseEntity::convert)
                .request();
        String downloadUrl = Objects.requireNonNull(wechatResponseEntity.getBody())
                .get("download_url")
                .asText();

        String ext = ".gzip";
        String filename = "fundoverseabill-" + billParams.getBillDate().toString() + ext;
        return this.downloadBillResponse(downloadUrl, filename);
    }

}
