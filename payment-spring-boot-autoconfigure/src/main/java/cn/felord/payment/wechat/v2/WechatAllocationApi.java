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
import cn.felord.payment.wechat.v2.model.BaseModel;
import cn.felord.payment.wechat.v2.model.allocation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

import java.util.List;

/**
 * 微信支付分账
 * <p>
 *
 * @since 1.0.10.RELEASE
 */
@Slf4j
public class WechatAllocationApi {
    /**
     * The constant MAPPER.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .registerModule(new JavaTimeModule());
    }

    private final WechatV2Client wechatV2Client;

    /**
     * Instantiates a new Wechat allocation api.
     *
     * @param wechatV2Client the wechat v 2 client
     */
    public WechatAllocationApi(WechatV2Client wechatV2Client) {
        this.wechatV2Client = wechatV2Client;
    }

    /**
     * 请求单次分账
     *
     * @param profitSharingModel the profit sharing model
     * @return json node
     */
    @SneakyThrows
    public JsonNode profitSharing(ProfitSharingModel profitSharingModel) {
        ProfitSharingSModel profitSharingSModel = new ProfitSharingSModel();
        List<Receiver> receivers = profitSharingModel.getReceivers();
        profitSharingSModel.setReceivers(MAPPER.writeValueAsString(receivers));

        WechatPayProperties.V3 v3 = wechatV2Client.getWechatMetaBean().getV3();
        profitSharingSModel.setAppid(v3.getAppId());
        profitSharingSModel.setMchId(v3.getMchId());

        profitSharingSModel.setTransactionId(profitSharingModel.getTransactionId());
        profitSharingSModel.setOutOrderNo(profitSharingModel.getOutOrderNo());

        profitSharingSModel.certPath(v3.getCertPath());
        profitSharingSModel.signType(BaseModel.HMAC_SHA256);
        return wechatV2Client.wechatPayRequest(profitSharingSModel,
                HttpMethod.POST,
                "https://api.mch.weixin.qq.com/secapi/pay/profitsharing");
    }

    /**
     * 请求单次分账
     *
     * @param multiProfitSharingModel the multi profit sharing model
     * @return json node
     */
    @SneakyThrows
    public JsonNode multiProfitSharing(MultiProfitSharingModel multiProfitSharingModel) {
        MultiProfitSharingSModel multiProfitSharingSModel = new MultiProfitSharingSModel();
        List<Receiver> receivers = multiProfitSharingModel.getReceivers();
        multiProfitSharingSModel.setReceivers(MAPPER.writeValueAsString(receivers));

        WechatPayProperties.V3 v3 = wechatV2Client.getWechatMetaBean().getV3();
        multiProfitSharingSModel.setAppid(v3.getAppId());
        multiProfitSharingSModel.setMchId(v3.getMchId());

        multiProfitSharingSModel.setTransactionId(multiProfitSharingModel.getTransactionId());
        multiProfitSharingSModel.setOutOrderNo(multiProfitSharingModel.getOutOrderNo());

        multiProfitSharingSModel.certPath(v3.getCertPath());
        multiProfitSharingSModel.signType(BaseModel.HMAC_SHA256);
        return wechatV2Client.wechatPayRequest(multiProfitSharingSModel,
                HttpMethod.POST,
                "https://api.mch.weixin.qq.com/secapi/pay/multiprofitsharing");
    }

    /**
     * 查询分账结果
     *
     * @param profitSharingQueryModel the profit sharing query model
     * @return json node
     */
    public JsonNode profitSharingQuery(ProfitSharingQueryModel profitSharingQueryModel) {
        WechatPayProperties.V3 v3 = wechatV2Client.getWechatMetaBean().getV3();
        profitSharingQueryModel.setMchId(v3.getMchId());
        profitSharingQueryModel.certPath(v3.getCertPath());
        profitSharingQueryModel.signType(BaseModel.HMAC_SHA256);
        return wechatV2Client.wechatPayRequest(profitSharingQueryModel,
                HttpMethod.POST,
                "https://api.mch.weixin.qq.com/pay/profitsharingquery");
    }

    /**
     * 添加分账接收方
     *
     * @param profitSharingAddReceiverModel the profit sharing add receiver model
     * @return json node
     */
    @SneakyThrows
    public JsonNode profitSharingAddReceiver(ProfitSharingAddReceiverModel profitSharingAddReceiverModel) {
        ProfitSharingAddReceiverSModel profitSharingAddReceiverSModel = new ProfitSharingAddReceiverSModel();
        ProfitSharingAddReceiverModel.Receiver receiver = profitSharingAddReceiverModel.getReceiver();
        profitSharingAddReceiverSModel.setReceiver(MAPPER.writeValueAsString(receiver));

        WechatPayProperties.V3 v3 = wechatV2Client.getWechatMetaBean().getV3();
        profitSharingAddReceiverSModel.setAppid(v3.getAppId());
        profitSharingAddReceiverSModel.setMchId(v3.getMchId());

        profitSharingAddReceiverSModel.certPath(v3.getCertPath());
        profitSharingAddReceiverSModel.signType(BaseModel.HMAC_SHA256);
        return wechatV2Client.wechatPayRequest(profitSharingAddReceiverSModel,
                HttpMethod.POST,
                "https://api.mch.weixin.qq.com/pay/profitsharingaddreceiver");
    }

    /**
     * 删除分账接收方
     *
     * @param profitSharingRemoveReceiverModel the profit sharing remove receiver model
     * @return json node
     */
    @SneakyThrows
    public JsonNode profitSharingRemoveReceiver(ProfitSharingRemoveReceiverModel profitSharingRemoveReceiverModel) {
        ProfitSharingRemoveReceiverSModel profitSharingRemoveReceiverSModel = new ProfitSharingRemoveReceiverSModel();
        ProfitSharingRemoveReceiverModel.Receiver receiver = profitSharingRemoveReceiverModel.getReceiver();
        profitSharingRemoveReceiverSModel.setReceiver(MAPPER.writeValueAsString(receiver));

        WechatPayProperties.V3 v3 = wechatV2Client.getWechatMetaBean().getV3();
        profitSharingRemoveReceiverSModel.setAppid(v3.getAppId());
        profitSharingRemoveReceiverSModel.setMchId(v3.getMchId());

        profitSharingRemoveReceiverSModel.certPath(v3.getCertPath());
        profitSharingRemoveReceiverSModel.signType(BaseModel.HMAC_SHA256);
        return wechatV2Client.wechatPayRequest(profitSharingRemoveReceiverSModel,
                HttpMethod.POST,
                "https://api.mch.weixin.qq.com/pay/profitsharingremovereceiver");
    }

    /**
     * 完结分账
     *
     * @param profitSharingFinishModel the profit sharing finish model
     * @return json node
     */
    public JsonNode profitSharingFinish(ProfitSharingFinishModel profitSharingFinishModel) {
        WechatPayProperties.V3 v3 = wechatV2Client.getWechatMetaBean().getV3();
        profitSharingFinishModel.setAppid(v3.getAppId());
        profitSharingFinishModel.setMchId(v3.getMchId());
        profitSharingFinishModel.certPath(v3.getCertPath());
        profitSharingFinishModel.signType(BaseModel.HMAC_SHA256);
        return wechatV2Client.wechatPayRequest(profitSharingFinishModel,
                HttpMethod.POST,
                "https://api.mch.weixin.qq.com/secapi/pay/profitsharingfinish");
    }

    /**
     * 查询订单待分账金额
     *
     * @param profitSharingOrderAmountQueryModel the profit sharing order amount query model
     * @return json node
     */
    public JsonNode profitSharingOrderAmountQuery(ProfitSharingOrderAmountQueryModel profitSharingOrderAmountQueryModel) {
        WechatPayProperties.V3 v3 = wechatV2Client.getWechatMetaBean().getV3();
        profitSharingOrderAmountQueryModel.setMchId(v3.getMchId());
        profitSharingOrderAmountQueryModel.certPath(v3.getCertPath());
        profitSharingOrderAmountQueryModel.signType(BaseModel.HMAC_SHA256);
        return wechatV2Client.wechatPayRequest(profitSharingOrderAmountQueryModel,
                HttpMethod.POST,
                "https://api.mch.weixin.qq.com/pay/profitsharingorderamountquery");
    }

    /**
     * 分账回退
     *
     * @param profitSharingReturnModel the profit sharing return model
     * @return json node
     */
    public JsonNode profitSharingReturn(ProfitSharingReturnModel profitSharingReturnModel) {
        WechatPayProperties.V3 v3 = wechatV2Client.getWechatMetaBean().getV3();
        profitSharingReturnModel.setAppid(v3.getAppId());
        profitSharingReturnModel.setMchId(v3.getMchId());
        profitSharingReturnModel.certPath(v3.getCertPath());
        profitSharingReturnModel.signType(BaseModel.HMAC_SHA256);
        return wechatV2Client.wechatPayRequest(profitSharingReturnModel,
                HttpMethod.POST,
                "https://api.mch.weixin.qq.com/secapi/pay/profitsharingreturn");
    }

    /**
     * 回退结果查询
     *
     * @param profitSharingReturnQueryModel the profit sharing return query model
     * @return json node
     */
    public JsonNode profitSharingReturnQuery(ProfitSharingReturnQueryModel profitSharingReturnQueryModel) {
        WechatPayProperties.V3 v3 = wechatV2Client.getWechatMetaBean().getV3();
        profitSharingReturnQueryModel.setAppid(v3.getAppId());
        profitSharingReturnQueryModel.setMchId(v3.getMchId());
        profitSharingReturnQueryModel.certPath(v3.getCertPath());
        profitSharingReturnQueryModel.signType(BaseModel.HMAC_SHA256);
        return wechatV2Client.wechatPayRequest(profitSharingReturnQueryModel,
                HttpMethod.POST,
                "https://api.mch.weixin.qq.com/pay/profitsharingreturnquery");
    }

}
