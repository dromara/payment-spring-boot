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
package cn.felord.payment.wechat.enumeration;

/**
 * 目标完成类型、优惠使用类型
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
public enum StrategyType {
    /**
     * 增加数量，表示用户发生了履约行为
     */
    INCREASE,
    /**
     * 减少数量，表示取消用户的履约行为（例如用户取消购买、退货退款等）
     */
    DECREASE
}
