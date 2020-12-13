package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 查询代金券详情API参数
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CouponDetailsQueryParams {
    /**
     * 用户在appid下授权得到的openid
     * <p>
     * 参考发券
     */
    private String openId;
    /**
     * 代金券id
     */
    private String couponId;
}
