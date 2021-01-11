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
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 场景信息
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class SceneInfo {
    /**
     * 用户终端IP
     */
    private String payerClientIp;
    /**
     * 商户端设备号
     */
    private String deviceId;
    /**
     * 商户门店信息
     */
    private StoreInfo storeInfo;
    /**
     * H5 场景信息
     */
    private H5Info h5Info;
}
