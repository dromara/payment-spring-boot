package cn.felord.payment.wechat.enumeration;

/**
 * 代金券批次状态.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public enum StockStatus {
    /**
     * Unactivated stock status.
     *
     * @since 1.0.0.RELEASE
     */
    UNACTIVATED("unactivated", "未激活"),
    /**
     * Audit stock status.
     *
     * @since 1.0.0.RELEASE
     */
    AUDIT("audit", "审核中"),
    /**
     * Running stock status.
     *
     * @since 1.0.0.RELEASE
     */
    RUNNING("running", "运行中"),
    /**
     * Stoped stock status.
     *
     * @since 1.0.0.RELEASE
     */
    STOPED("stoped", "已停止"),
    /**
     * Paused stock status.
     *
     * @since 1.0.0.RELEASE
     */
    PAUSED("paused", "暂停发放");

    private final String value;
    private final String description;

    StockStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * Value string.
     *
     * @return the string
     * @since 1.0.0.RELEASE
     */
    public String value() {
        return this.value;
    }

    /**
     * Description string.
     *
     * @return the string
     * @since 1.0.0.RELEASE
     */
    public String description() {
        return this.description;
    }
}
