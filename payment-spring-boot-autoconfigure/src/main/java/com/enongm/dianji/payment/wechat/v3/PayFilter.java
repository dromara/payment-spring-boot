package com.enongm.dianji.payment.wechat.v3;


/**
 * The interface Pay filter.
 *
 * @author Dax
 * @since 15 :08
 */
public interface PayFilter {

    /**
     * Do filter.
     *  @param requestEntity the request entity
     * @param chain         the chain*/
    void doFilter(WechatRequestEntity<?> requestEntity, PayFilterChain chain);

}
