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

package cn.felord.payment.wechat.v3.ecommerce;

import cn.felord.payment.wechat.v3.WechatCombinePayApi;
import cn.felord.payment.wechat.v3.WechatPartnerPayApi;
import cn.felord.payment.wechat.v3.WechatPayClient;

/**
 * 电商收付通.
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
public class WechatEcommerceApi {
    private final WechatPayClient wechatPayClient;
    private final String tenantId;

    /**
     * Instantiates a new Wechat ecommerce api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatEcommerceApi(WechatPayClient wechatPayClient, String tenantId) {
        this.wechatPayClient = wechatPayClient;
        this.tenantId = tenantId;
    }

    /**
     * 商户进件
     *
     * @return the applyment api
     */
    public ApplymentApi applyment() {
        return new ApplymentApi(this.wechatPayClient, this.tenantId);
    }

    /**
     * 普通支付
     *
     * @return the wechat partner pay api
     */
    public WechatPartnerPayApi partnerPay() {
        return new WechatPartnerPayApi(this.wechatPayClient, this.tenantId);
    }

    /**
     * 合单支付
     *
     * @return the WechatCombinePayApi
     */
    public WechatCombinePayApi combinePay() {
        return new WechatCombinePayApi(this.wechatPayClient, this.tenantId);
    }

    /**
     * 分账
     *
     * @return the profitsharing api
     */
    public ProfitsharingApi profitsharing() {
        return new ProfitsharingApi(this.wechatPayClient, this.tenantId);
    }

    /**
     * 补差
     *
     * @return the subsidies api
     */
    public SubsidiesApi subsidies() {
        return new SubsidiesApi(this.wechatPayClient, this.tenantId);
    }

    /**
     * 退款
     *
     * @return the refunds api
     */
    public RefundsApi refunds() {
        return new RefundsApi(this.wechatPayClient, this.tenantId);
    }

    /**
     * 余额查询
     *
     * @return the balance api
     */
    public BalanceApi balance() {
        return new BalanceApi(this.wechatPayClient, this.tenantId);
    }

    /**
     * 商户提现
     *
     * @return the withdraw api
     */
    public WithDrawApi withDraw() {
        return new WithDrawApi(this.wechatPayClient, this.tenantId);
    }

    /**
     * 下载账单
     *
     * @return the withdraw api
     */
    public DownloadApi download() {
        return new DownloadApi(this.wechatPayClient, this.tenantId);
    }
}
