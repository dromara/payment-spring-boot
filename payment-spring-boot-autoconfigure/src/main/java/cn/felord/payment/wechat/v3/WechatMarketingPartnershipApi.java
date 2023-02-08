/*
 *  Copyright 2019-2023 felord.cn
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

package cn.felord.payment.wechat.v3;

import cn.felord.payment.PayException;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.partnership.Partner;
import cn.felord.payment.wechat.v3.model.partnership.PartnershipParams;
import cn.felord.payment.wechat.v3.model.partnership.PartnershipQueryParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 委托营销-直连商户
 *
 * @author felord.cn
 * @since 1.0.16.RELEASE
 */
public class WechatMarketingPartnershipApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatMarketingPartnershipApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 建立合作关系API
     * <p>
     * 该接口主要为商户提供营销资源的授权能力，可授权给其他商户或小程序，方便商户间的互利合作。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> build(PartnershipParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_PARTNERSHIPS_BUILD, params)
                .function((type, partnershipParams) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .build()
                            .toUri();
                    String idempotencyKey = partnershipParams.getIdempotencyKey();
                    partnershipParams.clearKey();
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("Idempotency-Key", idempotencyKey);
                    return Post(uri, partnershipParams, httpHeaders);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询合作关系列表API
     * <p>
     * 该接口主要为商户提供合作关系列表的查询能力。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> query(PartnershipQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_PARTNERSHIPS_GET, params)
                .function((type, partnershipParams) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    Partner partner = params.getPartner();

                    try {
                        ObjectMapper mapper = this.getMapper();
                        if (Objects.nonNull(partner)) {
                            String partnerJson = mapper.writeValueAsString(partner);
                            queryParams.add("partner", UriUtils.encode(partnerJson, StandardCharsets.UTF_8));
                        }
                        String authorizedDataJson = mapper.writeValueAsString(params.getAuthorizedData());

                        queryParams.add("authorized_data", UriUtils.encode(authorizedDataJson, StandardCharsets.UTF_8));
                    } catch (JsonProcessingException e) {
                        throw new PayException(e);
                    }
                    Integer limit = params.getLimit();
                    if (Objects.nonNull(limit)) {
                        queryParams.add("limit", limit.toString());
                    }
                    Integer offset = params.getOffset();
                    if (Objects.nonNull(offset)) {
                        queryParams.add("offset", offset.toString());
                    }
                    URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build(false)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }
}
