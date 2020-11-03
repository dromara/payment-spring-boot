package com.enongm.dianji.payment.wechat.v3;

import com.enongm.dianji.payment.wechat.enumeration.V3PayType;

import java.util.function.Consumer;

/**
 * @author Dax
 * @since 15:27
 */
public class V3PayTypeBodyAndConsumer {
    private final V3PayType payType;
    private final String jsonBody;
    private final Consumer<String> responseBodyConsumer;

    public V3PayTypeBodyAndConsumer(V3PayType payType, String jsonBody, Consumer<String> responseBodyConsumer) {
        this.payType = payType;
        this.jsonBody = jsonBody;
        this.responseBodyConsumer = responseBodyConsumer;
    }

    public V3PayType getPayType() {
        return payType;
    }

    public String getJsonBody() {
        return jsonBody;
    }

    public Consumer<String> getResponseBodyConsumer() {
        return responseBodyConsumer;
    }
}
