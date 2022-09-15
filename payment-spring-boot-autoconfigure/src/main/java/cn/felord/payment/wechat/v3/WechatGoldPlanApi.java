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
import cn.felord.payment.wechat.v3.model.goldplan.GoldPlanChangeParams;
import cn.felord.payment.wechat.v3.model.goldplan.GoldPlanAdvertisingParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

/**
 * 经营能力-点金计划
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
public class WechatGoldPlanApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatGoldPlanApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 点金计划管理API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> change(GoldPlanChangeParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.GOLD_PLAN_CHANGE, params)
                .function((wechatPayV3Type, goldPlanChangeParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, goldPlanChangeParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 商家小票管理API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> changeCustom(GoldPlanChangeParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.GOLD_PLAN_CHANGE_CUSTOM, params)
                .function((wechatPayV3Type, goldPlanChangeParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, goldPlanChangeParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 同业过滤标签管理API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> filter(GoldPlanAdvertisingParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.GOLD_PLAN_CHANGE_CUSTOM, params)
                .function((wechatPayV3Type, goldPlanChangeParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, goldPlanChangeParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 开通广告展示API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> openAdv(GoldPlanAdvertisingParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.GOLD_PLAN_ADV_OPEN, params)
                .function((wechatPayV3Type, goldPlanChangeParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, goldPlanChangeParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 关闭广告展示API
     *
     * @param subMchid the sub mchid
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> closeAdv(String subMchid) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.GOLD_PLAN_ADV_CLOSE, subMchid)
                .function((wechatPayV3Type, id) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, Collections.singletonMap("sub_mchid",id));
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }
}
