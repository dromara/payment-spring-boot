package com.enongm.dianji.payment.wechat.v3.filter;


import com.enongm.dianji.payment.PayException;
import com.enongm.dianji.payment.wechat.v3.PayFilter;
import com.enongm.dianji.payment.wechat.v3.PayFilterChain;
import com.enongm.dianji.payment.wechat.v3.SignatureProvider;
import com.enongm.dianji.payment.wechat.v3.WechatPayRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 微信支付用来请求微信支付服务器
 *
 * @author Dax
 * @since 10:42
 */
@Slf4j
public class HttpRequestFilter implements PayFilter {
    private final OkHttpClient client;
    private final SignatureProvider signatureProvider;


    public HttpRequestFilter(SignatureProvider signatureProvider) {
        this.signatureProvider = signatureProvider;
        this.client = new OkHttpClient();

    }

    public HttpRequestFilter(SignatureProvider signatureProvider, OkHttpClient client) {
        this.signatureProvider = signatureProvider;
        this.client = client;
    }


    @Override
    public void doFilter(WechatPayRequest request, PayFilterChain chain) {

        Request.Builder builder = new Request.Builder()
                .url(request.url())
                .headers(Headers.of(request.getHeaders()));

        if (!request.getV3PayType().method().equals("GET")) {
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("application/json"), request.getBody());
            builder.method(request.getV3PayType().method(), requestBody);
        }

        Request httpRequest = builder.build();

        try {
            Response response = client.newCall(httpRequest).execute();
            ResponseBody responseBody = response.body();

            if (Objects.isNull(responseBody)) {
                throw new IllegalStateException("cant obtain response body");
            }
            // 微信请求回调id
//            String RequestId = response.header("Request-ID");
            // 微信平台证书序列号 用来取微信平台证书
            String wechatpaySerial = response.header("Wechatpay-Serial");
            //获取应答签名
            String wechatpaySignature = response.header("Wechatpay-Signature");
            String body = responseBody.string();

            //构造验签名串
            String wechatpayTimestamp = response.header("Wechatpay-Timestamp");
            String wechatpayNonce = response.header("Wechatpay-Nonce");

            // 验证微信服务器签名
            if (signatureProvider.responseSignVerify(wechatpaySerial, wechatpaySignature, wechatpayTimestamp, wechatpayNonce, body)) {
                Consumer<String> responseConsumer = request.getResponseBodyConsumer();
                if (Objects.nonNull(responseConsumer)) {
                    // 验证通过消费
                    responseConsumer.accept(body);
                }
            } else {
                throw new PayException("wechat pay signature failed");
            }

        } catch (IOException e) {
            throw new PayException("wechat pay http request failed");
        }
    }
}
