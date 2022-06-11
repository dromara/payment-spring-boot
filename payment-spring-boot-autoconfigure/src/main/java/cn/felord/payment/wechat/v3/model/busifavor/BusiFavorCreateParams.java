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
package cn.felord.payment.wechat.v3.model.busifavor;

import cn.felord.payment.wechat.enumeration.StockType;
import lombok.Data;

/**
 * 创建商家券请求参数.
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class BusiFavorCreateParams {

    /**
     * 商家券批次名称,[1,21],必填
     */
    private String stockName;
    /**
     * 批次归属商户号,必填
     */
    private String belongMerchant;
    /**
     * 批次备注,[1,20],选填
     */
    private String comment;
    /**
     * 适用商品范围,必填
     * <p>
     * 用来描述批次在哪些商品可用，会显示在微信卡包中。字数上限为15个，一个中文汉字/英文字母/数字均占用一个字数。
     */
    private String goodsName;
    /**
     * 批次类型
     */
    private StockType stockType;
    /**
     * 核销规则
     */
    private CouponUseRule couponUseRule;
    /**
     * 自定义入口
     */
    private CustomEntrance customEntrance;
    /**
     * 商家券code模式枚举
     */
    private CouponCodeMode couponCodeMode;
    /**
     * 样式信息
     */
    private DisplayPatternInfo displayPatternInfo;
    /**
     * 券发放规则
     */
    private StockSendRule stockSendRule;
    /**
     * 商户请求单号
     * <p>
     * 商户创建批次凭据号（格式：商户id+日期+流水号），商户侧需保持唯一性。
     */
    private String outRequestNo;
    /**
     * 事件通知配置
     */
    private BusiFavorNotifyConfig notifyConfig;


    /**
     * 商家券code模式枚举
     *
     * @author felord.cn
     * @since 1.0.4.RELEASE
     */
    public enum CouponCodeMode {

        /**
         * 系统分配券code。（固定22位纯数字）
         *
         * @since 1.0.4.RELEASE
         */
        WECHATPAY_MODE,
        /**
         * 商户发放时接口指定券code。
         *
         * @since 1.0.4.RELEASE
         */
        MERCHANT_API,
        /**
         * 商户上传自定义code，发券时系统随机选取上传的券code。
         *
         * @since 1.0.4.RELEASE
         */
        MERCHANT_UPLOAD
    }
}