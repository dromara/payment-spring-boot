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

import cn.felord.payment.wechat.v2.WechatAllocationApi;
import cn.felord.payment.wechat.v2.WechatPayRedpackApi;
import cn.felord.payment.wechat.v2.WechatPayTransfersApi;
import cn.felord.payment.wechat.v2.WechatV2Client;

/**
 * 微信支付工具.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class WechatApiProvider {
    /**
     * 微信支付客户端.
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
     * @since 1.0.0.RELEASE
     */
    public WechatMarketingFavorApi favorApi(String tenantId) {
        return new WechatMarketingFavorApi(this.wechatPayClient, tenantId);
    }

    /**
     * 普通支付-直连模式.
     *
     * @param tenantId the tenant id
     * @return the wechat pay api
     * @since 1.0.0.RELEASE
     */
    public WechatDirectPayApi directPayApi(String tenantId) {
        return new WechatDirectPayApi(wechatPayClient, tenantId);
    }


    /**
     * 普通支付-服务商模式.
     *
     * @param tenantId the tenant id
     * @return the wechat pay api
     * @since 1.0.9.RELEASE
     */
    public WechatPartnerPayApi partnerPayApi(String tenantId) {
        return new WechatPartnerPayApi(wechatPayClient, tenantId);
    }

    /**
     * 合单支付.
     *
     * @param tenantId the tenant id
     * @return the wechat combine pay api
     * @since 1.0.1.RELEASE
     */
    public WechatCombinePayApi combinePayApi(String tenantId) {
        return new WechatCombinePayApi(wechatPayClient, tenantId);
    }

    /**
     * 微信支付分.
     *
     * @param tenantId the tenant id
     * @return the wechat pay score api
     * @since 1.0.2.RELEASE
     */
    public WechatPayScoreApi payScoreApi(String tenantId) {
        return new WechatPayScoreApi(wechatPayClient, tenantId);
    }

    /**
     * 微信支付先享卡.
     *
     * @param tenantId the tenant id
     * @return the wechat discount card api
     * @since 1.0.2.RELEASE
     */
    public WechatDiscountCardApi discountCardApi(String tenantId) {
        return new WechatDiscountCardApi(wechatPayClient, tenantId);
    }

    /**
     * 微信支付商家券.
     *
     * @param tenantId the tenant id
     * @return the wechat discount card api
     * @since 1.0.4.RELEASE
     */
    public WechatMarketingBusiFavorApi busiFavorApi(String tenantId) {
        return new WechatMarketingBusiFavorApi(wechatPayClient, tenantId);
    }

    /**
     *  批量转账到零钱.
     * <p>
     * 批量转账到零钱提供商户同时向多个用户微信零钱转账的能力。商户可以使用批量转账到零钱用于费用报销、员工福利发放、合作伙伴货款或服务款项支付等场景，提高转账效率。
     *
     * @param tenantId the tenant id
     * @return the WechatBatchTransferApi
     * @since 1.0.6.RELEASE
     */
    public WechatBatchTransferApi batchTransferApi(String tenantId) {
        return new WechatBatchTransferApi(wechatPayClient, tenantId);
    }

    /**
     * 回调.
     * <p>
     * 需要处理白名单、幂等性问题。
     *
     * @param tenantId the tenant id
     * @return the wechat pay callback
     * @since 1.0.0.RELEASE
     */
    public WechatPayCallback callback(String tenantId) {
        return new WechatPayCallback(wechatPayClient.signatureProvider(), tenantId);
    }

    /**
     * 现金红包，基于V2
     *
     * @param tenantId the tenant id
     * @return wechat pay redpack api
     * @since 1.0.5.RELEASE
     */
    public WechatPayRedpackApi redpackApi(String tenantId) {
        WechatMetaBean wechatMeta = wechatPayClient.signatureProvider()
                .wechatMetaContainer()
                .getWechatMeta(tenantId);
        WechatV2Client wechatV2Client = new WechatV2Client(wechatMeta);
        return new WechatPayRedpackApi(wechatV2Client);
    }

    /**
     * 企业付款到零钱，目前不包括到银行卡，基于V2
     *
     * @param tenantId the tenant id
     * @return wechat pay redpack api
     * @since 1.0.5.RELEASE
     */
    public WechatPayTransfersApi transfersApi(String tenantId) {
        WechatMetaBean wechatMeta = wechatPayClient.signatureProvider()
                .wechatMetaContainer()
                .getWechatMeta(tenantId);
        WechatV2Client wechatV2Client = new WechatV2Client(wechatMeta);
        return new WechatPayTransfersApi(wechatV2Client);
    }

    /**
     * 微信支付分账，基于V2
     *
     * @param tenantId the tenant id
     * @return wechat allocation api
     * @since 1.0.10.RELEASE
     */
    public WechatAllocationApi allocationApi(String tenantId) {
        WechatMetaBean wechatMeta = wechatPayClient.signatureProvider()
                .wechatMetaContainer()
                .getWechatMeta(tenantId);
        WechatV2Client wechatV2Client = new WechatV2Client(wechatMeta);
        return new WechatAllocationApi(wechatV2Client);
    }

}
