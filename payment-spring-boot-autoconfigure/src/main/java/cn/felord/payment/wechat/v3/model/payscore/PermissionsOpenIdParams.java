package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 适用于以下API:
 * <p>
 * 查询与用户授权记录（openid）API
 * <p>
 * 解除用户授权关系（openid）API
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class PermissionsOpenIdParams {
    /**
     * openid，必填
     */
    private String openid;
    /**
     * 仅仅适用于解除用户授权关系（openid）API
     */
    private String appid;
    /**
     * 服务ID，必填
     * <p>
     * 该服务ID有本接口对应产品的权限。
     */
    private String serviceId;
    /**
     * 仅仅适用于解除用户授权关系（openid）API
     */
    private String reason;
}
