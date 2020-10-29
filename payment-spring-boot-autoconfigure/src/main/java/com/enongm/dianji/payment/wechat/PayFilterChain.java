package com.enongm.dianji.payment.wechat;


import com.enongm.dianji.payment.wechat.v3.WechatPayRequest;

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
     * @param request the request
     */
    void doChain(WechatPayRequest request);

    /**
     * Register.
     *
     * @param filter the filter
     */
    void register(PayFilter filter);
}
