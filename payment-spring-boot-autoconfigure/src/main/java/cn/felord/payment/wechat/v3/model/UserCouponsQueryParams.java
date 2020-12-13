package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * The type User coupons query params.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
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
    private Status status;
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
    private Integer offset = 0;
    /**
     * 分页大小
     */
    private Integer limit = 20;


    /**
     * The enum Status.
     *
     * @author felord.cn
     * @since 1.0.0.RELEASE
     */
    public enum Status{
        /**
         * Sended status.
         */
        SENDED,
        /**
         * Used status.
         */
        USED
    }

}
