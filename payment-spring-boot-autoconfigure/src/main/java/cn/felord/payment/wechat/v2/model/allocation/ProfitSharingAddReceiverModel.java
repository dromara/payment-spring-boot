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

package cn.felord.payment.wechat.v2.model.allocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Profit sharing add receiver model.
 *
 * @author wangzecheng
 * @since 1.0.10.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProfitSharingAddReceiverModel extends BaseProfitSharingReceiverModel {

    private Receiver receiver;

    /**
     * The type Receiver.
     */
    @Data
    public static class Receiver {

        /**
         * 分账接收方类型.
         * <p>
         * MERCHANT_ID：商户号（mch_id或者sub_mch_id）
         * PERSONAL_OPENID：个人openid
         */
        private Type type;

        /**
         * 分账接收方帐号.
         * <p>
         * 类型是MERCHANT_ID时，是商户号（mch_id或者sub_mch_id）
         * 类型是PERSONAL_OPENID时，是个人openid
         */
        private String account;

        /**
         * 分账接收方全称.
         * <p>
         * 分账接收方类型是MERCHANT_ID时，是商户全称（必传），当商户是小微商户或个体户时，是开户人姓名
         * 分账接收方类型是PERSONAL_OPENID时，是个人姓名（选传，传则校验）
         */
        private String name;

        /**
         * 与分账方的关系类型.
         * <p>
         * 子商户与接收方的关系。
         * 本字段值为枚举：
         * SERVICE_PROVIDER：服务商
         * STORE：门店
         * STAFF：员工
         * STORE_OWNER：店主
         * PARTNER：合作伙伴
         * HEADQUARTER：总部
         * BRAND：品牌方
         * DISTRIBUTOR：分销商
         * USER：用户
         * SUPPLIER：供应商
         * CUSTOM：自定义
         */
        private RelationType relationType;

        /**
         * 自定义的分账关系.
         * <p>
         * 子商户与接收方具体的关系，本字段最多10个字。
         * 当字段relation_type的值为CUSTOM时，本字段必填
         * 当字段relation_type的值不为CUSTOM时，本字段无需填写
         */
        private String customRelation;

    }

}
