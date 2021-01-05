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

import cn.felord.payment.wechat.enumeration.FundFlowAccountType;
import cn.felord.payment.wechat.enumeration.TarType;
import lombok.Data;

import java.time.LocalDate;

/**
 * 申请资金账单请求参数
 *
 * @author felord.cn
 * @since 1.0.3.RELEASE
 */
@Data
public class FundFlowBillParams {
    /**
     * 账单日期，必传。
     * <p>
     * 格式YYYY-MM-DD，仅支持三个月内的账单下载申请。
     */
    private LocalDate billDate;
    /**
     * 资金账户类型,不填则默认值为{@link FundFlowAccountType#BASIC}
     *
     * @see FundFlowAccountType
     */
    private FundFlowAccountType accountType;
    /**
     * 压缩类型，不填默认值为数据流
     *
     * @see TarType
     */
    private TarType tarType;
}
