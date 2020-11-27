package com.enongm.dianji.payment.wechat.v3;


import com.enongm.dianji.payment.PayException;
import com.enongm.dianji.payment.wechat.WechatPayResponseErrorHandler;
import com.enongm.dianji.payment.wechat.enumeration.WechatPayV3Type;
import com.enongm.dianji.payment.wechat.v3.model.ResponseSignVerifyParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * The type Wechat pay client.
 *
 * @author Dax
 * @since 11 :43
 */
public class WechatPayClient {
    private final SignatureProvider signatureProvider;

    /**
     * Instantiates a new Wechat pay service.
     *
     * @param signatureProvider the signature provider
     */
    public WechatPayClient(SignatureProvider signatureProvider) {
        this.signatureProvider = signatureProvider;
    }


    /**
     * 构造 {@link WechatRequestEntity}.
     *
     * @param <M>             the type parameter
     * @param wechatPayV3Type the v 3 pay type
     * @param m               the m
     * @return the executor
     */
    public <M> Executor<M> withType(WechatPayV3Type wechatPayV3Type, M m) {
        return new Executor<>(wechatPayV3Type, m, this.signatureProvider);
    }


    /**
     * The type Executor.
     *
     * @param <M> the type parameter
     */
    public static class Executor<M> {
        /**
         * The V 3 pay type.
         */
        private final WechatPayV3Type wechatPayV3Type;
        private final RestOperations restOperations;
        private final SignatureProvider signatureProvider;
        private final M model;

        /**
         * The Request entity bi function.
         */
        private BiFunction<WechatPayV3Type, M, RequestEntity<?>> requestEntityBiFunction;

        /**
         * The Response body consumer.
         */
        private Consumer<ResponseEntity<ObjectNode>> responseBodyConsumer;

        /**
         * Instantiates a new Executor.
         *
         * @param wechatPayV3Type   the v 3 pay type
         * @param model             the model
         * @param signatureProvider the signature provider
         */
        public Executor(WechatPayV3Type wechatPayV3Type,
                        M model,
                        SignatureProvider signatureProvider) {
            this.wechatPayV3Type = wechatPayV3Type;
            this.model = model;
            this.signatureProvider = signatureProvider;
            RestTemplate restTemplate = new RestTemplate();
            DefaultResponseErrorHandler errorHandler = new WechatPayResponseErrorHandler();
            restTemplate.setErrorHandler(errorHandler);
            this.restOperations = restTemplate;
        }

        /**
         * Function executor.
         *
         * @param requestEntityBiFunction the request entity bifunction
         * @return the executor
         */
        public Executor<M> function(BiFunction<WechatPayV3Type, M, RequestEntity<?>> requestEntityBiFunction) {
            this.requestEntityBiFunction = requestEntityBiFunction;
            return this;
        }

        /**
         * Consumer executor.
         *
         * @param responseBodyConsumer the response body consumer
         * @return the executor
         */
        public Executor<M> consumer(Consumer<ResponseEntity<ObjectNode>> responseBodyConsumer) {
            this.responseBodyConsumer = responseBodyConsumer;
            return this;
        }


        /**
         * Request.
         */
        @SneakyThrows
        public void request() {
            RequestEntity<?> requestEntity = this.requestEntityBiFunction.apply(this.wechatPayV3Type, this.model);
            WechatRequestEntity<?> wechatRequestEntity = WechatRequestEntity.of(requestEntity, this.responseBodyConsumer);
            this.doExecute(this.header(wechatRequestEntity));
        }


        /**
         * 构造私钥签名.
         *
         * @param <T>           the type parameter
         * @param requestEntity the request entity
         */
        private <T> WechatRequestEntity<T> header(WechatRequestEntity<T> requestEntity) {

            UriComponents uri = UriComponentsBuilder.fromUri(requestEntity.getUrl()).build();
            String canonicalUrl = uri.getPath();
            String encodedQuery = uri.getQuery();

            if (encodedQuery != null) {
                canonicalUrl += "?" + encodedQuery;
            }
            // 签名
            HttpMethod httpMethod = requestEntity.getMethod();
            Assert.notNull(httpMethod, "httpMethod is required");

            String body = requestEntity.hasBody() ? Objects.requireNonNull(requestEntity.getBody()).toString() : "";
            String authorization = signatureProvider.requestSign(httpMethod.name(), canonicalUrl, body);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.addAll(requestEntity.getHeaders());
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            // 兼容图片上传，自定义优先级最高
            if (Objects.isNull(httpHeaders.getContentType())) {
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            }
            httpHeaders.add("Authorization", authorization);
            httpHeaders.add("User-Agent", "X-Pay-Service");

            return requestEntity.headers(httpHeaders);

        }


        private <T> void doExecute(WechatRequestEntity<T> requestEntity) {

            ResponseEntity<ObjectNode> responseEntity = restOperations.exchange(requestEntity, ObjectNode.class);
            HttpHeaders headers = responseEntity.getHeaders();
            ObjectNode body = responseEntity.getBody();
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                throw new PayException("wechat pay server error,result : " + body);
            }
            if (Objects.isNull(body)) {
                throw new IllegalStateException("cant obtain response body");
            }


            ResponseSignVerifyParams params = new ResponseSignVerifyParams();
            // 微信请求回调id
            // String RequestId = response.header("Request-ID");
            // 微信平台证书序列号 用来取微信平台证书
            params.setWechatpaySerial(headers.getFirst("Wechatpay-Serial"));
            //获取应答签名
            params.setWechatpaySignature(headers.getFirst("Wechatpay-Signature"));
            //构造验签名串
            params.setWechatpayTimestamp(headers.getFirst("Wechatpay-Timestamp"));
            params.setWechatpayNonce(headers.getFirst("Wechatpay-Nonce"));
            params.setBody(body.toString());

            // 验证微信服务器签名
            if (signatureProvider.responseSignVerify(params)) {
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

}
