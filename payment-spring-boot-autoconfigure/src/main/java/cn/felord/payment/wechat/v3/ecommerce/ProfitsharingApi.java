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
import cn.felord.payment.wechat.enumeration.ReceiverType;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.AbstractApi;
import cn.felord.payment.wechat.v3.SignatureProvider;
import cn.felord.payment.wechat.v3.WechatPayClient;
import cn.felord.payment.wechat.v3.WechatResponseEntity;
import cn.felord.payment.wechat.v3.X509WechatCertificateInfo;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceFinishOrder;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceProfitsharingOrder;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceReceiver;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceReceiverDeleteParams;
import cn.felord.payment.wechat.v3.model.ecommerce.EcommerceReturnOrderParams;
import cn.felord.payment.wechat.v3.model.ecommerce.Receiver;
import cn.felord.payment.wechat.v3.model.profitsharing.PartnerQueryOrderParams;
import cn.felord.payment.wechat.v3.model.profitsharing.PartnerReturnOrdersParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * 电商收付通分账
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
class ProfitsharingApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    ProfitsharingApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 请求分账API
     * <p>
     * 微信订单支付成功后，商户发起分账请求，将结算后的资金分到分账接收方
     *
     * @param profitSharingOrder the profit sharing order
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> profitsharingOrders(EcommerceProfitsharingOrder profitSharingOrder) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_PROFITSHARING_ORDERS, profitSharingOrder)
                .function((wechatPayV3Type, params) -> {
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    SignatureProvider signatureProvider = this.client().signatureProvider();
                    X509WechatCertificateInfo certificate = signatureProvider.getCertificate(this.wechatMetaBean().getTenantId());
                    final X509Certificate x509Certificate = certificate.getX509Certificate();
                    params.setAppid(v3.getAppId());
                    List<Receiver> receivers = params.getReceivers();
                    if (!CollectionUtils.isEmpty(receivers)) {
                        receivers.forEach(receiversItem -> {
                            String name = receiversItem.getReceiverName();
                            if (StringUtils.hasText(name)) {
                                String encryptedName = signatureProvider.encryptRequestMessage(name, x509Certificate);
                                receiversItem.setReceiverName(encryptedName);
                            }
                        });
                    }
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("Wechatpay-Serial", certificate.getWechatPaySerial());
                    return Post(uri, params, httpHeaders);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询分账结果API
     * <p>
     * 发起分账请求后，可调用此接口查询分账结果
     * <p>
     * 注意：
     * <ul>
     *     <li>发起解冻剩余资金请求后，可调用此接口查询解冻剩余资金的结果</li>
     * </ul>
     *
     * @param queryOrderParams the query order params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryProfitsharingOrder(PartnerQueryOrderParams queryOrderParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_PROFITSHARING_RESULT, queryOrderParams)
                .function((wechatPayV3Type, params) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    queryParams.add("sub_mchid", params.getSubMchid());
                    queryParams.add("transaction_id", params.getTransactionId());
                    queryParams.add("out_order_no", params.getOutOrderNo());
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
     * 请求分账回退API
     * <p>
     * 如果订单已经分账，在退款时，可以先调此接口，将已分账的资金从分账接收方的账户回退给分账方，再发起退款
     *
     * @param returnOrdersParams the return orders params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> returnOrders(PartnerReturnOrdersParams returnOrdersParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_PROFITSHARING_RETURN_ORDERS, returnOrdersParams)
                .function((wechatPayV3Type, params) -> {

                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, params);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询分账回退结果API
     * <p>
     * 商户需要核实回退结果，可调用此接口查询回退结果
     * <p>
     * 注意：
     * <ul>
     *     <li>如果分账回退接口返回状态为处理中，可调用此接口查询回退结果</li>
     * </ul>
     *
     * @param queryReturnOrderParams the query return order params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryReturnOrders(EcommerceReturnOrderParams queryReturnOrderParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_PROFITSHARING_RETURN_ORDERS_RESULT, queryReturnOrderParams)
                .function((wechatPayV3Type, params) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    queryParams.add("sub_mchid", params.getSubMchid());
                    String orderId = params.getOrderId();
                    if (orderId != null) {
                        queryParams.add("out_order_no", orderId);
                    }
                    String outOrderNo = params.getOutOrderNo();
                    if (outOrderNo != null) {
                        queryParams.add("out_order_no", outOrderNo);
                    }
                    queryParams.add("out_return_no", params.getOutReturnNo());
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
     * 完结分账API
     * <p>
     * 不需要进行分账的订单，可直接调用本接口将订单的金额全部解冻给二级商户。
     *
     * @param finishOrder the finish order
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> finishOrder(EcommerceFinishOrder finishOrder) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_PROFITSHARING_FINISH_ORDER, finishOrder)
                .function((wechatPayV3Type, params) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, params);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询订单剩余待分金额API
     * <p>
     * 可调用此接口查询订单剩余待分金额
     *
     * @param transactionId the transaction id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryOrderAmounts(String transactionId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_PROFITSHARING_ORDER_AMOUNTS, transactionId)
                .function((wechatPayV3Type, id) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
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
     * 添加分账接收方API
     * <p>
     * 商户发起添加分账接收方请求，建立分账接收方列表。后续可通过发起分账请求，将分账方商户结算后的资金，分到该分账接收方
     *
     * @param ecommerceReceiver the ecommerce receiver
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> addReceivers(EcommerceReceiver ecommerceReceiver) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_PROFITSHARING_RECEIVERS_ADD, ecommerceReceiver)
                .function((wechatPayV3Type, params) -> {
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    params.setAppid(v3.getAppId());
                    SignatureProvider signatureProvider = this.client().signatureProvider();
                    X509WechatCertificateInfo certificate = signatureProvider.getCertificate(this.wechatMetaBean().getTenantId());
                    final X509Certificate x509Certificate = certificate.getX509Certificate();

                    String name = params.getName();
                    if (ReceiverType.PERSONAL_OPENID.equals(params.getType()) && StringUtils.hasText(name)) {
                        // 个人应该必传
                        String encryptedName = signatureProvider.encryptRequestMessage(name, x509Certificate);
                        params.setEncryptedName(encryptedName);
                    }
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("Wechatpay-Serial", certificate.getWechatPaySerial());
                    return Post(uri, params, httpHeaders);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 删除分账接收方API
     * <p>
     * 商户发起删除分账接收方请求。删除后，不支持将分账方商户结算后的资金，分到该分账接收方
     *
     * @param delReceiversParams the del receivers params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> deleteReceivers(EcommerceReceiverDeleteParams delReceiversParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_PROFITSHARING_RECEIVERS_DELETE, delReceiversParams)
                .function((wechatPayV3Type, params) -> {
                    WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
                    params.setAppid(v3.getAppId());
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    return Post(uri, params);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }
}
