package com.enongm.dianji.payment.wechat.enumeration;

/**
 * The enum Stock status.
 *
 * @author Dax
 * @since 15 :17
 */
public enum StockStatus {
    /**
     * Unactivated stock status.
     */
    UNACTIVATED("unactivated", "未激活"),
    /**
     * Audit stock status.
     */
    AUDIT("audit", "审核中"),
    /**
     * Running stock status.
     */
    RUNNING("running", "运行中"),
    /**
     * Stoped stock status.
     */
    STOPED("stoped", "已停止"),
    /**
     * Paused stock status.
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
     */
    public String value() {
        return this.value;
    }

    /**
     * Description string.
     *
     * @return the string
     */
    public String description() {
        return this.description;
    }
}
