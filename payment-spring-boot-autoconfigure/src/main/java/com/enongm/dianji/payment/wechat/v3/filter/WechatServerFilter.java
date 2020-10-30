package com.enongm.dianji.payment.wechat.v3.filter;


import com.enongm.dianji.payment.wechat.v3.PayFilter;
import com.enongm.dianji.payment.wechat.v3.PayFilterChain;
import com.enongm.dianji.payment.wechat.enumeration.V3PayType;
import com.enongm.dianji.payment.wechat.enumeration.WeChatServer;
import com.enongm.dianji.payment.wechat.v3.WechatPayRequest;

/**
 * 根据{@link V3PayType} 组装URL
 * 1
 *
 * @author Dax
 * @since 9:43
 */
public class WechatServerFilter implements PayFilter {

    @Override
    public void doFilter(WechatPayRequest request, PayFilterChain chain) {
        request.weChatServer(WeChatServer.CHINA);
        chain.doChain(request);
    }

}
