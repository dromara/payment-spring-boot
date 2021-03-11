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
import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.PayParams;
import cn.felord.payment.wechat.v3.model.RefundParams;
import cn.felord.payment.wechat.v3.model.TransactionQueryParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 普通支付-直连模式.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class WechatDirectPayApi extends AbstractApi {

    /**
     * Instantiates a new Wechat pay api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatDirectPayApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * APP下单API
     *
     * @param payParams the pay params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> appPay(PayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.APP, payParams)
                .function(this::payFunction)
                .consumer(responseEntity -> {
                    ObjectNode body = responseEntity.getBody();
                    if (Objects.isNull(body)) {
                        throw new PayException("response body cannot be resolved");
                    }

                    SignatureProvider signatureProvider = this.client().signatureProvider();
                    WechatMetaContainer wechatMetaContainer = signatureProvider.wechatMetaContainer();
                    WechatMetaBean wechatMetaBean = wechatMetaContainer.getWechatMeta(tenantId());
                    PrivateKey privateKey = wechatMetaBean.getKeyPair().getPrivate();

                    String appId = wechatMetaBean.getV3().getAppId();
                    long epochSecond = LocalDateTime.now()
                            .toEpochSecond(ZoneOffset.of("+8"));
                    String timestamp = String.valueOf(epochSecond);
                    String nonceStr = signatureProvider.nonceStrGenerator()
                            .generateId()
                            .toString()
                            .replaceAll("-", "");
                    String prepayId = body.get("prepay_id").asText();
                    String paySign = signatureProvider.doRequestSign(true,privateKey, appId, timestamp, nonceStr, prepayId);
                    String mchId = wechatMetaBean.getV3().getMchId();

                    body.put("appid", appId);
                    body.put("partnerid", mchId);
                    body.put("prepayid", prepayId);
                    body.put("package", "Sign=WXPay");
                    body.put("nonceStr", nonceStr);
                    body.put("timeStamp", timestamp);
                    body.put("signType", "RSA");
                    body.put("paySign", paySign);

                    wechatResponseEntity.setHttpStatus(responseEntity.getStatusCodeValue());
                    wechatResponseEntity.setBody(body);
                })
                .request();
        return wechatResponseEntity;
    }

    /**
     * JSAPI/小程序下单API
     *
     * @param payParams the pay params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> jsPay(PayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.JSAPI, payParams)
                .function(this::payFunction)
                .consumer(responseEntity -> {
                    ObjectNode body = responseEntity.getBody();
                    if (Objects.isNull(body)) {
                        throw new PayException("response body cannot be resolved");
                    }

                    SignatureProvider signatureProvider = this.client().signatureProvider();
                    WechatMetaContainer wechatMetaContainer = signatureProvider.wechatMetaContainer();
                    WechatMetaBean wechatMetaBean = wechatMetaContainer.getWechatMeta(tenantId());
                    PrivateKey privateKey = wechatMetaBean.getKeyPair().getPrivate();

                    String appId = wechatMetaBean.getV3().getAppId();
                    long epochSecond = LocalDateTime.now()
                            .toEpochSecond(ZoneOffset.of("+8"));
                    String timestamp = String.valueOf(epochSecond);
                    String nonceStr = signatureProvider.nonceStrGenerator()
                            .generateId()
                            .toString()
                            .replaceAll("-", "");
                    String packageStr = "prepay_id=" + body.get("prepay_id").asText();
                    String paySign = signatureProvider.doRequestSign(true,privateKey, appId, timestamp, nonceStr, packageStr);

                    body.put("appId", appId);
                    body.put("timeStamp", timestamp);
                    body.put("nonceStr", nonceStr);
                    body.put("package", packageStr);
                    body.put("signType", "RSA");
                    body.put("paySign", paySign);

                    wechatResponseEntity.setHttpStatus(responseEntity.getStatusCodeValue());
                    wechatResponseEntity.setBody(body);
                })
                .request();
        return wechatResponseEntity;
    }

    /**
     * Native下单API
     *
     * @param payParams the pay params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> nativePay(PayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.NATIVE, payParams)
                .function(this::payFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * H5下单API
     *
     * @param payParams the pay params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> h5Pay(PayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MWEB, payParams)
                .function(this::payFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> payFunction(WechatPayV3Type type, PayParams payParams) {
        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
        payParams.setAppid(v3.getAppId());
        payParams.setMchid(v3.getMchId());
        String notifyUrl = v3.getDomain().concat(payParams.getNotifyUrl());
        payParams.setNotifyUrl(notifyUrl);
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();
        return Post(uri, payParams);
    }

    /**
     * 微信支付订单号查询API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryTransactionById(TransactionQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.TRANSACTION_TRANSACTION_ID, params)
                .function(this::queryTransactionFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 商户订单号查询API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryTransactionByOutTradeNo(TransactionQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.TRANSACTION_OUT_TRADE_NO, params)
                .function(this::queryTransactionFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> queryTransactionFunction(WechatPayV3Type type, TransactionQueryParams params) {
        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("mchid", v3.getMchId());

        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .queryParams(queryParams)
                .build()
                .expand(params.getTransactionIdOrOutTradeNo())
                .toUri();
        return Get(uri);
    }

    /**
     * 关单API
     *
     * @param outTradeNo the out trade no
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> close(String outTradeNo) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.CLOSE, outTradeNo)
                .function(this::closeByOutTradeNoFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> closeByOutTradeNoFunction(WechatPayV3Type type, String outTradeNo) {
        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();

        Map<String, String> queryParams = new HashMap<>(1);
        queryParams.put("mchid", v3.getMchId());

        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .expand(outTradeNo)
                .toUri();
        return Post(uri, queryParams);
    }

    /**
     * 申请退款API
     *
     * @param refundParams the refund params
     * @return the wechat response entity
     * @since 1.0.6.RELEASE
     */
    public WechatResponseEntity<ObjectNode> refund(RefundParams refundParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.REFUND, refundParams)
                .function(((type, params) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    params.setNotifyUrl(v3.getDomain().concat(params.getNotifyUrl()));
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
     * @since 1.0.6.RELEASE
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
