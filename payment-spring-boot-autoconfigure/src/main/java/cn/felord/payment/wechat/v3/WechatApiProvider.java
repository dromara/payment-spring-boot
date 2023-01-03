/*
 *
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
 *
 */
package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.v2.WechatPayRedpackApi;
import cn.felord.payment.wechat.v2.WechatPayTransfersApi;
import cn.felord.payment.wechat.v2.WechatV2Client;
import cn.felord.payment.wechat.v3.ecommerce.WechatEcommerceApi;

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
     * 微信支付分停车服务.
     *
     * @param tenantId the tenant id
     * @return the wechat pay score parking api
     * @since 1.0.13.RELEASE
     */
    public WechatPayScoreParkingApi payScoreParkingApi(String tenantId) {
        return new WechatPayScoreParkingApi(wechatPayClient, tenantId);
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
     * 批量转账到零钱.
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
     * 直连商户微信支付分账，基于V3
     *
     * @param tenantId the tenant id
     * @return the wechat profitsharing api
     */
    public WechatProfitsharingApi profitsharingApi(String tenantId) {
        return new WechatProfitsharingApi(wechatPayClient, tenantId);
    }

    /**
     * 服务商微信支付分账，基于V3
     *
     * @param tenantId the tenant id
     * @return the wechat partner profitsharing api
     */
    public WechatPartnerProfitsharingApi partnerProfitsharingApi(String tenantId) {
        return new WechatPartnerProfitsharingApi(wechatPayClient, tenantId);
    }

    /**
     * 服务商品牌分账
     *
     * @param tenantId the tenant id
     * @return the wechat brand profitsharing api
     */
    public WechatBrandProfitsharingApi brandProfitsharingApi(String tenantId) {
        return new WechatBrandProfitsharingApi(wechatPayClient, tenantId);
    }

    /**
     * 微信V3服务商-商户进件-特约商户进件
     *
     * @param tenantId the tenant id
     * @return wechat partner special mch api
     * @since 1.0.14.RELEASE
     */
    public WechatPartnerSpecialMchApi partnerSpecialMchApi(String tenantId) {
        return new WechatPartnerSpecialMchApi(wechatPayClient, tenantId);
    }

    /**
     * 服务商或者直连商户-经营能力-支付即服务
     *
     * @param tenantId the tenant id
     * @return wechat smart guide api
     * @since 1.0.14.RELEASE
     */
    public WechatSmartGuideApi smartGuideApi(String tenantId) {
        return new WechatSmartGuideApi(wechatPayClient, tenantId);
    }

    /**
     * 服务商-经营能力-点金计划
     *
     * @param tenantId the tenant id
     * @return the wechat gold plan api
     * @since 1.0.14.RELEASE
     */
    public WechatGoldPlanApi goldPlanApi(String tenantId) {
        return new WechatGoldPlanApi(wechatPayClient, tenantId);
    }

    /**
     * 服务商-行业方案-电商收付通
     *
     * @param tenantId the tenant id
     * @return the wechat ecommerce api
     * @since 1.0.14.RELEASE
     */
    public WechatEcommerceApi ecommerceApi(String tenantId) {
        return new WechatEcommerceApi(wechatPayClient, tenantId);
    }

    /**
     * 服务商智慧商圈
     *
     * @param tenantId the tenant id
     * @return the wechat business circle api
     * @since 1.0.14.RELEASE
     */
    public WechatPartnerBusinessCircleApi partnerBusinessCircleApi(String tenantId) {
        return new WechatPartnerBusinessCircleApi(wechatPayClient, tenantId);
    }

    /**
     * 直连商户智慧商圈
     *
     * @param tenantId the tenant id
     * @return the wechat business circle api
     * @since 1.0.14.RELEASE
     */
    public WechatBusinessCircleApi businessCircleApi(String tenantId) {
        return new WechatBusinessCircleApi(wechatPayClient, tenantId);
    }

    /**
     * 其它能力-媒体上传
     *
     * @param tenantId the tenant id
     * @return the wechat media api
     * @since 1.0.14.RELEASE
     */
    public WechatMediaApi mediaApi(String tenantId) {
        return new WechatMediaApi(wechatPayClient, tenantId);
    }

    /**
     * 其它能力-银行组件（服务商）
     *
     * @param tenantId the tenant id
     * @return the wechat media api
     * @since 1.0.14.RELEASE
     */
    public WechatCapitalApi capitalApi(String tenantId) {
        return new WechatCapitalApi(wechatPayClient, tenantId);
    }

}
