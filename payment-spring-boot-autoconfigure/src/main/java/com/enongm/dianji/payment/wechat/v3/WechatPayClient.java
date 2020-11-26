package com.enongm.dianji.payment.wechat.v3;


import com.enongm.dianji.payment.wechat.enumeration.WechatPayV3Type;
import com.enongm.dianji.payment.wechat.v3.filter.HeaderFilter;
import com.enongm.dianji.payment.wechat.v3.filter.HttpRequestFilter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * The type Wechat pay client.
 *
 * @author Dax
 * @since 11 :43
 */
public class WechatPayClient {
    private final PayFilterChain payFilterChain;

    /**
     * Instantiates a new Wechat pay service.
     *
     * @param signatureProvider the signature provider
     */
    public WechatPayClient(SignatureProvider signatureProvider) {
        DefaultPayFilterChain defaultPayFilterChain = new DefaultPayFilterChain();
        // 构造私钥签名
        defaultPayFilterChain.register(new HeaderFilter(signatureProvider));
        // 向微信支付服务器发起请求
        defaultPayFilterChain.register(new HttpRequestFilter(signatureProvider));
        this.payFilterChain = defaultPayFilterChain;
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
        return new Executor<>(wechatPayV3Type,m ,this.payFilterChain);
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

        /**
         * The Pay filter chain.
         */
        private final PayFilterChain payFilterChain;
        private final M model;

        /**
         * The Request entity bi function.
         */
        private BiFunction<WechatPayV3Type, M, RequestEntity<?>> requestEntityBiFunction;

        /**
         * The Response body consumer.
         */
        private  Consumer<ResponseEntity<ObjectNode>> responseBodyConsumer;

        /**
         * Instantiates a new Executor.
         *
         * @param wechatPayV3Type the v 3 pay type
         * @param model           the model
         * @param payFilterChain  the pay filter chain
         */
        public Executor(WechatPayV3Type wechatPayV3Type,
                        M model,
                        PayFilterChain payFilterChain) {
            this.wechatPayV3Type = wechatPayV3Type;
            this.model = model;
            this.payFilterChain = payFilterChain;
        }

        /**
         * Function executor.
         *
         * @param requestEntityBiFunction the request entity bi function
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
            payFilterChain.doChain(WechatRequestEntity.of(requestEntity, this.responseBodyConsumer));
        }
    }

}
