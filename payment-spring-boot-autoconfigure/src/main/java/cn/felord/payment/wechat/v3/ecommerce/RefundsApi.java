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

import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.AbstractApi;
import cn.felord.payment.wechat.v3.WechatPayClient;
import cn.felord.payment.wechat.v3.WechatResponseEntity;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceRefundParams;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceReturnAdvance;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

/**
 * 电商收付通退款
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
class RefundsApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    RefundsApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 申请退款API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> apply(EcommerceRefundParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_REFUNDS_APPLY, params)
                .function((wechatPayV3Type, refundParams) -> {
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    refundParams.setSpAppid(v3.getAppId());
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
     * 查询退款API-通过微信支付退款单号查询退款
     *
     * @param refundId the refund id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryByRefundId(String refundId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_REFUNDS_ID, refundId)
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
     * 查询退款API-通过商户退款单号查询退款
     *
     * @param outRefundNo the out refund no
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryByOutRefundNo(String outRefundNo) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_REFUNDS_OUT_REFUND_NO, outRefundNo)
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
     * 垫付退款回补API
     * <p>
     * 提交垫付退款后，发起退款方可通过该接口发起垫付退款资金回补，把退款垫付的资金从二级商户回补到电商平台账户。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> returnAdvance(EcommerceReturnAdvance params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_REFUNDS_RETURN_ADVANCE, params)
                .function((wechatPayV3Type, advanceParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(advanceParams.getRefundId())
                            .toUri();
                    return Post(uri, Collections.singletonMap("sub_mchid", advanceParams.getSubMchid()));
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询垫付回补结果API
     * <p>
     * 提交垫付退款回补后，通过调用该接口查询垫付回补结果。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryAdvanceResult(EcommerceReturnAdvance params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_REFUNDS_RETURN_ADVANCE_RESULT, params)
                .function((type, returnAdvance) -> {
                    LinkedMultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    queryParams.add("refund_id",returnAdvance.getRefundId());
                    queryParams.add("sub_mchid",returnAdvance.getSubMchid());
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
