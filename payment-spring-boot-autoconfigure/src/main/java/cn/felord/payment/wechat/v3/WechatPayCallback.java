package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.v3.model.CouponConsumeData;
import cn.felord.payment.wechat.v3.model.ResponseSignVerifyParams;
import cn.felord.payment.PayException;
import cn.felord.payment.wechat.v3.model.CallbackParams;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Dax
 * @since 10:21
 */
@Slf4j
public class WechatPayCallback {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final SignatureProvider signatureProvider;

    static {
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public WechatPayCallback(SignatureProvider signatureProvider) {
        this.signatureProvider = signatureProvider;
    }


    @SneakyThrows
    public Map<String, ?> wechatPayCouponCallback(ResponseSignVerifyParams params, Consumer<CouponConsumeData> couponConsumeDataConsumer) {

        if (signatureProvider.responseSignVerify(params)) {
            CallbackParams callbackParams = MAPPER.readValue(params.getBody(), CallbackParams.class);

            CallbackParams.Resource resource = callbackParams.getResource();
            String associatedData = resource.getAssociatedData();
            String nonce = resource.getNonce();
            String ciphertext = resource.getCiphertext();
            String data = signatureProvider.decryptResponseBody(associatedData, nonce, ciphertext);
            Assert.hasText(data, "decryptData is required");
            CouponConsumeData couponConsumeData = MAPPER.readValue(data, CouponConsumeData.class);
            couponConsumeDataConsumer.accept(couponConsumeData);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("code", 200);
            responseBody.put("message", "核销成功");
            return responseBody;
        }
        throw new PayException("invalid wechat pay coupon callback");
    }


}
