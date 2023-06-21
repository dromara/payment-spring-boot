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

import lombok.Getter;

/**
 * @author dax
 * @since 2023/2/1 8:59
 */
@Getter
public enum WechatPayAlgorithms {
    RSA("WECHATPAY2-SHA256-RSA2048"),
    SM3("WECHATPAY2-SM2-WITH-SM3");

    private final String algorithm;

    WechatPayAlgorithms(String algorithm) {
        this.algorithm = algorithm;
    }

}
