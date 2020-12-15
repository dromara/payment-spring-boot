package cn.felord.payment.wechat.enumeration;

/**
 * 代金券状态.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public enum CouponStatus {
    /**
     * 可用.
     *
     * @since 1.0.0.RELEASE
     */
    SENDED,
    /**
     * 已实扣.
     *
     * @since 1.0.0.RELEASE
     */
    USED,
    /**
     * 已过期.
     *
     * @since 1.0.0.RELEASE
     */
    EXPIRED
}
