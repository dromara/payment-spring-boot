package com.enongm.dianji.payment.wechat.v3;



import java.util.ArrayList;
import java.util.List;

/**
 * @author Dax
 * @since 16:16
 */
public class DefaultPayFilterChain implements PayFilterChain {
    private int pos = 0;
    private final List<PayFilter> filters = new ArrayList<>();

    @Override
    public void register(PayFilter filter) {
        filters.add(filter);
    }

    @Override
    public void doChain(WechatPayRequest request) {
        int size = filters.size();
        if (pos < size) {
            PayFilter payFilter = filters.get(pos++);
            payFilter.doFilter(request, this);
        }
    }

}
