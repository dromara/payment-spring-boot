package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class Payer {
    /**
     * 用户标识
     */
    private String openid;
    /**
     * 用户服务标识
     */
    private String spOpenid;
    /**
     * 用户子标识
     */
    private String subOpenid;
}
