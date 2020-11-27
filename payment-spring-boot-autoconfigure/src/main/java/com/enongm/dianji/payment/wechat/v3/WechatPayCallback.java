package com.enongm.dianji.payment.wechat.v3;

import com.enongm.dianji.payment.PayException;
import com.enongm.dianji.payment.wechat.v3.model.CallbackParams;
import com.enongm.dianji.payment.wechat.v3.model.CouponConsumeData;
import com.enongm.dianji.payment.wechat.v3.model.ResponseSignVerifyParams;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.SneakyThrows;
import org.springframework.util.Assert;

/**
 * @author Dax
 * @since 10:21
 */
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
    public CouponConsumeData wechatPayCouponCallback(ResponseSignVerifyParams params) {

        if (signatureProvider.responseSignVerify(params)) {
            CallbackParams callbackParams = MAPPER.readValue(params.getBody(), CallbackParams.class);

            CallbackParams.Resource resource = callbackParams.getResource();
            String associatedData = resource.getAssociatedData();
            String nonce = resource.getNonce();
            String ciphertext = resource.getCiphertext();
            String data = signatureProvider.decryptResponseBody(associatedData, nonce, ciphertext);
            Assert.hasText(data, "decryptData is required");
            return MAPPER.readValue(data, CouponConsumeData.class);
        }
        throw new PayException("invalid wechat pay coupon callback");
    }


}
