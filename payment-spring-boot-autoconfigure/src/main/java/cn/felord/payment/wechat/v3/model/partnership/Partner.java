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

package cn.felord.payment.wechat.v3.model.partnership;

import cn.felord.payment.wechat.enumeration.PartnerType;
import lombok.Getter;

/**
 * The type Partner.
 *
 * @author felord.cn
 * @since 1.0.16.RELEASE
 */
@Getter
public class Partner {
    private final PartnerType type;
    private final String appid;
    private final String merchantId;

    /**
     * Instantiates a new Partner.
     *
     * @param type       the type
     * @param appid      the appid
     * @param merchantId the merchant id
     */
    Partner(PartnerType type, String appid, String merchantId) {
        this.type = type;
        this.appid = appid;
        this.merchantId = merchantId;
    }

    /**
     * 建立APP合作
     *
     * @param appid the appid
     * @return the partner
     */
    public static Partner app(String appid) {
        return new Partner(PartnerType.APPID, appid, null);
    }

    /**
     * 建立商户合作
     *
     * @param merchantId the merchant id
     * @return the partner
     */
    public static Partner merchant(String merchantId) {
        return new Partner(PartnerType.MERCHANT, null, merchantId);
    }
}
