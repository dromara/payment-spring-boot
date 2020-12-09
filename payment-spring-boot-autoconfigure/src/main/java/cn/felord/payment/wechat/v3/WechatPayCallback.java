package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.v3.model.CouponConsumeData;
import cn.felord.payment.wechat.v3.model.ResponseSignVerifyParams;
import cn.felord.payment.PayException;
import cn.felord.payment.wechat.v3.model.CallbackParams;
import cn.felord.payment.wechat.v3.model.TransactionConsumeData;
import com.fasterxml.jackson.annotation.JsonInclude;
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
 *
 * @author Dax
 * @since 10 :21
 */
@Slf4j
public class WechatPayCallback {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final SignatureProvider signatureProvider;
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
     * @param params                    the params
     * @param couponConsumeDataConsumer the coupon consume data consumer
     * @return the map
     */
    @SneakyThrows
    public Map<String, ?> couponCallback(ResponseSignVerifyParams params, Consumer<CouponConsumeData> couponConsumeDataConsumer) {
        String data = callback(params, EventType.COUPON);
        CouponConsumeData couponConsumeData = MAPPER.readValue(data, CouponConsumeData.class);
        couponConsumeDataConsumer.accept(couponConsumeData);
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
     * @param params                    the params
     * @param couponConsumeDataConsumer the coupon consume data consumer
     * @return the map
     */
    @SneakyThrows
    public Map<String, ?> transactionCallback(ResponseSignVerifyParams params, Consumer<TransactionConsumeData> couponConsumeDataConsumer) {
        String data = callback(params, EventType.TRANSACTION);
        TransactionConsumeData transactionConsumeData = MAPPER.readValue(data, TransactionConsumeData.class);
        couponConsumeDataConsumer.accept(transactionConsumeData);
        return Collections.singletonMap("code", "SUCCESS");

    }


    @SneakyThrows
    private String callback(ResponseSignVerifyParams params, EventType eventType) {
        if (signatureProvider.responseSignVerify(params)) {
            CallbackParams callbackParams = MAPPER.readValue(params.getBody(), CallbackParams.class);

            if (!Objects.equals(callbackParams.getEventType(), eventType.event)) {
                log.error("wechat pay event type is not matched, callbackParams {}", callbackParams);
                throw new PayException(" wechat pay event type is not matched");
            }
            CallbackParams.Resource resource = callbackParams.getResource();
            String associatedData = resource.getAssociatedData();
            String nonce = resource.getNonce();
            String ciphertext = resource.getCiphertext();
            String data = signatureProvider.decryptResponseBody(tenantId, associatedData, nonce, ciphertext);
            Assert.hasText(data, "decryptData is required");
            return data;
        }
        throw new PayException("invalid wechat pay callback");
    }

    /**
     * 事件类型用于处理回调.
     */
    enum EventType {
        /**
         * 优惠券核销事件.
         */
        COUPON("COUPON.USE"),
        /**
         * 支付成功事件.
         */
        TRANSACTION("TRANSACTION.SUCCESS");

        private final String event;

        EventType(String event) {
            this.event = event;
        }
    }


}
