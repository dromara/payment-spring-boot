package com.enongm.dianji.payment.wechat.v3.model;


import com.enongm.dianji.payment.wechat.enumeration.CouponBgColor;
import lombok.Data;

/**
 * 优惠券样式
 *
 * @author Dax
 * @since 15:09
 */
@Data
public class PatternInfo {
    /**
     * 背景色
     */
    private CouponBgColor backgroundColor;
    /**
     * 券详情图片
     */
    private String couponImage;
    /**
     * 使用说明
     */
    private String description;
    /**
     * 商户logo
     */
    private String merchantLogo;
    /**
     * 商户名称
     */
    private String merchantName;

}
