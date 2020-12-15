package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 查询用户授权状态参数.
 * <p>
 * {@code appid} 从对应租户的配置中自动注入。
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class UserServiceStateParams {
    /**
     * 微信支付分 服务ID , 需要微信侧运营操作绑定到商户。
     */
    private String serviceId;
    /**
     * 微信用户在商户对应appid下的唯一标识。
     */
    private String openId;

}
