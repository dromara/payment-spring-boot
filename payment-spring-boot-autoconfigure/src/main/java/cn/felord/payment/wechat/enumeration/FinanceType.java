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

package cn.felord.payment.wechat.enumeration;

/**
 * 金融机构类型需与营业执照/登记证书上一致，可参考选择 <a href="https://kf.qq.com/faq/220215IrMRZ3220215n6buiU.html">金融机构指引</a>。
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
public enum FinanceType {

    /**
     * 银行业, 适用于商业银行、政策性银行、农村合作银行、村镇银行、开发性金融机构等
     */
    BANK_AGENT,
    /**
     * 支付机构, 适用于非银行类支付机构
     */
    PAYMENT_AGENT,
    /**
     * 保险业, 适用于保险、保险中介、保险代理、保险经纪等保险类业务
     */
    INSURANCE,
    /**
     * 交易及结算类金融机构, 适用于交易所、登记结算类机构、银行卡清算机构、资金清算中心等
     */
    TRADE_AND_SETTLE,
    /**
     * 其他金融机构, 适用于财务公司、信托公司、金融资产管理公司、金融租赁公司、汽车金融公司、贷款公司、货币经纪公司、消费金融公司、证券业、金融控股公司、股票、期货、货币兑换、小额贷款公司、金融资产管理、担保公司、商业保理公司、典当行、融资租赁公司、财经咨询等其他金融业务
     */
    OTHER
}
