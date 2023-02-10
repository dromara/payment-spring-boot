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
package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 查询用户授权状态参数.
 * <p>
 * {@code appid} 从对应租户的配置中自动注入。
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class UserServiceStateParams {
    /**
     * 微信支付分 服务ID , 需要微信侧运营操作绑定到商户。
     */
    private String serviceId;
    /**
     * 微信用户在商户对应appid下的唯一标识。
     */
    private String openId;

}
