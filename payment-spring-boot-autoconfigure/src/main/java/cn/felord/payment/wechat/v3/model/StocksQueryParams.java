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

import cn.felord.payment.wechat.enumeration.StockStatus;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 查询参数，适用以下接口：
 * <p>
 * 条件查询批次列表API
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class StocksQueryParams {
    /**
     * 必填
     * <p>
     * 条件查询批次列表API 页码从0开始，默认第0页，传递1可能出错。
     */
    private Integer offset = 0;
    /**
     * 必填
     * <p>
     * 条件查询批次列表API 分页大小，最大10。
     */
    private Integer limit = 10;
    /**
     * 选填
     * <p>
     * 起始时间  最终满足格式 {@code yyyy-MM-dd'T'HH:mm:ssXXX}
     */
    private OffsetDateTime createStartTime;
    /**
     * 选填
     * <p>
     * 终止时间  最终满足格式 {@code yyyy-MM-dd'T'HH:mm:ssXXX}
     */
    private OffsetDateTime createEndTime;
    /**
     * 根据API而定
     * <p>
     * 批次状态
     */
    private StockStatus status;
}
