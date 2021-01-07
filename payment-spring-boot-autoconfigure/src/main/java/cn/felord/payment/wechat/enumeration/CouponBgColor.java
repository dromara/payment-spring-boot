/*
 *
 *  Copyright 2019-2020 felord.cn
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *  Website:
 *       https://felord.cn
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.felord.payment.wechat.enumeration;

/**
 * 优惠券背景色
 * <p>
 * 详见<a target= "_blank" href= "https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/marketing/convention/chapter3_1.shtml#menu1">优惠券背景色参考</a>
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public enum CouponBgColor {
    /**
     * Color 010 coupon bg color.
     */
    COLOR010("#63B359"),
    /**
     * Color 020 coupon bg color.
     */
    COLOR020("#2C9F67"),
    /**
     * Color 030 coupon bg color.
     */
    COLOR030("#509FC9"),
    /**
     * Color 040 coupon bg color.
     */
    COLOR040("#5885CF"),
    /**
     * Color 050 coupon bg color.
     */
    COLOR050("#9062C0"),
    /**
     * Color 060 coupon bg color.
     */
    COLOR060("#D09A45"),
    /**
     * Color 070 coupon bg color.
     */
    COLOR070("#E4B138"),
    /**
     * Color 080 coupon bg color.
     */
    COLOR080("#EE903C"),
    /**
     * Color 090 coupon bg color.
     */
    COLOR090("#DD6549"),
    /**
     * Color 100 coupon bg color.
     */
    COLOR100("#CC463D");

    /**
     * The Color.
     */
    private final String color;

    /**
     * Instantiates a new Coupon bg color.
     *
     * @param color the color
     */
    CouponBgColor(String color) {
        this.color = color;
    }

    /**
     * Color string.
     *
     * @return the string
     */
    public String color() {
        return this.color;
    }
}
