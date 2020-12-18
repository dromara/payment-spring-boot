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
package cn.felord.payment.wechat.v3;


import java.util.*;

/**
 * 配置容器
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class WechatMetaContainer {
    private final Map<String, WechatMetaBean> wechatMetaBeanMap = new HashMap<>();
    private final Set<String> tenantIds = new HashSet<>();


    /**
     * Add wechat meta boolean.
     *
     * @param tenantId       the tenantId
     * @param wechatMetaBean the wechat meta bean
     * @return the boolean
     */
    public WechatMetaBean addWechatMeta(String tenantId, WechatMetaBean wechatMetaBean) {
        tenantIds.add(tenantId);
        return this.wechatMetaBeanMap.put(tenantId, wechatMetaBean);
    }

    /**
     * Remove wechat meta wechat meta bean.
     *
     * @param tenantId the tenantId
     * @return the wechat meta bean
     */
    public WechatMetaBean removeWechatMeta(String tenantId) {
        tenantIds.remove(tenantId);
        return this.wechatMetaBeanMap.remove(tenantId);
    }

    /**
     * Gets wechat meta.
     *
     * @param tenantId the tenantId
     * @return the wechat meta
     */
    public WechatMetaBean getWechatMeta(String tenantId) {
        return Objects.requireNonNull(this.wechatMetaBeanMap.get(tenantId));
    }

    /**
     * Gets properties keys.
     *
     * @return the properties keys
     */
    public Set<String> getTenantIds() {
        return tenantIds;
    }
}
