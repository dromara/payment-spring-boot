package com.enongm.dianji.payment.wechat.v3.filter;


import com.enongm.dianji.payment.wechat.v3.PayFilter;
import com.enongm.dianji.payment.wechat.v3.PayFilterChain;
import com.enongm.dianji.payment.wechat.v3.SignatureProvider;
import com.enongm.dianji.payment.wechat.v3.WechatPayRequest;
import okhttp3.HttpUrl;

/**
 * 微信支付 给请求添加必要的请求头.
 * 3
 *
 * @author Dax
 * @since 15 :12
 */
public class HeaderFilter implements PayFilter {
    private static final String APPLICATION_JSON = "application/json";
    private final SignatureProvider signatureProvider;

    public HeaderFilter(SignatureProvider signatureProvider) {
        this.signatureProvider = signatureProvider;
    }

    @Override
    public void doFilter(WechatPayRequest request, PayFilterChain chain) {

        // 签名
        HttpUrl url = HttpUrl.get(request.url());

        String canonicalUrl = url.encodedPath();
        String encodedQuery = url.encodedQuery();
        if (encodedQuery != null) {
            canonicalUrl += "?" + encodedQuery;
        }
        String method = request.getV3PayType().method();
        String body = "GET".equals(method) ? "" : request.getBody();

        String authorization = signatureProvider.requestSign(method, canonicalUrl, body);
        request.addHeader("Accept", APPLICATION_JSON)
                .addHeader("Authorization", authorization)
                .addHeader("Content-Type", APPLICATION_JSON)
                .addHeader("User-Agent", "pay-service");
        chain.doChain(request);
    }


}
