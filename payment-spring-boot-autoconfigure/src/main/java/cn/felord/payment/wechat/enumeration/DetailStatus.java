/*
 *  Copyright 2019-2021 felord.cn
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
 * 转账明细.
 *
 * @author xfa00
 * @since 1.0.6.RELEASE
 */
public enum DetailStatus {
    /**
     * 全部。需要同时查询转账成功和转账失败的明细单
     */
    ALL,
    /**
     * 转账成功。只查询转账成功的明细单
     */
    SUCCESS,
    /**
     * 转账失败。只查询转账失败的明细单
     */
    FAIL
}
