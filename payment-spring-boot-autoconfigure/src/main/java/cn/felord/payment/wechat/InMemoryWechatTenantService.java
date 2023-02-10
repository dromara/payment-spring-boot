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

package cn.felord.payment.wechat;

import cn.felord.payment.wechat.v3.KeyPairFactory;
import cn.felord.payment.wechat.v3.WechatMetaBean;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 从配置文件中加载租户信息,默认实现,可被覆盖
 *
 * @author xiafang
 * @since 2023/2/3 11:40
 */
@AllArgsConstructor
public class InMemoryWechatTenantService implements WechatTenantService {
    private final WechatPayProperties wechatPayProperties;

    @Override
    public Set<WechatMetaBean> loadTenants() {
        Map<String, WechatPayProperties.V3> v3Map = wechatPayProperties.getV3();
        KeyPairFactory keyPairFactory = new KeyPairFactory();
        return v3Map.entrySet()
                .stream()
                .map(entry -> {
                    WechatPayProperties.V3 v3 = entry.getValue();
                    String tenantId = entry.getKey();
                    String certPath = v3.getCertPath();
                    String certAbsolutePath = v3.getCertAbsolutePath();
                    String mchId = v3.getMchId();
                    Resource resource = certAbsolutePath != null ? new FileSystemResource(certAbsolutePath) :
                            new ClassPathResource(certPath == null ? "wechat/apiclient_cert.p12" : certPath);
                    WechatMetaBean wechatMetaBean = keyPairFactory.initWechatMetaBean(resource, mchId);
                    wechatMetaBean.setV3(v3);
                    wechatMetaBean.setTenantId(tenantId);
                    return wechatMetaBean;
                })
                .collect(Collectors.toSet());
    }
}
