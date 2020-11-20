package com.enongm.dianji.payment.wechat.v3.filter;


import com.enongm.dianji.payment.PayException;
import com.enongm.dianji.payment.wechat.WechatPayResponseErrorHandler;
import com.enongm.dianji.payment.wechat.v3.PayFilter;
import com.enongm.dianji.payment.wechat.v3.PayFilterChain;
import com.enongm.dianji.payment.wechat.v3.SignatureProvider;
import com.enongm.dianji.payment.wechat.v3.WechatRequestEntity;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

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
    private final RestOperations restOperations;
    private final SignatureProvider signatureProvider;


    public HttpRequestFilter(SignatureProvider signatureProvider) {
        this.signatureProvider = signatureProvider;
        RestTemplate restTemplate = new RestTemplate();
        DefaultResponseErrorHandler errorHandler = new WechatPayResponseErrorHandler();
        restTemplate.setErrorHandler(errorHandler);
        this.restOperations = restTemplate;
    }

    @Override
    public void doFilter(WechatRequestEntity<?> requestEntity, PayFilterChain chain) {

        ResponseEntity<ObjectNode> responseEntity = restOperations.exchange(requestEntity, ObjectNode.class);
        HttpHeaders headers = responseEntity.getHeaders();
        ObjectNode body = responseEntity.getBody();

        if (Objects.isNull(body)) {
            throw new IllegalStateException("cant obtain response body");
        }
        // 微信请求回调id
        // String RequestId = response.header("Request-ID");
        // 微信平台证书序列号 用来取微信平台证书
        String wechatpaySerial = headers.getFirst("Wechatpay-Serial");
        //获取应答签名
        String wechatpaySignature = headers.getFirst("Wechatpay-Signature");
        //构造验签名串
        String wechatpayTimestamp = headers.getFirst("Wechatpay-Timestamp");
        String wechatpayNonce = headers.getFirst("Wechatpay-Nonce");

        // 验证微信服务器签名
        if (signatureProvider.responseSignVerify(wechatpaySerial,
                wechatpaySignature,
                wechatpayTimestamp,
                wechatpayNonce,
                body.toString())) {
            Consumer<ResponseEntity<ObjectNode>> responseConsumer = requestEntity.getResponseBodyConsumer();
            if (Objects.nonNull(responseConsumer)) {
                // 验证通过消费
                responseConsumer.accept(responseEntity);
            }
        } else {
            throw new PayException("wechat pay signature failed");
        }
    }
}
