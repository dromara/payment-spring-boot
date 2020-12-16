package cn.felord.payment.wechat.v3;

import cn.felord.payment.PayException;
import cn.felord.payment.wechat.v3.model.*;
import cn.felord.payment.wechat.v3.model.payscore.PayScoreUserConfirmConsumeData;
import cn.felord.payment.wechat.v3.model.payscore.PayScoreUserPaidConsumeData;
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
        CouponConsumeData couponConsumeData = MAPPER.readValue(data, CouponConsumeData.class);
        consumeDataConsumer.accept(couponConsumeData);
        Map<String, Object> responseBody = new HashMap<>();
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
        TransactionConsumeData transactionConsumeData = MAPPER.readValue(data, TransactionConsumeData.class);
        consumeDataConsumer.accept(transactionConsumeData);
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
        CombineTransactionConsumeData combineTransactionConsumeData = MAPPER.readValue(data, CombineTransactionConsumeData.class);
        consumeDataConsumer.accept(combineTransactionConsumeData);
        return Collections.singletonMap("code", "SUCCESS");

    }


    /**
     * 微信支付分确认订单回调通知.
     * <p>
     * 该链接是通过商户[创建支付分订单]提交notify_url参数，必须为https协议。如果链接无法访问，商户将无法接收到微信通知。 通知url必须为直接可访问的url，不能携带参数。示例： “https://pay.weixin.qq.com/wxpay/pay.action”
     *
     * @param params              the params
     * @param consumeDataConsumer the consume data consumer
     * @return the map
     * @since 1.0.2.RELEASE
     */
    @SneakyThrows
    public Map<String, ?> payscoreUserConfirmCallback(ResponseSignVerifyParams params, Consumer<PayScoreUserConfirmConsumeData> consumeDataConsumer) {
        String data = this.callback(params, EventType.PAYSCORE_USER_CONFIRM);
        PayScoreUserConfirmConsumeData payScoreUserConfirmConsumeData = MAPPER.readValue(data, PayScoreUserConfirmConsumeData.class);
        consumeDataConsumer.accept(payScoreUserConfirmConsumeData);
        return Collections.singletonMap("code", "SUCCESS");
    }

    /**
     * 微信支付分支付成功回调通知API.
     * <p>
     * 请求URL：该链接是通过商户[创建支付分订单]提交notify_url参数，必须为https协议。如果链接无法访问，商户将无法接收到微信通知。 通知url必须为直接可访问的url，不能携带参数。示例： “https://pay.weixin.qq.com/wxpay/pay.action”
     *
     * @param params              the params
     * @param consumeDataConsumer the consume data consumer
     * @return the map
     * @since 1.0.2.RELEASE
     */
    @SneakyThrows
    public Map<String, ?> payscoreUserPaidCallback(ResponseSignVerifyParams params, Consumer<PayScoreUserPaidConsumeData> consumeDataConsumer) {
        String data = this.callback(params, EventType.PAYSCORE_USER_PAID);
        PayScoreUserPaidConsumeData payScoreUserPaidConsumeData = MAPPER.readValue(data, PayScoreUserPaidConsumeData.class);
        consumeDataConsumer.accept(payScoreUserPaidConsumeData);
        return Collections.singletonMap("code", "SUCCESS");
    }

    /**
     * Permission callback map.
     *
     * @param params              the params
     * @param consumeDataConsumer the consume data consumer
     * @return the map
     */
    @SneakyThrows
    public Map<String, ?> permissionCallback(ResponseSignVerifyParams params, Consumer<PayScoreUserPaidConsumeData> consumeDataConsumer) {


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
        CallbackParams callbackParams = resolve(params);

        if (!Objects.equals(callbackParams.getEventType(), eventType.event)) {
            log.error("wechat pay event type is not matched, callbackParams {}", callbackParams);
            throw new PayException(" wechat pay event type is not matched");
        }
        return decrypt(callbackParams);
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
        PAYSCORE_USER_OPEN("PAYSCORE.USER_CLOSE_SERVICE"),
        /**
         * 微信支付分解除授权事件.
         *
         * @since 1.0.2.RELEASE
         */
        PAYSCORE_USER_CLOSE("PAYSCORE.USER_CLOSE_SERVICE"),
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
