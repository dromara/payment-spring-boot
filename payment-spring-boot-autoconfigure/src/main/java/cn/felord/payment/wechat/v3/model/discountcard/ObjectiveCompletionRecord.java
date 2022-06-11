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
 */
package cn.felord.payment.wechat.v3.model.discountcard;

import cn.felord.payment.wechat.enumeration.StrategyType;
import lombok.Data;

/**
 * 微信先享卡目标完成纪录
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class ObjectiveCompletionRecord {

    /**
     * 目标完成数量
     */
    private Long completionCount;
    /**
     * 目标完成时间
     */
    private String completionTime;
    /**
     * 目标完成类型
     */
    private StrategyType completionType;
    /**
     * 目标完成描述
     */
    private String description;
    /**
     * 目标完成流水号
     */
    private String objectiveCompletionSerialNo;
    /**
     * 目标id
     */
    private String objectiveId;
    /**
     * 备注说明
     */
    private String remark;

}
