package cn.felord.payment.wechat.v3;


import cn.felord.payment.PayException;
import cn.felord.payment.wechat.WechatPayResponseErrorHandler;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.ResponseSignVerifyParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * The type Wechat pay client.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class WechatPayClient {
    /**
     * The Signature provider.
     */
    private final SignatureProvider signatureProvider;
    /**
     * The Rest operations.
     */
    private RestOperations restOperations;

    /**
     * Instantiates a new Wechat pay service.
     *
     * @param signatureProvider the signature provider
     */
    public WechatPayClient(SignatureProvider signatureProvider) {
        this.signatureProvider = signatureProvider;
        applyDefaultRestTemplate();
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
        return new Executor<>(wechatPayV3Type, m, this.signatureProvider, this.restOperations);
    }


    /**
     * The type Executor.
     *
     * @param <M> the type parameter
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    public static class Executor<M> {
        /**
         * The V3 pay type.
         */
        private final WechatPayV3Type wechatPayV3Type;
        /**
         * The Rest operations.
         */
        private final RestOperations restOperations;
        /**
         * The Signature provider.
         */
        private final SignatureProvider signatureProvider;
        /**
         * The Model.
         */
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
         * @param restOperations    the rest operations
         */
        Executor(WechatPayV3Type wechatPayV3Type,
                 M model,
                 SignatureProvider signatureProvider, RestOperations restOperations) {
            this.wechatPayV3Type = wechatPayV3Type;
            this.model = model;
            this.signatureProvider = signatureProvider;
            this.restOperations = restOperations;
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
        public void request() {
            RequestEntity<?> requestEntity = this.requestEntityBiFunction.apply(this.wechatPayV3Type, this.model);
            WechatRequestEntity<?> wechatRequestEntity = WechatRequestEntity.of(requestEntity, this.responseBodyConsumer);
            this.doExecute(this.header(wechatRequestEntity));
        }


        /**
         * Download string.
         *
         * @return the string
         */
        public String download() {
            RequestEntity<?> requestEntity = this.requestEntityBiFunction.apply(this.wechatPayV3Type, this.model);
            WechatRequestEntity<?> wechatRequestEntity = WechatRequestEntity.of(requestEntity, this.responseBodyConsumer);
            return this.doDownload(this.header(wechatRequestEntity));
        }


        /**
         * 构造私钥签名.
         *
         * @param <T>           the type parameter
         * @param requestEntity the request entity
         * @return the wechat request entity
         */
        private <T> WechatRequestEntity<T> header(WechatRequestEntity<T> requestEntity) {

            UriComponents uri = UriComponentsBuilder.fromUri(requestEntity.getUrl()).build();
            String canonicalUrl = uri.getPath();
            String encodedQuery = uri.getQuery();
            Assert.notNull(canonicalUrl, "canonicalUrl is required");
            if (encodedQuery != null) {
                canonicalUrl += "?" + encodedQuery;
            }
            // 签名
            HttpMethod httpMethod = requestEntity.getMethod();

            Assert.notNull(httpMethod, "httpMethod is required");
            HttpHeaders headers = requestEntity.getHeaders();

            T entityBody = requestEntity.getBody();
            String body = requestEntity.hasBody() ? Objects.requireNonNull(entityBody).toString() : "";
            if (WechatPayV3Type.MARKETING_IMAGE_UPLOAD.pattern().contains(canonicalUrl)) {
                body = Objects.requireNonNull(headers.get("Meta-Info")).get(0);
            }

            String tenantId = Objects.requireNonNull(headers.get("Pay-TenantId")).get(0);
            String authorization = signatureProvider.requestSign(tenantId, httpMethod.name(), canonicalUrl, body);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.addAll(headers);
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            // 兼容图片上传，自定义优先级最高
            if (Objects.isNull(httpHeaders.getContentType())) {
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            }
            httpHeaders.add("Authorization", authorization);
            httpHeaders.add("User-Agent", "X-Pay-Service");
            httpHeaders.remove("Meta-Info");
            httpHeaders.remove("Pay-TenantId");
            return requestEntity.headers(httpHeaders);

        }


        /**
         * Do execute.
         *
         * @param <T>           the type parameter
         * @param requestEntity the request entity
         */
        private <T> void doExecute(WechatRequestEntity<T> requestEntity) {

            ResponseEntity<ObjectNode> responseEntity = restOperations.exchange(requestEntity, ObjectNode.class);
            HttpHeaders headers = responseEntity.getHeaders();
            ObjectNode body = responseEntity.getBody();
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (!statusCode.is2xxSuccessful()) {
                throw new PayException("wechat pay server error,statusCode " + statusCode + ",result : " + body);
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

            String content = Objects.isNull(body) ? "" : body.toString();
            params.setBody(content);

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

        /**
         * Do download string.
         *
         * @param <T>           the type parameter
         * @param requestEntity the request entity
         * @return the string
         */
        private <T> String doDownload(WechatRequestEntity<T> requestEntity) {

            ResponseEntity<String> responseEntity = restOperations.exchange(requestEntity, String.class);

            String body = responseEntity.getBody();
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (!statusCode.is2xxSuccessful()) {
                throw new PayException("wechat pay server error,statusCode " + statusCode + ",result : " + body);
            }
            if (Objects.isNull(body)) {
                throw new PayException("cant obtain wechat response body");
            }
            return body;
        }

    }

    /**
     * Signature provider signature provider.
     *
     * @return the signature provider
     */
    public SignatureProvider signatureProvider() {
        return signatureProvider;
    }

    /**
     * Apply default rest template.
     */
    private void applyDefaultRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        DefaultResponseErrorHandler errorHandler = new WechatPayResponseErrorHandler();
        restTemplate.setErrorHandler(errorHandler);
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

        messageConverters.removeIf(httpMessageConverter -> httpMessageConverter instanceof AllEncompassingFormHttpMessageConverter);
        messageConverters.add(new ExtensionFormHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        this.restOperations = restTemplate;
    }
}
