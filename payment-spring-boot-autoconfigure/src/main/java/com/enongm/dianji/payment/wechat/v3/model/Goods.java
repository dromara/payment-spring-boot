package com.enongm.dianji.payment.wechat.v3.model;

import lombok.Data;

/**
 * @author Dax
 * @since 17:02
 */
@Data
public class Goods {
    /**
     * 商户侧商品编码
     */
    private String merchantGoodsId;
    /**
     * 微信侧商品编码
     */
    private String wechatpayGoodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品数量
     */
    private int quantity;
    /**
     * 商品单价
     */
    private int unitPrice;
}
