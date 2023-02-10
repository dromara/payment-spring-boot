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
package cn.felord.payment.wechat.v3.model.discountcard;

import cn.felord.payment.wechat.enumeration.StrategyType;
import lombok.Data;

/**
 * 优惠使用纪录列表对象
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class RewardUsageRecord {

    /**
     * 优惠金额
     *
     * <ol>
     *  <li>优惠金额，用户此项本次享受的优惠对应的优惠总金额，单位：分，必须大于0。</li>
     *  <li>子优惠已享金额累计≤创建模板时配置的此子优惠的价值金额 例如：优惠为【满10元减3元优惠券4张】时，用户一次消费使用了2张优惠券，优惠金额为本次优惠总金额6元，优惠数量为本次使用优惠的优惠券数量2张</li>
     * </ol>
     */
    private Long amount;
    /**
     * 优惠使用描述
     */
    private String description;
    /**
     * 备注说明
     */
    private String remark;
    /**
     * 优惠Id
     */
    private String rewardId;
    /**
     * 优惠使用纪录流水号
     */
    private String rewardUsageSerialNo;
    /**
     * 优惠使用数量
     */
    private Long usageCount;
    /**
     * 优惠使用时间
     */
    private String usageTime;
    /**
     * 优惠使用类型
     */
    private StrategyType usageType;

}
