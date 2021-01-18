/*
 *  Copyright 2019-2021 felord.cn
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

import cn.felord.payment.wechat.enumeration.BusiFavorCodeDisplayMode;
import cn.felord.payment.wechat.enumeration.BusiFavorUseMethod;
import lombok.Data;

/**
 * 修改商家券基本信息API请求参数.
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class BusiFavorUpdateParams {

    /**
     * 商家券批次号，必传
     */
    private String stockId;
    /**
     * 自定义入口
     */
    private UpdateCustomEntrance customEntrance;
    /**
     * 商家券批次名称,[1,21]
     */
    private String stockName;
    /**
     * 批次备注,[1,20]
     */
    private String comment;
    /**
     * 适用商品范围
     * <p>
     * 用来描述批次在哪些商品可用，会显示在微信卡包中。字数上限为15个，一个中文汉字/英文字母/数字均占用一个字数。
     */
    private String goodsName;
    /**
     * 商户请求单号
     * <p>
     * 商户创建批次凭据号（格式：商户id+日期+流水号），商户侧需保持唯一性。
     */
    private String outRequestNo;
    /**
     * 样式信息
     */
    private DisplayPatternInfo displayPatternInfo;
    /**
     * 核销规则
     */
    private UpdateCouponUseRule couponUseRule;
    /**
     * 券发放规则
     */
    private UpdateStockSendRule stockSendRule;
    /**
     * 事件通知配置
     */
    private BusiFavorNotifyConfig notifyConfig;


    /**
     * 商家券核销规则-自定义入口修改参数
     * <p>
     * 卡详情页面，可选择多种入口引导用户。
     *
     * @author felord.cn
     * @see CustomEntrance
     * @since 1.0.4.RELEASE
     */
    @Data
    public static class UpdateCustomEntrance {

        /**
         * 营销馆id
         */
        private String hallId;
        /**
         * 小程序入口
         */
        private MiniProgramsInfo miniProgramsInfo;
        /**
         * 商户公众号appid
         * <p>
         * 从券详情可跳转至公众号
         */
        private String appid;
        /**
         * code展示模式
         */
        private BusiFavorCodeDisplayMode codeDisplayMode;

    }

    /**
     * 商家券核销规则修改参数
     *
     * @author felord.cn
     * @see CouponUseRule
     * @since 1.0.4.RELEASE
     */
    @Data
    public static class UpdateCouponUseRule {

        /**
         * 核销方式
         */
        private BusiFavorUseMethod useMethod;
        /**
         * 核销小程序appid
         *
         * @see BusiFavorUseMethod#MINI_PROGRAMS
         */
        private String miniProgramsAppid;
        /**
         * 核销小程序path
         *
         * @see #miniProgramsAppid
         */
        private String miniProgramsPath;
    }


    /**
     * 商家券发放规则修改参数
     *
     * @author felord.cn
     * @see StockSendRule
     * @since 1.0.4.RELEASE
     */
    @Data
    public static class UpdateStockSendRule {

        /**
         * 是否开启自然人限制
         */
        private Boolean naturalPersonLimit;
        /**
         * 可疑账号拦截
         */
        private Boolean preventApiAbuse;
    }
}