/*
 *  Copyright 2019-2021 felord.cn
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

package cn.felord.payment.wechat.v2;

import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.v2.model.RefundModel;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpMethod;

/**
 * 退款相关API.
 *
 * @author felord.cn
 * @since 1.0.5.RELEASE
 */
@Deprecated
public class WechatPayRefundApi {
    private final WechatV2Client wechatV2Client;

    /**
     * Instantiates a new Wechat pay refund api.
     *
     * @param wechatV2Client the wechat v 2 client
     */
    public WechatPayRefundApi(WechatV2Client wechatV2Client) {
        this.wechatV2Client = wechatV2Client;
    }

    /**
     * 退款
     *
     * @param refundModel the refund model
     * @return json node
     */
    public JsonNode transfer(RefundModel refundModel) {
        WechatPayProperties.V3 v3 = wechatV2Client.getWechatMetaBean().getV3();
        refundModel.setAppid(v3.getAppId());
        refundModel.setMchId(v3.getMchId());
        return wechatV2Client.wechatPayRequest(refundModel,
                HttpMethod.POST,
                "https://api.mch.weixin.qq.com/secapi/pay/refund");
    }
}
