package com.enongm.dianji.payment.wechat;



import com.enongm.dianji.payment.wechat.v3.SignatureProvider;
import com.enongm.dianji.payment.wechat.v3.WechatPayRequest;
import com.enongm.dianji.payment.wechat.v3.filter.BodyMergeFilter;
import com.enongm.dianji.payment.wechat.v3.filter.HeaderFilter;
import com.enongm.dianji.payment.wechat.v3.filter.HttpRequestFilter;
import com.enongm.dianji.payment.wechat.v3.filter.WechatServerFilter;
import com.enongm.dianji.payment.wechat.v3.model.BaseModel;
import com.enongm.dianji.payment.wechat.v3.model.WechatMetaBean;

import java.util.function.Function;

/**
 * The type Wechat pay service.
 *
 * @author Dax
 * @since 11 :43
 */
public class WechatPayV3Service {
    private final PayFilterChain payFilterChain;

    /**
     * Instantiates a new Wechat pay service.
     *
     * @param payFilterChain the pay filter chain
     */
    public WechatPayV3Service(PayFilterChain payFilterChain) {
        this.payFilterChain = payFilterChain;
    }

    /**
     * Instantiates a new Wechat pay service.
     *
     * @param signatureProvider the signature provider
     */
    public WechatPayV3Service(SignatureProvider signatureProvider) {
        WechatMetaBean wechatMetaBean = signatureProvider.getWechatMetaBean();
        DefaultPayFilterChain defaultPayFilterChain = new DefaultPayFilterChain();
        // 微信服务器选择
        defaultPayFilterChain.register(new WechatServerFilter());
        // 对请求体注入业务无关参数
        defaultPayFilterChain.register(new BodyMergeFilter(wechatMetaBean));
        // 构造私钥签名
        defaultPayFilterChain.register(new HeaderFilter(signatureProvider));
        defaultPayFilterChain.register(new HttpRequestFilter(signatureProvider));

        this.payFilterChain = defaultPayFilterChain;
    }


    /**
     * Exec executor.
     *
     * @param <M>             the type parameter
     * @param <R>             the type parameter
     * @param requestFunction the request function
     * @return the executor
     */
    public <M extends BaseModel, R extends WechatPayRequest> Executor<M, R> request(Function<M, R> requestFunction) {
        return new Executor<>(this.payFilterChain, requestFunction);
    }


    /**
     * The type Executor.
     *
     * @param <M> the type parameter
     * @param <R> the type parameter
     */
    public static class Executor<M extends BaseModel, R extends WechatPayRequest> {
        /**
         * The Pay filter chain.
         */
        PayFilterChain payFilterChain;
        /**
         * The Request function.
         */
        Function<M, R> requestFunction;

        /**
         * Instantiates a new Executor.
         *
         * @param payFilterChain  the pay filter chain
         * @param requestFunction the request function
         */
        public Executor(PayFilterChain payFilterChain, Function<M, R> requestFunction) {
            this.payFilterChain = payFilterChain;
            this.requestFunction = requestFunction;
        }

        /**
         * With model.
         *
         * @param model the model
         */
        public void withModel(M model) {
            payFilterChain.doChain(requestFunction.apply(model));
        }
    }

}
