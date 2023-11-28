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

import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.RefundParams;
import cn.felord.payment.wechat.v3.model.ResponseSignVerifyParams;
import cn.felord.payment.wechat.v3.model.payscore.parking.ParkingServiceQueryParams;
import cn.felord.payment.wechat.v3.model.payscore.parking.ParkingParams;
import cn.felord.payment.wechat.v3.model.payscore.parking.TransParkingParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.function.Consumer;

/**
 * 微信支付分停车服务API.
 * <p>
 * 详细文档，参见 <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/open/pay/chapter3_7_1.shtml">微信支付分停车服务介绍</a>
 *
 * @author felord.cn
 * @since 1.0.13.RELEASE
 */
public class WechatPayScoreParkingApi extends AbstractApi {

    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatPayScoreParkingApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 微信支付分-查询车牌服务开通信息API
     * <p>
     * 该接口仅支持停车场景，商户首先请求查询车牌服务开通信息接口，确认该车牌，是否被该用户开通车主服务。
     *
     * @param params the params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> find(ParkingServiceQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_PARKING_FIND, params)
                .function((wechatPayV3Type, parkingServiceQueryParams) -> {

                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

                    queryParams.add("appid", parkingServiceQueryParams.getAppid());
                    queryParams.add("plate_number", parkingServiceQueryParams.getPlateNumber());
                    queryParams.add("plate_color", parkingServiceQueryParams.getPlateColor().name());
                    queryParams.add("openid", parkingServiceQueryParams.getOpenid());

                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .encode()
                            .build()
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 微信支付分-创建停车入场API
     * <p>
     * 车辆入场以后，商户调用该接口，创建停车入场信息，需要处理回调逻辑。
     * <p>
     * 回调通知 {@link WechatPayCallback#payscoreParkingCallback(ResponseSignVerifyParams, Consumer)}    
     * @param params the params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> parking(ParkingParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_PARKING_PARKINGS, params)
                .function((wechatPayV3Type, parkingParams) -> {
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    parkingParams.setNotifyUrl(v3.getDomain().concat(parkingParams.getNotifyUrl()));
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, parkingParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 微信支付分-扣费受理API
     * <p>
     * 商户请求扣费受理接口，会完成订单受理。微信支付进行异步扣款，支付完成后，会将订单支付结果发送给商户。
     * <p>
     * 注意：
     * <ul>
     *     <li>必须确认扣费受理接口的交易状态返回{@code ACCEPTED}才能放行车辆，若未接收到该状态而放行车辆离场，造成的资金损失由商户侧自行承担</li>
     * </ul>
     * 回调通知 {@link WechatPayCallback#payscoreTransParkingCallback(ResponseSignVerifyParams, Consumer)}
     * @param params the params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> transactionsParking(TransParkingParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_PARKING_TRANSACTIONS_PARKINGS, params)
                .function((wechatPayV3Type, transParkingParams) -> {
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    transParkingParams.setNotifyUrl(v3.getDomain().concat(transParkingParams.getNotifyUrl()));
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, transParkingParams);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询订单API.
     *
     * @param outTradeNo the out trade no
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryTransactionByOutTradeNo(String outTradeNo) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.PAY_SCORE_PARKING_TRANSACTIONS_OUTTRADENO, outTradeNo)
                .function((wechatPayV3Type, tradeNo) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(tradeNo)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 申请退款API
     *
     * @param refundParams the refund params
     * @return the wechat response entity
     * @since 1.0.17.RELEASE
     */
    public WechatResponseEntity<ObjectNode> refund(RefundParams refundParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.REFUND, refundParams)
                .function(((type, params) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    String notifyUrl = params.getNotifyUrl();
                    if (StringUtils.hasText(notifyUrl)) {
                        params.setNotifyUrl(v3.getDomain().concat(notifyUrl));
                    }
                    return Post(uri, params);
                }))
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }


    /**
     * 查询单笔退款API
     *
     * @param outRefundNo the out refund no
     * @return the wechat response entity
     * @since 1.0.17.RELEASE
     */
    public WechatResponseEntity<ObjectNode> queryRefundInfo(String outRefundNo) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.QUERY_REFUND, outRefundNo)
                .function(((type, param) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(param)
                            .toUri();
                    return Get(uri);
                }))
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }
}
