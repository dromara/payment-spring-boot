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

import cn.felord.payment.wechat.enumeration.FundFlowAccountType;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.AbstractApi;
import cn.felord.payment.wechat.v3.WechatPayClient;
import cn.felord.payment.wechat.v3.WechatResponseEntity;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceFundEndDay;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceFundSubMch;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.format.DateTimeFormatter;

/**
 * 电商收付通余额查询
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
class BalanceApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    BalanceApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 查询二级商户账户实时余额API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryBySubMchid(EcommerceFundSubMch params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_BALANCE_REAL_TIME, params)
                .function((type, fundSubMch) -> {
                    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA));
                    if (params.getAccountType() != null) {
                        builder.queryParam("account_type", fundSubMch.getAccountType().name());
                    }
                    URI uri = builder
                            .build()
                            .expand(fundSubMch.getSubMchid())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询二级商户账户日终余额API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryAtEndDay(EcommerceFundEndDay params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_BALANCE_END_DAY, params)
                .function((type, fundEndDay) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParam("account_type", fundEndDay.getDate().format(DateTimeFormatter.ISO_DATE))
                            .build()
                            .expand(fundEndDay.getSubMchid())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询电商平台账户实时余额API
     *
     * @param type the type
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryBalanceAtRealTime(FundFlowAccountType type) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_BALANCE_TYPE_REAL_TIME, type)
                .function((wechatPayV3Type, accountType) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(accountType.name())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询电商平台账户实时余额API
     *
     * @param type the type
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryBalanceAtEndDay(FundFlowAccountType type) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_BALANCE_TYPE_END_DAY, type)
                .function((wechatPayV3Type, accountType) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(accountType.name())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }
}
