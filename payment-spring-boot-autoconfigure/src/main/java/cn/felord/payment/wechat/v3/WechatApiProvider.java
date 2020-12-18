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

/**
 * 微信支付工具.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class WechatApiProvider {
    /**
     * The Wechat pay client.
     */
    private final WechatPayClient wechatPayClient;

    /**
     * Instantiates a new Wechat api provider.
     *
     * @param wechatPayClient the wechat pay client
     */
    public WechatApiProvider(WechatPayClient wechatPayClient) {
        this.wechatPayClient = wechatPayClient;
    }

    /**
     * 代金券.
     *
     * @param tenantId the tenant id
     * @return the wechat marketing favor api
     */
    public WechatMarketingFavorApi favorApi(String tenantId) {
        return new WechatMarketingFavorApi(this.wechatPayClient, tenantId);
    }

    /**
     * 普通支付-直连模式.
     *
     * @param tenantId the tenant id
     * @return the wechat pay api
     */
    public WechatDirectPayApi directPayApi(String tenantId) {
        return new WechatDirectPayApi(wechatPayClient, tenantId);
    }

    /**
     * 合单支付.
     *
     * @param tenantId the tenant id
     * @return the wechat combine pay api
     */
    public WechatCombinePayApi combinePayApi(String tenantId) {
        return new WechatCombinePayApi(wechatPayClient, tenantId);
    }

    /**
     * 回调.
     *
     * @param tenantId the tenant id
     * @return the wechat pay callback
     */
    public WechatPayCallback callback(String tenantId) {
        return new WechatPayCallback(wechatPayClient.signatureProvider(), tenantId);
    }

}
