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

package cn.felord.payment.wechat.v3.model.ecommerce;

import cn.felord.payment.wechat.enumeration.ReceiverType;
import lombok.Data;

/**
 * The type Ecommerce receiver.
 *
 * @author felord.cn
 * @since 1.0.15.RELEASE
 */
@Data
public class BrandReceiver {
    private String brandMchid;
    private String appid;
    private String subAppid;
    private ReceiverType type;
    private String account;
    private String name;
    private RelationType relationType;

    /**
     * The enum Relation type.
     */
    public enum RelationType {
        /**
         * 供应商
         */
        SUPPLIER,
        /**
         * 分销商
         */
        DISTRIBUTOR,
        /**
         * 服务商
         */
        SERVICE_PROVIDER,
        /**
         * 平台
         */
        PLATFORM,
        /**
         * 员工
         */
        STAFF,
        /**
         * 其它
         */
        OTHERS
    }
}
