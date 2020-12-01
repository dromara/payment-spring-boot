package com.enongm.dianji.payment.wechat.oauth2;


import com.enongm.dianji.payment.PayException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.SneakyThrows;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * OAuth2 获取用户的公众号授权openid.
 * 1.需要微信公众号服务号
 * 2.需要微信公众号服务号绑定微信开放平台
 * 3.需要正确设置授权回调
 *
 * @author Dax
 * @since 11 :39
 */
public class OAuth2AuthorizationRequestRedirectProvider {
    private static final String AUTHORIZATION_URI = "https://open.weixin.qq.com/connect/oauth2/authorize";
    private static final String TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private final RestOperations restOperations = new RestTemplate();
    private final ObjectMapper objectMapper;
    private final String appId;
    private final String secret;

    /**
     * Instantiates a new O auth 2 authorization request redirect provider.
     *
     * @param appId  the app id
     * @param secret the secret
     */
    public OAuth2AuthorizationRequestRedirectProvider(String appId, String secret) {
        this.objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.appId = appId;
        this.secret = secret;
    }

    /**
     * 拼接微信公众号授权的url,用以触发用户点击跳转微信授权.
     *
     * @param state       the state
     * @param redirectUri the redirect uri
     * @return uri components
     */
    @SneakyThrows
    public String redirect(String state, String redirectUri) {
        Assert.hasText(redirectUri, "redirectUri is required");
        String encode = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.name());
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("appid", appId);
        queryParams.add("redirect_uri", encode);
        queryParams.add("response_type", "code");
        queryParams.add("scope", "snsapi_base");
        queryParams.add("state", state);
        return UriComponentsBuilder.fromHttpUrl(AUTHORIZATION_URI).queryParams(queryParams).build().toUriString() + "#wechat_redirect";
    }


    /**
     * 微信服务器授权成功后调用redirectUri的处理逻辑.
     *
     * @param code the code
     * @return the string
     */
    @SneakyThrows
    public OAuth2Exchange exchange(String code, String state) {
        Assert.hasText(code, "wechat pay oauth2 code is required");
        Assert.hasText(state, "wechat pay oauth2 state is required");
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("appid", appId);
        queryParams.add("secret", secret);
        queryParams.add("code", code);
        queryParams.add("state", state);
        queryParams.add("grant_type", "authorization_code");
        URI uri = UriComponentsBuilder.fromHttpUrl(TOKEN_URI)
                .queryParams(queryParams)
                .build()
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();
        ResponseEntity<String> responseEntity = restOperations.exchange(requestEntity, String.class);

        String body = responseEntity.getBody();
        if (Objects.nonNull(body)) {
            return objectMapper.readValue(body, OAuth2Exchange.class);
        }
        throw new PayException("Wechat OAuth2 Authorization failed");
    }


}
