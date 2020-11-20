package com.enongm.dianji.payment.wechat.v3.model;

import lombok.Data;

/**
 * @author Dax
 * @since 16:22
 */
@Data
public class StocksMchQueryParams {
    private int offset =1;
    private int limit = 10;
    private String stockCreatorMchid;
    private String stockId;

}
