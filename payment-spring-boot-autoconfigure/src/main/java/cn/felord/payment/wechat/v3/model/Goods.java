package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
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
