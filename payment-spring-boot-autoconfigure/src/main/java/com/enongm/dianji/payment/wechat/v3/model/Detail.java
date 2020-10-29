package com.enongm.dianji.payment.wechat.v3.model;

import lombok.Data;

import java.util.List;

/**
 * @author Dax
 * @since 17:01
 */
@Data
public class Detail {
    /**
     * 订单原价
     */
    private int costPrice;
    /**
     * 商品小票ID
     */
    private String invoiceId;
    /**
     * 单品列表
     */
    private List<Goods> goodsDetail;
}
