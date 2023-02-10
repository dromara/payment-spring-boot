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
package cn.felord.payment.wechat.v3.model.batchtransfer;

import cn.felord.payment.wechat.enumeration.FundFlowAccountType;
import lombok.Data;

import java.time.LocalDate;

/**
 * 查询账户日终余额API 请求参数
 *
 * @author felord.cn
 * @since 1.0.11.RELEASE
 */
@Data
public class QueryDayBalanceParams {
    /**
     * 账户类型，必填
     * @see FundFlowAccountType
     */
    private FundFlowAccountType accountType;
    /**
     * 日期，必填
     */
    private LocalDate date;
}
