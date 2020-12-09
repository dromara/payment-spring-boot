
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

import java.util.List;

public class PromotionDetail {

    private Long amount;
    private String couponId;
    private String currency;
    private List<GoodsDetail> goodsDetail;
    private Long merchantContribute;
    private String name;
    private Long otherContribute;
    private String scope;
    private String stockId;
    private String type;
    private Long wechatpayContribute;

    @Data
    public static class GoodsDetail {

        private String goodsId;
        private Long quantity;
        private Long unitPrice;
        private Long discountAmount;
        private String goodsRemark;

    }
}
