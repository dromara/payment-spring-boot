package cn.felord.payment.wechat.v3.model;

import lombok.Data;

@Data
public class StocksSendParams {
    /**
     * 批次号 必须为代金券（全场券或单品券）批次号，不支持立减与折扣。
     */
    private String stockId;
    /**
     * 用户openid 该openid需要与接口传入中的appid有对应关系。
     */
    private String openid;
    /**
     * 商户单据号
     */
    private String outRequestNo;
    /**
     * 公众账号ID
     */
    private String appid;
    /**
     * 创建批次的商户号
     */
    private String stockCreatorMchid;
    /**
     * 指定面额发券，面额
     */
    private String couponValue;
    /**
     * 指定面额发券，券门槛
     */
    private String couponMinimum;
}
