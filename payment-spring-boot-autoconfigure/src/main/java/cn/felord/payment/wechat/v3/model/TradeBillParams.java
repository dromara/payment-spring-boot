/*
 *
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
package cn.felord.payment.wechat.v3.model;

import cn.felord.payment.wechat.enumeration.TarType;
import cn.felord.payment.wechat.enumeration.TradeBillType;
import lombok.Data;

import java.time.LocalDate;

/**
 * 申请交易账单请求参数
 *
 * @author felord.cn
 * @since 1.0.3.RELEASE
 */
@Data
public class TradeBillParams {
    /**
     * 账单日期，必传。
     * <p>
     * 格式YYYY-MM-DD，仅支持三个月内的账单下载申请。
     */
    private LocalDate billDate;
    /**
     * 二级商户号，选填。
     *
     * <ol>
     *     <li>若商户是直连商户：无需填写该字段。</li>
     *     <li>若商户是服务商：
     *     <ul>
     *         <li>不填则默认返回服务商下的交易或退款数据。</li>
     *         <li>如需下载某个子商户下的交易或退款数据，则该字段必填。</li>
     *     </ul>
     *     </li>
     * </ol>
     * <p>
     * 特殊规则：最小字符长度为8
     * <p>
     * 注意：仅适用于电商平台 服务商
     */
    private String subMchid;
    /**
     * 账单类型,不填则默认值为{@link TradeBillType#ALL}
     *
     * @see TradeBillType
     */
    private TradeBillType billType;
    /**
     * 压缩类型，不填默认值为数据流
     *
     * @see TarType
     */
    private TarType tarType;
}
