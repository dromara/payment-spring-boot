
package com.enongm.dianji.payment.wechat.v3.model;

import lombok.Data;

/**
 * 商户下单接口传的单品信息
 */
@Data
public class GoodsDetail {

    private Long discountAmount;
    private String goodsId;
    private Long price;
    private Long quantity;

}
