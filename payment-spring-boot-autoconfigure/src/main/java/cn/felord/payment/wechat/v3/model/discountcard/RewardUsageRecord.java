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
 *
 */
package cn.felord.payment.wechat.v3.model.discountcard;

import cn.felord.payment.wechat.enumeration.StrategyType;
import lombok.Data;

/**
 * The type Reward usage record.
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class RewardUsageRecord {

    /**
     * The Amount.
     */
    private Long amount;
    /**
     * The Description.
     */
    private String description;
    /**
     * The Remark.
     */
    private String remark;
    /**
     * The Reward id.
     */
    private String rewardId;
    /**
     * The Reward usage serial no.
     */
    private String rewardUsageSerialNo;
    /**
     * The Usage count.
     */
    private Long usageCount;
    /**
     * The Usage time.
     */
    private String usageTime;
    /**
     * The Usage type.
     */
    private StrategyType usageType;

}
