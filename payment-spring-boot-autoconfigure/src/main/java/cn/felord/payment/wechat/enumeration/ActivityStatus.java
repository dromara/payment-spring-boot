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

package cn.felord.payment.wechat.enumeration;

/**
 * The enum Activity status.
 *
 * @author dax
 * @since 1.0.19.RELEASE
 */
public enum ActivityStatus {
    /**
     * 状态未知
     */
    ACT_STATUS_UNKNOWN,
    /**
     * 已创建
     */
    CREATE_ACT_STATUS,
    /**
     * 运行中
     */
    ONGOING_ACT_STATUS,
    /**
     * 已终止
     */
    TERMINATE_ACT_STATUS,

    /**
     * 已暂停
     */
    STOP_ACT_STATUS,

    /**
     * 已过期
     */
    OVER_TIME_ACT_STATUS,

    /**
     * 创建活动失败
     */
    CREATE_ACT_FAILED
}
