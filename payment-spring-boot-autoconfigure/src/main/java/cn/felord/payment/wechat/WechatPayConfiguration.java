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


import cn.felord.payment.wechat.v3.SignatureProvider;
import cn.felord.payment.wechat.v3.WechatApiProvider;
import cn.felord.payment.wechat.v3.WechatMetaContainer;
import cn.felord.payment.wechat.v3.WechatPayClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Wechat pay configuration.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Configuration(proxyBeanMethods = false)
public class WechatPayConfiguration {

    /**
     * 微信支付公私钥 以及序列号等元数据.
     *
     * @param wechatTenantService the wechat tenant service
     * @return the wechat cert bean
     */
    @Bean
    @ConditionalOnMissingBean
    WechatMetaContainer wechatMetaContainer(WechatTenantService wechatTenantService) {
        WechatMetaContainer container = new WechatMetaContainer();
              container.addWechatMetas(wechatTenantService.getAllTenants());
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
