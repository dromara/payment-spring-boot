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
import cn.felord.payment.wechat.v2.model.TransferInfoModel;
import cn.felord.payment.wechat.v2.model.TransferModel;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpMethod;

/**
 * 企业付款
 * <p>
 * TODO 暂时没有企业付款到银行卡
 *
 * @author felord.cn
 * @since 1.0.5.RELEASE
 */
public class WechatPayTransfersApi {
    private final WechatV2Client wechatV2Client;

    /**
     * Instantiates a new Wechat pay transfers api.
     *
     * @param wechatV2Client the wechat v 2 client
     */
    public WechatPayTransfersApi(WechatV2Client wechatV2Client) {
        this.wechatV2Client = wechatV2Client;
    }

    /**
     * 企业付款到零钱
     *
     * @param transferModel the transfer model
     * @return json node
     */
    public JsonNode transfer(TransferModel transferModel) {
        WechatPayProperties.V3 v3 = wechatV2Client.getWechatMetaBean().getV3();
        transferModel.setMchAppid(v3.getAppId());
        transferModel.setMchid(v3.getMchId());
        return wechatV2Client.wechatPayRequest(transferModel,
                HttpMethod.POST,
                "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
    }

    /**
     * 查询企业付款
     *
     * @param transferModel the transfer model
     * @return the json node
     */
    public JsonNode transferInfo(TransferInfoModel transferModel) {
        WechatPayProperties.V3 v3 = wechatV2Client.getWechatMetaBean().getV3();
        transferModel.setAppid(v3.getAppId());
        transferModel.setMchId(v3.getMchId());
        return wechatV2Client.wechatPayRequest(transferModel,
                HttpMethod.POST,
                "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo");
    }
}
