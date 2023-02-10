/*
 *  Copyright 2019-2022 felord.cn
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
package cn.felord.payment.wechat.v3.model;


import cn.felord.payment.wechat.enumeration.CouponBgColor;
import lombok.Data;

/**
 * 优惠券样式
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
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
