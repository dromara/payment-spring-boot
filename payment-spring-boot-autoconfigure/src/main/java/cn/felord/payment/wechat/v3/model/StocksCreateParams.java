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
package cn.felord.payment.wechat.v3.model;

import cn.felord.payment.wechat.enumeration.StockType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 创建优惠券批次参数.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
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
     * 批次开始时间 rfc 3339   yyyy-MM-ddTHH:mm:ss+TIMEZONE
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "GMT+8")
    private OffsetDateTime availableBeginTime;
    /**
     * 批次结束时间 rfc 3339   yyyy-MM-ddTHH:mm:ss+TIMEZONE
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "GMT+8")
    private OffsetDateTime availableEndTime;
    /**
     * 可创建代金券的类型包含预充值和免充值两种类型。此字段用来标识制券 <strong>是否无资金流</strong>
     * <p>
     * ● 预充值代金券适用于第三方出资策划的活动，例如：满100减10. 指订单金额100元，用户实付90元，商户实收100元。设置为{@link Boolean#FALSE}
     * <p>
     * ● 免充值适用于商户策划的活动，例如：满100减10。 指订单金额100元，用户实付90元（用户领券后，在支付中直接核销10元），商户实收90元。设置为{@link Boolean#TRUE}
     */
    private Boolean noCash;
    /**
     * 批次类型
     *
     * @since 1.0.4.RELEASE
     */
    private StockType stockType = StockType.NORMAL;
    /**
     * 商户单据号
     */
    private String outRequestNo;
    /**
     * 扩展属性
     */
    private String extInfo;
    /**
     * 批次使用规则
     */
    private StockUseRule stockUseRule;
    /**
     * 核销规则
     */
    private CouponUseRule couponUseRule;
    /**
     * 代金券样式
     */
    private PatternInfo patternInfo;

}
