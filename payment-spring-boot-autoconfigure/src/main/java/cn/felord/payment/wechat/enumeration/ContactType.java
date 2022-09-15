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
 * 超级管理员类型
 *
 * @since
 */
public enum ContactType {
    /**
     * 经营者或者法人
     * <p>
     * 主体为个体工商户/企业/政府机关/事业单位/社会组织
     */
    LEGAL,
    /**
     * 经办人
     *
     * 经商户授权办理微信支付业务的人员
     */
    SUPER
}
