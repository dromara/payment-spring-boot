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

package cn.felord.payment.wechat.v2;

import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.v2.model.BaseModel;
import cn.felord.payment.wechat.v3.WechatMetaBean;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpMethod;

/**
 * 微信支付V2 客户端
 * <p>
 * V3接口不完善的临时性解决方案
 *
 * @author felord.cn
 * @since 1.0.5.RELEASE
 */
public class WechatV2Client {
    private final WechatMetaBean wechatMetaBean;

    public WechatV2Client(WechatMetaBean wechatMetaBean) {
        this.wechatMetaBean = wechatMetaBean;
    }

    public <M extends BaseModel> JsonNode wechatPayRequest(M model, HttpMethod method, String url) {
        WechatPayProperties.V3 v3 = wechatMetaBean.getV3();
        return model
                .appSecret(v3.getAppSecret())
                .certPath(v3.getCertPath())
                .certAbsolutePath(v3.getCertAbsolutePath())
                .request(v3.getMchId(), method, url);
    }

    public WechatMetaBean getWechatMetaBean() {
        return wechatMetaBean;
    }
}
