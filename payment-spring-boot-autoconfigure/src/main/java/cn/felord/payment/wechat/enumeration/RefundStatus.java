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
 *  退款状态.
 *
 * @author felord.cn
 * @since 1.0.7.RELEASE
 */
public enum RefundStatus {
    /**
     * 退款异常.
     *
     * @since 1.0.7.RELEASE
     */
    ABNORMAL,

    /**
     * 退款关闭.
     *
     * @since 1.0.7.RELEASE
     */
    CLOSED,
    /**
     * 退款成功.
     *
     * @since 1.0.7.RELEASE
     */
    SUCCESS

}
