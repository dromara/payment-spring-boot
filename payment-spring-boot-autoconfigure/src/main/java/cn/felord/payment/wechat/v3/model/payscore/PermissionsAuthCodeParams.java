package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 适用于以下API:
 * <p>
 * 查询与用户授权记录（授权协议号）API
 * <p>
 * 解除用户授权关系（授权协议号）API
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class PermissionsAuthCodeParams {
    /**
     * 服务ID，必填
     */
    private String serviceId;
    /**
     * 授权协议号，必填
     */
    private String authorizationCode;
    /**
     * 仅仅适用于解除用户授权关系（授权协议号）API
     */
    private String reason;
}
