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
import cn.felord.payment.wechat.v3.model.paygiftactivity.ActivitiesListPageRequest;
import cn.felord.payment.wechat.v3.model.paygiftactivity.ActivitiesPageRequest;
import cn.felord.payment.wechat.v3.model.paygiftactivity.DeleteActivityMchRequest;
import cn.felord.payment.wechat.v3.model.paygiftactivity.GiftActivityParams;
import cn.felord.payment.wechat.v3.model.paygiftactivity.NewActivityMchRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付有礼
 *
 * @author dax
 * @since 1.0.19.RELEASE
 */
public class WechatMarketingPayGiftActivityApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatMarketingPayGiftActivityApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 创建全场满额送活动API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> create(GiftActivityParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_PAYGIFTACTIVITY, params)
                .function((type, activityParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, activityParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询活动详情接口API
     *
     * @param activityId the activity id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> details(String activityId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_PAYGIFTACTIVITY_DETAIL, activityId)
                .function((type, param) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(param)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询活动发券商户号API
     *
     * @param request the request
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> merchants(ActivitiesPageRequest request) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_PAYGIFTACTIVITY_MCH, request)
                .function((type, params) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParam("offset", params.getOffset())
                            .queryParam("limit", params.getLimit())
                            .build()
                            .expand(params.getActivityId())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询活动指定商品列表API
     *
     * @param request the request
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> goods(ActivitiesPageRequest request) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_PAYGIFTACTIVITY_GOODS, request)
                .function((type, params) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParam("offset", params.getOffset())
                            .queryParam("limit", params.getLimit())
                            .build()
                            .expand(params.getActivityId())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 终止活动API
     *
     * @param activityId the activity id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> terminate(String activityId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_PAYGIFTACTIVITY_TERMINATE, activityId)
                .function((type, param) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(param)
                            .toUri();
                    return Post(uri, Collections.emptyMap());
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 新增活动发券商户号API
     *
     * @param request the request
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> addMches(NewActivityMchRequest request) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_PAYGIFTACTIVITY_MCH_ADD, request)
                .function((type, params) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(params.getActivityId())
                            .toUri();
                    Map<String, Object> body = new HashMap<>();
                    body.put("merchant_id_list", params.getMerchantIdList());
                    body.put("add_request_no", params.getAddRequestNo());
                    return Post(uri, body);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 获取支付有礼活动列表API
     *
     * @param request the request
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> page(ActivitiesListPageRequest request) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_PAYGIFTACTIVITY_ACTIVITIES, request)
                .function((type, params) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParam("offset", params.getOffset())
                            .queryParam("limit", params.getLimit())
                            .queryParam("activity_name", params.getActivityName())
                            .queryParam("activity_status", params.getActivityStatus())
                            .queryParam("award_type", params.getAwardType())
                            .build()
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 删除活动发券商户号API
     *
     * @param request the request
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> deleteMch(DeleteActivityMchRequest request) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_PAYGIFTACTIVITY_MCH_DEL, request)
                .function((type, params) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(params.getActivityId())
                            .toUri();
                    Map<String, Object> body = new HashMap<>();
                    body.put("merchant_id_list", params.getMerchantIdList());
                    body.put("delete_request_no", params.getDeleteRequestNo());
                    return Post(uri, body);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }
}
