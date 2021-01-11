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
 */
package cn.felord.payment.wechat.enumeration;

/**
 * 微信先享卡的守约状态
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
public enum ContractStatus {
    /**
     * 约定进行中，表示用户在约定有效期内，尚未完成所有目标时，守约状态为约定进行中。
     */
    ONGOING,

    /**
     * 约定到期核对中，在约定有效期结束后的一段时间，商户可对卡记录进行校对并做必要调整，守约状态为约定到期核对调整中。
     */
    SETTLING,
    /**
     * 已完成约定，表示用户在约定有效期内，已完成所有目标，守约状态为已完成约定。
     */
    FINISHED,

    /**
     * 未完成约定，表示用户在约定有效期到期后，最终未完成所有约定目标，或用户提前退出约定，守约状态为未完成约定。
     */
    UNFINISHED
}
