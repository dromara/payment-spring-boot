package cn.felord.payment.wechat.v3.model;

import cn.felord.payment.wechat.enumeration.CouponStatus;
import lombok.Data;

/**
 * @author Dax
 * @since 19:19
 */
@Data
public class UserCouponsQueryParams {
    /**
     * 用户公众号服务号标识
     */
    private String openId;
    /**
     * 公众服务号ID
     */
    private String appId;
    /**
     * 批次号
     */
    private String stockId;
    /**
     * 券状态  null 不生效
     */
    private CouponStatus status;
    /**
     * 创建批次的商户号
     */
    private String creatorMchId;
    /**
     * 批次发放商户号
     */
    private String senderMchId;
    /**
     * 可用商户号
     */
    private String availableMchId;
    /**
     * 分页页码
     */
    private int offset = 0;
    /**
     * 分页大小
     */
    private int limit = 20;
}
