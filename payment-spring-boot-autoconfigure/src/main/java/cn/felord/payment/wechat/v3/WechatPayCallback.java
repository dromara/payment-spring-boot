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
 */
package cn.felord.payment.wechat.v3;

import cn.felord.payment.PayException;
import cn.felord.payment.wechat.v3.model.CallbackParams;
import cn.felord.payment.wechat.v3.model.CouponConsumeData;
import cn.felord.payment.wechat.v3.model.ResponseSignVerifyParams;
import cn.felord.payment.wechat.v3.model.TransactionConsumeData;
import cn.felord.payment.wechat.v3.model.combine.CombineTransactionConsumeData;
import cn.felord.payment.wechat.v3.model.discountcard.*;
import cn.felord.payment.wechat.v3.model.payscore.PayScoreUserConfirmConsumeData;
import cn.felord.payment.wechat.v3.model.payscore.PayScoreUserPaidConsumeData;
import cn.felord.payment.wechat.v3.model.payscore.PayScoreUserPermissionConsumeData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 微信支付回调工具.
 * <p>
 * 注意：<strong>开发者应该保证回调调用的幂等性</strong>
 * <p>
 * 支付通知http应答码为200或204才会当作正常接收，当回调处理异常时，应答的HTTP状态码应为500，或者4xx。
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Slf4j
public class WechatPayCallback {
    /**
     * The constant MAPPER.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * The Signature provider.
     */
    private final SignatureProvider signatureProvider;
    /**
     * The Tenant id.
     */
    private final String tenantId;

    static {
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Instantiates a new Wechat pay callback.
     *
     * @param signatureProvider the signature provider
     * @param tenantId          the tenant id
     */
    public WechatPayCallback(SignatureProvider signatureProvider, String tenantId) {
        this.signatureProvider = signatureProvider;
        Assert.hasText(tenantId, "tenantId is required");
        this.tenantId = tenantId;
    }


    /**
     * 微信支付代金券核销回调.
     *
     * @param params              the params
     * @param consumeDataConsumer the consume data consumer
     * @return the map
     * @since 1.0.0.RELEASE
     */
    @SneakyThrows
    public Map<String, ?> couponCallback(ResponseSignVerifyParams params, Consumer<CouponConsumeData> consumeDataConsumer) {
        String data = this.callback(params, EventType.COUPON);
        CouponConsumeData consumeData = MAPPER.readValue(data, CouponConsumeData.class);
        consumeDataConsumer.accept(consumeData);
        Map<String, Object> responseBody = new HashMap<>(2);
        responseBody.put("code", 200);
        responseBody.put("message", "SUCCESS");
        return responseBody;

    }

    /**
     * 微信支付成功回调.
     * <p>
     * 无需开发者判断，只有扣款成功微信才会回调此接口
     *
     * @param params              the params
     * @param consumeDataConsumer the consume data consumer
     * @return the map
     * @since 1.0.0.RELEASE
     */
    @SneakyThrows
    public Map<String, ?> transactionCallback(ResponseSignVerifyParams params, Consumer<TransactionConsumeData> consumeDataConsumer) {
        String data = this.callback(params, EventType.TRANSACTION);
        TransactionConsumeData consumeData = MAPPER.readValue(data, TransactionConsumeData.class);
        consumeDataConsumer.accept(consumeData);
        return Collections.singletonMap("code", "SUCCESS");

    }

    /**
     * 微信合单支付成功回调.
     * <p>
     * 无需开发者判断，只有扣款成功微信才会回调此接口
     *
     * @param params              the params
     * @param consumeDataConsumer the consume data consumer
     * @return the map
     * @since 1.0.0.RELEASE
     */
    @SneakyThrows
    public Map<String, ?> combineTransactionCallback(ResponseSignVerifyParams params, Consumer<CombineTransactionConsumeData> consumeDataConsumer) {
        String data = this.callback(params, EventType.TRANSACTION);
        CombineTransactionConsumeData consumeData = MAPPER.readValue(data, CombineTransactionConsumeData.class);
        consumeDataConsumer.accept(consumeData);
        return Collections.singletonMap("code", "SUCCESS");

    }

    /**
     * 微信支付分确认订单、支付成功回调通知.
     * <p>
     * 该链接是通过商户 <a target= "_blank" href= "https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/payscore/chapter3_1.shtml">创建支付分订单</a> 提交notify_url参数，必须为https协议。如果链接无法访问，商户将无法接收到微信通知。 通知url必须为直接可访问的url，不能携带参数。示例： “https://pay.weixin.qq.com/wxpay/pay.action”
     *
     * @param params           the params
     * @param payScoreConsumer the pay score consumer
     * @return the map
     * @since 1.0.2.RELEASE
     */
    @SneakyThrows
    public Map<String, ?> payscoreUserOrderCallback(ResponseSignVerifyParams params, PayScoreConsumer payScoreConsumer) {
        CallbackParams callbackParams = resolve(params);
        String eventType = callbackParams.getEventType();

        if (Objects.equals(eventType, EventType.PAYSCORE_USER_CONFIRM.event)) {
            String data = this.decrypt(callbackParams);
            PayScoreUserConfirmConsumeData confirmConsumeData = MAPPER.readValue(data, PayScoreUserConfirmConsumeData.class);
            payScoreConsumer.getConfirmConsumeDataConsumer().accept(confirmConsumeData);
        } else if (Objects.equals(eventType, EventType.PAYSCORE_USER_PAID.event)) {
            String data = this.decrypt(callbackParams);
            PayScoreUserPaidConsumeData paidConsumeData = MAPPER.readValue(data, PayScoreUserPaidConsumeData.class);
            payScoreConsumer.getPaidConsumeDataConsumer().accept(paidConsumeData);
        } else {
            log.error("wechat pay event type is not matched, callbackParams {}", callbackParams);
            throw new PayException(" wechat pay event type is not matched");
        }

        return Collections.singletonMap("code", "SUCCESS");
    }

    /**
     * 授权/解除授权服务回调通知API.
     * <p>
     * 微信支付分通过授权/解除授权服务通知接口将用户过授权/解除授权服务消息通知给商户
     * <p>
     * 普通授权模式是通过[商户入驻配置申请表]提交service_notify_url设置，预授权模式是通过[商户预授权]提交的notify_url设置，必须为https协议。如果链接无法访问，商户将无法接收到微信通知。 通知url必须为直接可访问的url，不能携带参数。示例： “https://pay.weixin.qq.com/wxpay/pay.action”
     *
     * @param params              the params
     * @param consumeDataConsumer the consume data consumer
     * @return the map
     */
    @SneakyThrows
    public Map<String, ?> permissionCallback(ResponseSignVerifyParams params, Consumer<PayScoreUserPermissionConsumeData> consumeDataConsumer) {
        CallbackParams callbackParams = resolve(params);
        String eventType = callbackParams.getEventType();
        boolean closed;
        if (Objects.equals(eventType, EventType.PAYSCORE_USER_OPEN.event)) {
            closed = false;
        } else if (Objects.equals(eventType, EventType.PAYSCORE_USER_CLOSE.event)) {
            closed = true;
        } else {
            log.error("wechat pay event type is not matched, callbackParams {}", callbackParams);
            throw new PayException(" wechat pay event type is not matched");
        }
        String data = this.decrypt(callbackParams);
        PayScoreUserPermissionConsumeData consumeData = MAPPER.readValue(data, PayScoreUserPermissionConsumeData.class);
        consumeData.setClosed(closed);
        consumeDataConsumer.accept(consumeData);
        return Collections.singletonMap("code", "SUCCESS");
    }

    /**
     * 用户领卡、守约状态变化、扣费状态变化通知API
     * <p>
     * 用户领取优惠卡后或者用户守约状态发生变更后或扣费状态变化后，微信会把对应信息发送给商户。
     * <p>
     * 该链接是通过商户<a target= "_blank" href= "https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/discount-card/chapter3_1.shtml">预受理领卡请求</a>中提交notify_url参数，必须为https协议。如果链接无法访问，商户将无法接收到微信通知。 通知url必须为直接可访问的url，不能携带参数。示例： “https://pay.weixin.qq.com/wxpay/pay.action”
     *
     * @param params               the params
     * @param discountCardConsumer the discount card consumer
     * @return the map
     */
    @SneakyThrows
    public Map<String, ?> discountCardCallback(ResponseSignVerifyParams params, DiscountCardConsumer discountCardConsumer) {

        CallbackParams callbackParams = resolve(params);
        String eventType = callbackParams.getEventType();

        if (Objects.equals(eventType, EventType.DISCOUNT_CARD_AGREEMENT_ENDED.event)) {
            String data = this.decrypt(callbackParams);
            DiscountCardAgreementEndConsumeData agreementEndConsumeData = MAPPER.readValue(data, DiscountCardAgreementEndConsumeData.class);
            discountCardConsumer.getAgreementEndConsumeDataConsumer().accept(agreementEndConsumeData);
        } else if (Objects.equals(eventType, EventType.DISCOUNT_CARD_USER_ACCEPTED.event)) {
            String data = this.decrypt(callbackParams);
            DiscountCardAcceptedConsumeData acceptedConsumeData = MAPPER.readValue(data, DiscountCardAcceptedConsumeData.class);
            discountCardConsumer.getAcceptedConsumeDataConsumer().accept(acceptedConsumeData);
        }  else if (Objects.equals(eventType, EventType.DISCOUNT_CARD_USER_PAID.event)) {
            String data = this.decrypt(callbackParams);
            DiscountCardUserPaidConsumeData paidConsumeData = MAPPER.readValue(data, DiscountCardUserPaidConsumeData.class);
            discountCardConsumer.getCardUserPaidConsumeDataConsumer().accept(paidConsumeData);
        } else {
            log.error("wechat pay event type is not matched, callbackParams {}", callbackParams);
            throw new PayException(" wechat pay event type is not matched");
        }
        return Collections.singletonMap("code", "SUCCESS");
    }


    /**
     * Callback.
     *
     * @param params    the params
     * @param eventType the event type
     * @return the string
     */
    @SneakyThrows
    private String callback(ResponseSignVerifyParams params, EventType eventType) {
        CallbackParams callbackParams = this.resolve(params);

        if (!Objects.equals(callbackParams.getEventType(), eventType.event)) {
            log.error("wechat pay event type is not matched, callbackParams {}", callbackParams);
            throw new PayException(" wechat pay event type is not matched");
        }
        return this.decrypt(callbackParams);
    }

    /**
     * Resolve callback params.
     *
     * @param params the params
     * @return the callback params
     * @throws JsonProcessingException the json processing exception
     * @since 1.0.2.RELEASE
     */
    private CallbackParams resolve(ResponseSignVerifyParams params) throws JsonProcessingException {
        if (signatureProvider.responseSignVerify(params)) {
            return MAPPER.readValue(params.getBody(), CallbackParams.class);
        }
        throw new PayException("invalid wechat pay callback");
    }

    /**
     * Decrypt.
     *
     * @param callbackParams the callback params
     * @return the string
     * @since 1.0.2.RELEASE
     */
    private String decrypt(CallbackParams callbackParams) {
        CallbackParams.Resource resource = callbackParams.getResource();
        String associatedData = resource.getAssociatedData();
        String nonce = resource.getNonce();
        String ciphertext = resource.getCiphertext();
        String data = signatureProvider.decryptResponseBody(tenantId, associatedData, nonce, ciphertext);
        Assert.hasText(data, "decryptData is required");
        return data;
    }


    /**
     * 事件类型用于处理回调.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    enum EventType {

        /**
         * 微信支付分确认订单事件.
         *
         * @since 1.0.2.RELEASE
         */
        PAYSCORE_USER_CONFIRM("PAYSCORE.USER_CONFIRM"),

        /**
         * 微信支付分用户支付成功订单事件.
         *
         * @since 1.0.2.RELEASE
         */
        PAYSCORE_USER_PAID("PAYSCORE.USER_PAID"),

        /**
         * 微信支付分授权事件.
         *
         * @since 1.0.2.RELEASE
         */
        PAYSCORE_USER_OPEN("PAYSCORE.USER_OPEN_SERVICE"),

        /**
         * 微信支付分解除授权事件.
         *
         * @since 1.0.2.RELEASE
         */
        PAYSCORE_USER_CLOSE("PAYSCORE.USER_CLOSE_SERVICE"),

        /**
         * 用户领取微信先享卡事件.
         *
         * @since 1.0.2.RELEASE
         */
        DISCOUNT_CARD_USER_ACCEPTED("DISCOUNT_CARD.USER_ACCEPTED"),

        /**
         * 微信先享卡守约状态变化事件.
         *
         * @since 1.0.2.RELEASE
         */
        DISCOUNT_CARD_AGREEMENT_ENDED("DISCOUNT_CARD.AGREEMENT_ENDED"),

        /**
         * 微信先享卡扣费状态变化事件.
         *
         * @since 1.0.2.RELEASE
         */
        DISCOUNT_CARD_USER_PAID("DISCOUNT_CARD.USER_PAID"),

        /**
         * 优惠券核销事件.
         *
         * @since 1.0.0.RELEASE
         */
        COUPON("COUPON.USE"),
        /**
         * 支付成功事件.
         *
         * @since 1.0.0.RELEASE
         */
        TRANSACTION("TRANSACTION.SUCCESS");

        /**
         * The Event.
         */
        private final String event;

        /**
         * Instantiates a new Event type.
         *
         * @param event the event
         */
        EventType(String event) {
            this.event = event;
        }
    }

}
