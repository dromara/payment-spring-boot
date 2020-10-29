package com.enongm.dianji.payment.wechat;


import com.enongm.dianji.payment.wechat.v3.WechatPayRequest;

/**
 * The interface Pay filter.
 *
 * @author Dax
 * @since 15 :08
 */

public interface PayFilter {

    /**
     * Do filter.
     *
     * @param request the request
     * @param chain   the chain
     */
    void doFilter(WechatPayRequest request, PayFilterChain chain);

}
