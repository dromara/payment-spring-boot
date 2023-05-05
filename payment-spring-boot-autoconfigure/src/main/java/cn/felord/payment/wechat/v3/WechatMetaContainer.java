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
package cn.felord.payment.wechat.v3;


import cn.felord.payment.wechat.WechatTenantService;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 配置容器
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@AllArgsConstructor
public class WechatMetaContainer {
    private final Map<String, WechatMetaBean> wechatMetaBeanMap = new ConcurrentHashMap<>();
    private final Set<String> tenantIds = new ConcurrentSkipListSet<>();
    private final WechatTenantService wechatTenantService;

    /**
     * Add wechat metas.
     *
     * @param wechatMetaBeans the wechat meta beans
     */
    public void addWechatMetas(Collection<WechatMetaBean> wechatMetaBeans) {
        wechatMetaBeans.forEach(this::addMeta);
    }

    private void addMeta(WechatMetaBean wechatMetaBean) {
        String tenantId = wechatMetaBean.getTenantId();
        tenantIds.add(tenantId);
        wechatMetaBeanMap.put(tenantId, wechatMetaBean);
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
        WechatMetaBean wechatMetaBean = this.wechatMetaBeanMap.get(tenantId);
        if (Objects.nonNull(wechatMetaBean)) {
            return wechatMetaBean;
        } else {
            this.addWechatMetas(wechatTenantService.loadTenants());
            return Objects.requireNonNull(this.wechatMetaBeanMap.get(tenantId),
                    "cant obtain the config with tenant: "+tenantId);
        }
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
