
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
package cn.felord.payment.wechat.v3.model.combine;


import lombok.Data;

/**
 * 合单支付，支付者信息.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CombinePayerInfo {
    /**
     * 使用合单appid获取的对应用户openid。是用户在商户appid下的唯一标识。
     */
    private String openid;
}
