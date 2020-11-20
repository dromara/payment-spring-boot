package com.enongm.dianji.payment.wechat.v3;


/**
 * The interface Pay filter chain.
 *
 * @author Dax
 * @since 16 :11
 */
public interface PayFilterChain {

    /**
     * Do chain.
     *
     * @param requestEntity the request entity
     */
    void doChain(WechatRequestEntity<?> requestEntity);

    /**
     * Register.
     *
     * @param filter the filter
     */
    void register(PayFilter filter);
}
