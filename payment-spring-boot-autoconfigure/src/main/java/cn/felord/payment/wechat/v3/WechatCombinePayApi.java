/*
 *
 *  Copyright 2019-2020 felord.cn
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
 *
 */
package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.enumeration.*;
import cn.felord.payment.wechat.v3.model.combine.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * 微信合单支付.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class WechatCombinePayApi extends AbstractApi {
    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatCombinePayApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }

    /**
     * 合单下单-APP支付API
     * <p>
     * 使用合单支付接口，用户只输入一次密码，即可完成多个订单的支付。目前最多一次可支持50笔订单进行合单支付。
     * <p>
     * 注意：
     * • 订单如果需要进行抽佣等，需要在合单中指定需要进行分账（profit_sharing为true）；指定后，交易资金进入二级商户账户，处于冻结状态，可在后续使用分账接口进行分账，利用分账完结进行资金解冻，实现抽佣和对二级商户的账期。
     *
     * @param combinePayParams the combine pay params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> appPay(CombinePayParams combinePayParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.COMBINE_APP, combinePayParams)
                .function(this::combinePayFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }


    /**
     * 合单下单-JSAPI支付/小程序支付API
     * <p>
     * 使用合单支付接口，用户只输入一次密码，即可完成多个订单的支付。目前最多一次可支持50笔订单进行合单支付。
     * <p>
     * 注意：
     * • 订单如果需要进行抽佣等，需要在合单中指定需要进行分账（profit_sharing为true）；指定后，交易资金进入二级商户账户，处于冻结状态，可在后续使用分账接口进行分账，利用分账完结进行资金解冻，实现抽佣和对二级商户的账期。
     *
     * @param combinePayParams the combine pay params
     * @return wechat response entity
     */
    public WechatResponseEntity<ObjectNode> jsPay(CombinePayParams combinePayParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.COMBINE_JSAPI, combinePayParams)
                .function(this::combinePayFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * Combine pay function request entity.
     *
     * @param type   the type
     * @param params the params
     * @return the request entity
     */
    private RequestEntity<?> combinePayFunction(WechatPayV3Type type, CombinePayParams params) {
        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
        params.setCombineAppid(v3.getAppId());
        params.setCombineMchid(v3.getMchId());

        String notifyUrl = v3.getDomain().concat(params.getNotifyUrl());
        params.setNotifyUrl(notifyUrl);
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();
        return Post(uri, params);
    }

    /**
     * 合单下单-H5支付API.
     * <p>
     * 使用合单支付接口，用户只输入一次密码，即可完成多个订单的支付。目前最多一次可支持50笔订单进行合单支付。
     * <p>
     * 注意：
     * • 订单如果需要进行抽佣等，需要在合单中指定需要进行分账（profit_sharing为true）；指定后，交易资金进入二级商户账户，处于冻结状态，可在后续使用分账接口进行分账，利用分账完结进行资金解冻，实现抽佣和对二级商户的账期。
     *
     * @param combineH5PayParams the combine h 5 pay params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> h5Pay(CombineH5PayParams combineH5PayParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.COMBINE_MWEB, combineH5PayParams)
                .function(this::combinePayFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * Combine pay function request entity.
     *
     * @param type   the type
     * @param params the params
     * @return the request entity
     */
    private RequestEntity<?> combinePayFunction(WechatPayV3Type type, CombineH5PayParams params) {
        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
        params.setCombineAppid(v3.getAppId());
        params.setCombineMchid(v3.getMchId());

        String notifyUrl = v3.getDomain().concat(params.getNotifyUrl());
        params.setNotifyUrl(notifyUrl);
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();
        return Post(uri, params);
    }

    /**
     * 合单下单-Native支付API.
     * <p>
     * 使用合单支付接口，用户只输入一次密码，即可完成多个订单的支付。目前最多一次可支持50笔订单进行合单支付。
     * <p>
     * 注意：
     * • 订单如果需要进行抽佣等，需要在合单中指定需要进行分账（profit_sharing为true）；指定后，交易资金进入二级商户账户，处于冻结状态，可在后续使用分账接口进行分账，利用分账完结进行资金解冻，实现抽佣和对二级商户的账期。
     *
     * @param combinePayParams the combine pay params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> nativePay(CombinePayParams combinePayParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.COMBINE_NATIVE, combinePayParams)
                .function(this::combinePayFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 合单查询订单API.
     *
     * @param combineOutTradeNo the combine out trade no
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryTransactionByOutTradeNo(String combineOutTradeNo) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.COMBINE_TRANSACTION_OUT_TRADE_NO, combineOutTradeNo)
                .function((wechatPayV3Type, outTradeNo) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build()
                            .expand(outTradeNo)
                            .toUri();
                    return Get(uri);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 合单关闭订单API.
     * <p>
     * 合单支付订单只能使用此合单关单api完成关单。
     * <p>
     * 微信服务器返回 204。
     *
     * @param combineCloseParams the combine close params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> close(CombineCloseParams combineCloseParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.COMBINE_CLOSE, combineCloseParams)
                .function((wechatPayV3Type, params) -> {
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .build().toUri();
                    return Post(uri, params);
                })
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 申请交易账单API
     * <p>
     * 微信支付按天提供交易账单文件，商户可以通过该接口获取账单文件的下载地址。文件内包含交易相关的金额、时间、营销等信息，供商户核对订单、退款、银行到账等情况。
     * <p>
     * 注意：
     * <ul>
     *     <li>微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致；</li>
     *     <li>对账单中涉及金额的字段单位为“元”；</li>
     *     <li>对账单接口只能下载三个月以内的账单。</li>
     *     <li>小微商户不单独提供对账单下载，如有需要，可在调取“下载对账单”API接口时不传sub_mch_id，获取服务商下全量电商二级商户（包括小微商户和非小微商户）的对账单。</li>
     * </ul>
     *
     * @param tradeBillParams tradeBillParams
     * @since 1.0.3.RELEASE
     */
    public void downloadTradeBill(TradeBillParams tradeBillParams) {
        this.client().withType(WechatPayV3Type.COMBINE_TRADEBILL, tradeBillParams)
                .function((wechatPayV3Type, params) -> {
                    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
                    LocalDate billDate = params.getBillDate();
                    queryParams.add("bill_date", billDate.format(DateTimeFormatter.ISO_DATE));
                    String subMchid = params.getSubMchid();

                    if (StringUtils.hasText(subMchid)) {
                        queryParams.add("sub_mchid", subMchid);
                    }

                    TradeBillType tradeBillType = Optional.ofNullable(params.getBillType())
                            .orElse(TradeBillType.ALL);
                    queryParams.add("bill_type", tradeBillType.name());
                    TarType tarType = params.getTarType();
                    if (Objects.nonNull(tarType)) {
                        queryParams.add("tar_type", tarType.name());
                    }
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build().toUri();
                    return Get(uri);
                })
                .consumer(response -> this.billDownload(Objects.requireNonNull(response.getBody()).get("download_url").asText()))
                .request();
    }

    /**
     * 申请资金账单API
     * <p>
     * 微信支付按天提供微信支付账户的资金流水账单文件，商户可以通过该接口获取账单文件的下载地址。文件内包含该账户资金操作相关的业务单号、收支金额、记账时间等信息，供商户进行核对。
     * <p>
     * 注意：
     * <ul>
     *     <li>资金账单中的数据反映的是商户微信支付账户资金变动情况；</li>
     *     <li>对账单中涉及金额的字段单位为“元”。</li>
     * </ul>
     *
     * @param fundFlowBillParams fundFlowBillParams
     * @since 1.0.3.RELEASE
     */
    public void downloadFundFlowBill(FundFlowBillParams fundFlowBillParams) {
        this.client().withType(WechatPayV3Type.COMBINE_FUNDFLOWBILL, fundFlowBillParams)
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
                    URI uri = UriComponentsBuilder.fromHttpUrl(wechatPayV3Type.uri(WeChatServer.CHINA))
                            .queryParams(queryParams)
                            .build().toUri();
                    return Get(uri);
                })
                .consumer(response -> this.billDownload(Objects.requireNonNull(response.getBody()).get("download_url").asText()))
                .request();
    }
}
