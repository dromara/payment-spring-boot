/*
 *
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
 *
 */
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 查询代金券可用商户.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class MchQueryParams {
    /**
     * 必填
     * <p>
     * 查询代金券可用商户API 分页页码，最大1000。
     */
    private Integer offset = 0;
    /**
     * 必填
     * <p>
     * 查询代金券可用商户API 最大50。
     */
    private Integer limit = 10;
    /**
     * 批次ID
     */
    private String stockId;
}
