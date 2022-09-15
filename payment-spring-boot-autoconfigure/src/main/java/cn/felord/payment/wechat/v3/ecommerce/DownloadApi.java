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

import cn.felord.payment.wechat.enumeration.FundFlowAccountType;
import cn.felord.payment.wechat.enumeration.TarType;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.AbstractApi;
import cn.felord.payment.wechat.v3.WechatPartnerProfitsharingApi;
import cn.felord.payment.wechat.v3.WechatPayClient;
import cn.felord.payment.wechat.v3.WechatResponseEntity;
import cn.felord.payment.wechat.v3.model.FundFlowBillParams;
import cn.felord.payment.wechat.v3.model.profitsharing.PartnerProfitsharingBillParams;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * 电商收付通下载账单
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
public class DownloadApi extends AbstractApi {
    private final WechatPartnerProfitsharingApi wechatPartnerProfitsharingApi;

    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    DownloadApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
        this.wechatPartnerProfitsharingApi = new WechatPartnerProfitsharingApi(wechatPayClient, tenantId);
    }

    /**
     * 申请分账账单API
     *
     * @param params the params
     * @return the response entity
     */
    public ResponseEntity<Resource> downloadProfitsharingBills(PartnerProfitsharingBillParams params) {
        return this.wechatPartnerProfitsharingApi.downloadMerchantBills(params);
    }

    /**
     * 申请二级商户资金账单API
     * <p>
     * 过于复杂，需要合并文件并自行解密
     *
     * @param fundFlowBillParams the fund flow bill params
     * @return response entity
     */
    public WechatResponseEntity<ObjectNode> downloadMchFundFlowBill(FundFlowBillParams fundFlowBillParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.ECOMMERCE_FUND_FLOW_BILL, fundFlowBillParams)
                .function((wechatPayV3Type, params) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    LocalDate billDate = params.getBillDate();
                    queryParams.add("bill_date", billDate.format(DateTimeFormatter.ISO_DATE));

                    FundFlowAccountType accountType = Optional.ofNullable(params.getAccountType())
                            .orElse(FundFlowAccountType.BASIC);
                    queryParams.add("account_type", accountType.name());
                    TarType tarType = params.getTarType();
                    if (Objects.nonNull(tarType)) {
                        queryParams.add("tar_type", tarType.name());
                    }
                    queryParams.add("algorithm", "AEAD_AES_256_GCM");
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build().toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }
}
