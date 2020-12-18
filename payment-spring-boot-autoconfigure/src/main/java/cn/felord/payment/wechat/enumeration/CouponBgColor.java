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
 *
 */
package cn.felord.payment.wechat.enumeration;

/**
 * 优惠券背景色
 * <p>
 * https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/marketing/convention/chapter3_1.shtml#menu1
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
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
