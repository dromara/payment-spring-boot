package com.enongm.dianji.payment.wechat.oauth2;


import com.enongm.dianji.payment.PayException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

/**
 * OAuth2 获取用户的公众号授权openid.
 *
 * @author Dax
 * @since 11 :39
 */
public class OAuth2AuthorizationRequestRedirectProvider {
    private static final String AUTHORIZATION_URI = "https://open.weixin.qq.com/connect/oauth2/authorize";
    private static final String TOKEN_URI = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private final RestOperations restOperations = new RestTemplate();
    private final String appId;
    private final String secret;

    /**
     * Instantiates a new O auth 2 authorization request redirect provider.
     *
     * @param appId  the app id
     * @param secret the secret
     */
    public OAuth2AuthorizationRequestRedirectProvider(String appId, String secret) {
        this.appId = appId;
        this.secret = secret;
    }

    /**
     * 拼接微信公众号授权的url,用以触发用户点击跳转微信授权.
     *
     * @param phoneNumber the phone number
     * @param redirectUri the redirect uri
     * @return uri components
     */
    public UriComponents redirect(String phoneNumber, String redirectUri) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("appid", appId);
        queryParams.add("redirect_uri", redirectUri);
        queryParams.add("response_type", "code");
        queryParams.add("scope", "snsapi_base");
        queryParams.add("state", phoneNumber);
        return UriComponentsBuilder.fromHttpUrl(AUTHORIZATION_URI).queryParams(queryParams).build();
    }


    /**
     * 微信服务器授权成功后调用redirectUri的处理逻辑.
     *
     * @param code the code
     * @return the string
     */
    public String openId(String code) {
        Assert.hasText(code, "wechat pay oauth2 code is required");
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("appid", appId);
        queryParams.add("secret", secret);
        queryParams.add("code", code);
        queryParams.add("grant_type", "authorization_code");
        URI uri = UriComponentsBuilder.fromHttpUrl(TOKEN_URI)
                .queryParams(queryParams)
                .build()
                .toUri();

        RequestEntity<Void> requestEntity = RequestEntity.get(uri).build();
        ResponseEntity<ObjectNode> responseEntity = restOperations.exchange(requestEntity, ObjectNode.class);
        ObjectNode body = responseEntity.getBody();
        if (Objects.nonNull(body)) {
            JsonNode openid = body.get("openid");
            return openid.asText();
        }
        throw new PayException("OpenId Not Available");
    }


}
