package cn.felord.payment.wechat.oauth2;

import lombok.Data;

/**
 * OAuth2 Exchange
 * @author Dax
 * @since 13:14
 */
@Data
public class OAuth2Exchange {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String openid;
    private String scope;
}
