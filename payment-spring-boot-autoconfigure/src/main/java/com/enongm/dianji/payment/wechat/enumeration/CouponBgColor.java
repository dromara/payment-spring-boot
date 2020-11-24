package com.enongm.dianji.payment.wechat.enumeration;

/**
 * 优惠券背景色
 * <p>
 * https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/marketing/convention/chapter3_1.shtml#menu1
 *
 * @author Dax
 * @since 14:50
 */
public enum CouponBgColor {
    COLOR010("#63B359"),
    COLOR020("#2C9F67"),
    COLOR030("#509FC9"),
    COLOR040("#5885CF"),
    COLOR050("#9062C0"),
    COLOR060("#D09A45"),
    COLOR070("#E4B138"),
    COLOR080("#EE903C"),
    COLOR090("#DD6549"),
    COLOR100("#CC463D");

    private final String color;

    CouponBgColor(String color) {
        this.color = color;
    }

    public String color() {
        return this.color;
    }
}
