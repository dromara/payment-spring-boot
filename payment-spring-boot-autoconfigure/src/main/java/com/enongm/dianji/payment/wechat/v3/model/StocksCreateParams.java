package com.enongm.dianji.payment.wechat.v3.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 创建优惠券批次参数.
 *
 * @author Dax
 * @since 14 :19
 */
@Data
public class StocksCreateParams {
    /**
     * 批次名称
     */
    private String stockName;
    /**
     * 仅配置商户可见，用于自定义信息
     */
    private String comment;
    /**
     * 批次归属商户号
     */
    private String belongMerchant;
    /**
     * 批次开始时间
     */
    private LocalDateTime availableBeginTime;
    /**
     * 批次结束时间
     */
    private LocalDateTime availableEndTime;
    /**
     * 是否无资金流
     */
    private Boolean noCash;
    /**
     * 批次类型
     */
    private String stockType = "NORMAL";
    /**
     * 商户单据号
     */
    private String outRequestNo;
    /**
     * 扩展属性
     */
    private String ext_info;
    /**
     * 批次使用规则
     */
    private StockUseRule stockUseRule;
    /**
     * 核销规则
     */
    private CouponUseRule couponUseRule;

}
