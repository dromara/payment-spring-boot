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


import cn.felord.payment.wechat.v3.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.Map;

/**
 * The type Wechat pay configuration.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Configuration(proxyBeanMethods = false)
@Conditional(WechatPayConfiguredCondition.class)
@EnableConfigurationProperties(WechatPayProperties.class)
public class WechatPayConfiguration {
    /**
     * The constant CERT_ALIAS.
     */
    private static final String CERT_ALIAS = "Tenpay Certificate";

    /**
     * 微信支付公私钥 以及序列号等元数据.
     *
     * @param wechatPayProperties the wechat pay properties
     * @return the wechat cert bean
     */
    @Bean
    @ConditionalOnMissingBean
    WechatMetaContainer wechatMetaContainer(WechatPayProperties wechatPayProperties) {

        Map<String, WechatPayProperties.V3> v3Map = wechatPayProperties.getV3();
        WechatMetaContainer container = new WechatMetaContainer();
        KeyPairFactory keyPairFactory = new KeyPairFactory();
        v3Map.keySet().forEach(tenantId -> {
            WechatPayProperties.V3 v3 = v3Map.get(tenantId);
            String certPath = v3.getCertPath();
            String certAbsolutePath = v3.getCertAbsolutePath();
            String mchId = v3.getMchId();
            Resource resource = certAbsolutePath != null ? new FileSystemResource(certAbsolutePath) :
                    new ClassPathResource(certPath == null ? "wechat/apiclient_cert.p12" : certPath);
            WechatMetaBean wechatMetaBean = keyPairFactory.initWechatMetaBean(resource, CERT_ALIAS, mchId);
            wechatMetaBean.setV3(v3);
            wechatMetaBean.setTenantId(tenantId);
            container.addWechatMeta(tenantId, wechatMetaBean);
        });
        return container;
    }

    /**
     * 微信支付V3签名工具.
     *
     * @param wechatMetaContainer the wechat meta container
     * @return the signature provider
     */
    @Bean
    SignatureProvider signatureProvider(WechatMetaContainer wechatMetaContainer) {
        return new SignatureProvider(wechatMetaContainer);
    }


    /**
     * 微信支付V3 客户端.
     *
     * @param signatureProvider the signature provider
     * @return the wechat pay service
     */
    @Bean
    public WechatPayClient wechatPayClient(SignatureProvider signatureProvider) {
        return new WechatPayClient(signatureProvider);
    }

    /**
     * 多租户接口Provider.
     *
     * @param wechatPayClient the wechat pay client
     * @return the wechat api provider
     */
    @Bean
    public WechatApiProvider wechatApiProvider(WechatPayClient wechatPayClient) {
        return new WechatApiProvider(wechatPayClient);
    }
}
