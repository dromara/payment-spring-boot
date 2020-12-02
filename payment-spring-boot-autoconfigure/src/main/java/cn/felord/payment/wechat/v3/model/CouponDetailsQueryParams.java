package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 查询代金券详情API参数
 *
 * @author Dax
 * @since 18 :51
 */
@Data
public class CouponDetailsQueryParams {
    /**
     * 用户在公众号服务号配置{@link cn.felord.payment.wechat.WechatPayProperties.Mp}下授权得到的openid
     * 参考发券
     */
    private String openId;
    /**
     * 代金券id
     */
    private String couponId;
}
