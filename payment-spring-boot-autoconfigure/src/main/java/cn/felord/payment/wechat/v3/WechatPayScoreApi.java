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

import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.payscore.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 微信支付分API.
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
public class WechatPayScoreApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatPayScoreApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 微信支付分-查询用户授权状态API.
     * <p>
     * 免确认订单起始接口，【免确认订单模式】是高级接口权限，参见：<a href="https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/payscore/chapter2_5.shtml">业务流程说明</a>
     * <p>
     * 用户申请使用服务时，商户可通过此接口查询用户是否“已授权”本服务。在“已授权”状态下的服务，用户才可以申请使用。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> userServiceState(UserServiceStateParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_USER_SERVICE_STATE, params)
                .function((wechatPayV3Type, userServiceStateParams) -> {
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();

                    MultiValueMap<String, String> expandParams = new LinkedMultiValueMap<>();
                    expandParams.add("appid", v3.getAppId());
                    expandParams.add("service_id", params.getServiceId());
                    expandParams.add("openid", params.getOpenId());

                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(expandParams)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 创建支付分订单API
     * <p>
     * 用户申请使用服务时，商户可通过此接口申请创建微信支付分订单。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> createServiceOrder(UserServiceOrderParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_CREATE_USER_SERVICE_ORDER, params)
                .function((wechatPayV3Type, orderParams) -> {
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();

                    orderParams.setAppid(v3.getAppId());
                    orderParams.setNotifyUrl(v3.getDomain().concat(orderParams.getNotifyUrl()));

                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, orderParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询支付分订单API
     * <p>
     * 用于查询单笔微信支付分订单详细信息。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryServiceOrder(QueryServiceOrderParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_QUERY_USER_SERVICE_ORDER, params)
                .function((wechatPayV3Type, orderParams) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

                    String outOrderNo = orderParams.getOutOrderNo();
                    if (StringUtils.hasText(outOrderNo)) {
                        queryParams.add("out_order_no", outOrderNo);
                    }

                    String queryId = orderParams.getQueryId();
                    if (StringUtils.hasText(queryId)) {
                        queryParams.add("query_id", queryId);
                    }
                    queryParams.add("service_id", orderParams.getServiceId());

                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    queryParams.add("appid", v3.getAppId());

                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
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
     * 取消支付分订单API
     * <p>
     * 微信支付分订单创建之后，由于某些原因导致订单不能正常支付时，可使用此接口取消订单。
     * <p>
     * 订单为以下状态时可以取消订单：CREATED（已创单）、DOING（进行中）（包括商户完结支付分订单后，且支付分订单收款状态为待支付USER_PAYING)
     * <p>
     * 注意：
     * • DOING状态包含了订单用户确认、已完结-待支付（USER_PAYING）的状态，因此这种状态下订单也是可以被取消的，请确认当前操作是否正确，防止误操作将完结后需要支付分收款的单据取消。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> cancelServiceOrder(CancelServiceOrderParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_CANCEL_USER_SERVICE_ORDER, params)
                .function((wechatPayV3Type, orderParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(orderParams.getOutOrderNo())
                            .toUri();

                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    orderParams.setAppid(v3.getAppId());
                    orderParams.setOutOrderNo(null);
                    return Post(uri, orderParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 修改订单金额API
     * <p>
     * 完结订单总金额与实际金额不符时，可通过该接口修改订单金额。
     * 例如：充电宝场景，由于机器计费问题导致商户完结订单时扣除用户99元，用户客诉成功后，商户需要按照实际的消费金额（如10元）扣费，当服务订单支付状态处于“待支付”时，商户可使用此能力修改订单金额。
     * <p>
     * 注意：
     * • 若此笔订单已收款成功，商户直接使用退款能力，将差价退回用户即可。
     * <p>
     * • 修改次数&gt;=1，第n次修改后金额 &lt;第n-1次修改后金额
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> modifyServiceOrder(ModifyServiceOrderParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_MODIFY_USER_SERVICE_ORDER, params)
                .function((wechatPayV3Type, orderParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(orderParams.getOutOrderNo())
                            .toUri();

                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    orderParams.setAppid(v3.getAppId());
                    orderParams.setOutOrderNo(null);
                    return Post(uri, orderParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 完结支付分订单API
     * <p>
     * 前置条件：服务订单状态为“进行中”且订单状态说明需为[USER_CONFIRM:用户确认]
     * <p>
     * 完结微信支付分订单。用户使用服务完成后，商户可通过此接口完结订单。
     * <p>
     * 特别说明：
     * • 完结接口调用成功后，微信支付将自动发起免密代扣。 若扣款失败，微信支付将自动再次发起免密代扣（按照一定频次），直到扣成功为止。
     *
     * @param params the params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> completeServiceOrder(CompleteServiceOrderParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_COMPLETE_USER_SERVICE_ORDER, params)
                .function((wechatPayV3Type, orderParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(orderParams.getOutOrderNo())
                            .toUri();

                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    orderParams.setAppid(v3.getAppId());
                    orderParams.setOutOrderNo(null);
                    return Post(uri, orderParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 商户发起催收扣款API
     * <p>
     * 前置条件：服务订单支付状态处于“待支付”状态
     * <p>
     * 当微信支付分订单支付状态处于“待支付”时，商户可使用该接口向用户发起收款。
     * <p>
     * 注意：
     * • 此能力不影响微信支付分代商户向用户发起收款的策略。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> payServiceOrder(PayServiceOrderParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_PAY_USER_SERVICE_ORDER, params)
                .function((wechatPayV3Type, orderParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(orderParams.getOutOrderNo())
                            .toUri();

                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    orderParams.setAppid(v3.getAppId());
                    orderParams.setOutOrderNo(null);
                    return Post(uri, orderParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 同步服务订单信息API
     * <p>
     * 前提条件：同步商户渠道收款成功信息时，即场景类型=“Order_Paid”，订单的状态需为[MCH_COMPLETE:商户完结订单]
     * <p>
     * 由于收款商户进行的某些“线下操作”会导致微信支付侧的订单状态与实际情况不符。例如，用户通过线下付款的方式已经完成支付，而微信支付侧并未支付成功，此时可能导致用户重复支付。因此商户需要通过订单同步接口将订单状态同步给微信支付，修改订单在微信支付系统中的状态。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> syncServiceOrder(SyncServiceOrderParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_SYNC_USER_SERVICE_ORDER, params)
                .function((wechatPayV3Type, orderParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(orderParams.getOutOrderNo())
                            .toUri();

                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    orderParams.setAppid(v3.getAppId());
                    orderParams.setOutOrderNo(null);
                    return Post(uri, orderParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 创单结单合并API
     * <p>
     * 相对需确认模式，免确认模式减少了用户确认授权这步操作，因此在免确认模式下商户无法获取用户的授权状态，为了解决商户的困扰，我们为免确认模式特别提供了查询授权状态和调起授权页面的api接口，这些接口仅在免确认模式下需要调用，且必须调用。
     * <p>
     * <p>
     * 该接口适用于无需微信支付分做订单风控判断的业务场景，在服务完成后，通过该接口对用户进行免密代扣。
     * <p>
     * 注意：
     * • 限制条件：【免确认订单模式】，用户已授权状态下，可调用该接口。
     * <p>
     * <p>
     * 特别提醒：创单结单合并接口暂未对外开放，如有需要请咨询对接的微信支付运营人员，申请开通调用权限。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> directCompleteServiceOrder(DirectCompleteServiceOrderParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_DIRECT_COMPLETE, params)
                .function((wechatPayV3Type, orderParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();

                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    orderParams.setAppid(v3.getAppId());
                    String notifyUrl = orderParams.getNotifyUrl();
                    if (StringUtils.hasText(notifyUrl)) {
                        orderParams.setNotifyUrl(v3.getDomain().concat(notifyUrl));
                    }
                    return Post(uri, orderParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 商户预授权API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> permissions(ServiceOrderPermissionParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_PERMISSIONS, params)
                .function((wechatPayV3Type, orderParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();

                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    String notifyUrl = orderParams.getNotifyUrl();
                    orderParams.setAppid(v3.getAppId());
                    if (StringUtils.hasText(notifyUrl)) {
                        orderParams.setNotifyUrl(v3.getDomain().concat(notifyUrl));
                    }
                    return Post(uri, orderParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }


    /**
     * 查询与用户授权记录（授权协议号）API
     * <p>
     * 通过authorization_code，商户查询与用户授权关系
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryPermissionsByAuthCode(PermissionsAuthCodeParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_PERMISSIONS_AUTH_CODE, params)
                .function((wechatPayV3Type, orderParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .queryParam("service_id", orderParams.getServiceId())
                            .build()
                            .expand(orderParams.getAuthorizationCode())
                            .toUri();

                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 解除用户授权关系（授权协议号）API
     * <p>
     * 通过authorization_code，商户解除用户授权关系
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> terminatePermissionsByAuthCode(PermissionsAuthCodeParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_TERMINATE_PERMISSIONS_AUTH_CODE, params)
                .function((wechatPayV3Type, orderParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(orderParams.getAuthorizationCode())
                            .toUri();
                    orderParams.setAuthorizationCode(null);
                    return Post(uri, orderParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询与用户授权记录（openid）API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryPermissionsByOpenId(PermissionsOpenIdParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_PERMISSIONS_OPENID, params)
                .function((wechatPayV3Type, orderParams) -> {
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .queryParam("appid", v3.getAppId())
                            .queryParam("service_id", orderParams.getServiceId())
                            .build()
                            .expand(orderParams.getOpenid())
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 解除用户授权关系（openid）API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> terminatePermissionsByOpenId(PermissionsOpenIdParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_TERMINATE_PERMISSIONS_OPENID, params)
                .function((wechatPayV3Type, orderParams) -> {

                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(orderParams.getOpenid())
                            .toUri();
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    orderParams.setAppid(v3.getAppId());
                    orderParams.setOpenid(null);
                    return Post(uri, orderParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }
}
