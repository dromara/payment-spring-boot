package com.enongm.dianji.payment.wechat.v3.filter;


import com.enongm.dianji.payment.wechat.v3.PayFilter;
import com.enongm.dianji.payment.wechat.v3.PayFilterChain;
import com.enongm.dianji.payment.wechat.v3.SignatureProvider;
import com.enongm.dianji.payment.wechat.v3.WechatRequestEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Objects;

/**
 * 微信支付 给请求添加必要的请求头.
 * 3
 *
 * @author Dax
 * @since 15 :12
 */
public class HeaderFilter implements PayFilter {
    private final SignatureProvider signatureProvider;

    public HeaderFilter(SignatureProvider signatureProvider) {
        this.signatureProvider = signatureProvider;
    }

    @Override
    public <T> void doFilter(WechatRequestEntity<T> requestEntity, PayFilterChain chain) {

        UriComponents uri = UriComponentsBuilder.fromUri(requestEntity.getUrl()).build();
        String canonicalUrl = uri.getPath();
        String encodedQuery = uri.getQuery();

        if (encodedQuery != null) {
            canonicalUrl += "?" + encodedQuery;
        }
        // 签名
        HttpMethod httpMethod = requestEntity.getMethod();
        Assert.notNull(httpMethod, "httpMethod is required");

        String body = requestEntity.hasBody() ? Objects.requireNonNull(requestEntity.getBody()).toString() : "";
        String authorization = signatureProvider.requestSign(httpMethod.name(), canonicalUrl, body);

        HttpHeaders headers = requestEntity.getHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // 兼容图片上传，自定义优先级最高
        if (Objects.isNull(headers.getContentType())) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        headers.add("Authorization", authorization);
        headers.add("User-Agent", "X-Pay-Service");

        chain.doChain(requestEntity.headers(headers));
    }


}
