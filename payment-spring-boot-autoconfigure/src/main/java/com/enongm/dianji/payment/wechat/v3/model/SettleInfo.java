package com.enongm.dianji.payment.wechat.v3.model;

import lombok.Data;

/**
 * @author Dax
 * @since 17:05
 */
@Data
public class SettleInfo {
    /**
     * 是否指定分账
     */
    private boolean profitSharing;
    /**
     * 补差金额
     */
    private int subsidyAmount;
}
