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
import cn.felord.payment.wechat.enumeration.StockStatus;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.bouncycastle.jcajce.provider.digest.SHA256;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 微信支付营销代金券API
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class WechatMarketingFavorApi extends AbstractApi {


    /**
     * Instantiates a new Wechat marketing favor api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public WechatMarketingFavorApi(WechatPayClient wechatPayClient, String tenantId) {
        super(wechatPayClient, tenantId);
    }


    /**
     * 创建代金券批次API
     * <p>
     * 通过调用此接口可创建代金券批次，包括 <strong>预充值</strong> 和 <strong>免充值</strong> 类型。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> createStock(StocksCreateParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_COUPON_STOCKS, params)
                .function(this::createStocksFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * Create stocks function request entity.
     *
     * @param type   the type
     * @param params the params
     * @return the request entity
     */
    private RequestEntity<?> createStocksFunction(WechatPayV3Type type, StocksCreateParams params) {

        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();

        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
        String mchId = v3.getMchId();
        params.setBelongMerchant(mchId);
        return Post(uri, params);
    }

    /**
     * 激活代金券批次API
     * <p>
     * 制券成功后，通过调用此接口激活批次，如果是预充值代金券，激活时会从商户账户余额中锁定本批次的营销资金。
     *
     * @param stockId the stock id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> startStock(String stockId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_START, stockId)
                .function(this::startAndRestartAndPauseStockFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }


    /**
     * 发放代金券API
     * <p>
     * 商户侧开发时建议增加发放流水记录。
     * <p>
     * 微信支付文档所要求的微信公众号服务号不是必须的，只要你有一个绑定了微信支付商户平台和开放平台的appid即可。
     * <p>
     * 流程为：
     * 1. appid 请求授权微信登录。
     * 2. 登录成功后，开发者在商户侧保存用户 <strong>对应此appid的openid</strong>。
     * 3. 通过 appid - openid 进行发券。
     * <p>
     * 商户平台/API完成制券后，可使用发放代金券接口发券。通过调用此接口可发放指定批次给指定用户，发券场景可以是小程序、H5、APP等。
     * <p>
     * 注意：
     * • 商户可在H5活动页面、商户小程序、商户APP等自有场景内调用该接口完成发券，商户默认只允许发放本商户号（调用发券接口的商户号）创建的代金券，如需发放其他商户商户创建的代金券，请参考常见问题Q1。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> sendStock(StocksSendParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_USERS_COUPONS, params)
                .function(this::sendStocksFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }


    /**
     * Send stocks function request entity.
     *
     * @param type   the type
     * @param params the params
     * @return the request entity
     */
    private RequestEntity<?> sendStocksFunction(WechatPayV3Type type, StocksSendParams params) {
        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();

        params.setAppid(v3.getAppId());
        params.setStockCreatorMchid(v3.getMchId());
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .expand(params.getOpenid())
                .toUri();
        params.setOpenid(null);
        return Post(uri, params);
    }

    /**
     * 暂停代金券批次API
     * <p>
     * 通过此接口可暂停指定代金券批次。暂停后，该代金券批次暂停发放，用户无法通过任何渠道再领取该批次的券。
     *
     * @param stockId the stock id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> pauseStock(String stockId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_PAUSE, stockId)
                .function(this::startAndRestartAndPauseStockFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 重启代金券批次API
     * <p>
     * 通过此接口可重启指定代金券批次。重启后，该代金券批次可以再次发放。
     *
     * @param stockId the stock id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> restartStock(String stockId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_RESTART, stockId)
                .function(this::startAndRestartAndPauseStockFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * Start and restart and pause stock function request entity.
     *
     * @param type    the type
     * @param stockId the stock id
     * @return the request entity
     */
    private RequestEntity<?> startAndRestartAndPauseStockFunction(WechatPayV3Type type, String stockId) {
        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
        String mchId = v3.getMchId();
        Map<String, String> body = new HashMap<>();
        body.put("stock_creator_mchid", mchId);

        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .expand(stockId)
                .toUri();
        return Post(uri, body);
    }

    /**
     * 条件查询批次列表API
     * <p>
     * 通过此接口可查询多个批次的信息，包括批次的配置信息以及批次概况数据。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryStocksByMch(StocksQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS, params)
                .function(this::queryStocksByMchFunction)
                .consumer(wechatResponseEntity::convert)
                .request();

        return wechatResponseEntity;
    }

    /**
     * Query stocks function request entity.
     *
     * @param type   the type
     * @param params the params
     * @return the request entity
     */
    private RequestEntity<?> queryStocksByMchFunction(WechatPayV3Type type, StocksQueryParams params) {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("offset", String.valueOf(params.getOffset()));
        queryParams.add("limit", String.valueOf(params.getLimit()));
        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();

        queryParams.add("stock_creator_mchid", v3.getMchId());
        OffsetDateTime createStartTime = params.getCreateStartTime();
        if (Objects.nonNull(createStartTime)) {
            //rfc 3339   YYYY-MM-DDTHH:mm:ss.sss+TIMEZONE
            queryParams.add("create_start_time", createStartTime
                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        OffsetDateTime createEndTime = params.getCreateEndTime();
        if (Objects.nonNull(createEndTime)) {
            //rfc 3339   YYYY-MM-DDTHH:mm:ss.sss+TIMEZONE
            queryParams.add("create_end_time", createEndTime
                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        StockStatus status = params.getStatus();
        if (Objects.nonNull(status)) {
            queryParams.add("status", status.value());
        }

        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .queryParams(queryParams)
                .build().toUri();
        return Get(uri);
    }


    /**
     * 查询批次详情API
     * <p>
     * 通过此接口可查询批次信息，包括批次的配置信息以及批次概况数据。
     *
     * @param stockId the stock id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryStockDetail(String stockId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_DETAIL, stockId)
                .function(this::stockDetailFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }


    /**
     * Stock detail function request entity.
     *
     * @param type    the type
     * @param stockId the stock id
     * @return the request entity
     */
    private RequestEntity<?> stockDetailFunction(WechatPayV3Type type, String stockId) {
        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("stock_creator_mchid", v3.getMchId());

        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .queryParams(queryParams)
                .build()
                .expand(stockId)
                .toUri();
        return Get(uri);

    }

    /**
     * 查询代金券详情API
     * <p>
     * 通过此接口可查询代金券信息，包括代金券的基础信息、状态。如代金券已核销，会包括代金券核销的订单信息（订单号、单品信息等）。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryCouponDetails(CouponDetailsQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_USERS_COUPONS_DETAIL, params)
                .function(this::couponDetailFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }


    /**
     * Coupon detail function request entity.
     *
     * @param type   the type
     * @param params the params
     * @return the request entity
     */
    private RequestEntity<?> couponDetailFunction(WechatPayV3Type type, CouponDetailsQueryParams params) {

        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("appid", v3.getAppId());

        MultiValueMap<String, String> pathParams = new LinkedMultiValueMap<>();
        pathParams.add("openid", params.getOpenId());
        pathParams.add("coupon_id", params.getCouponId());
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .queryParams(queryParams)
                .build()
                .expand(pathParams)
                .toUri();
        return Get(uri);

    }

    /**
     * 查询代金券可用商户API
     * <p>
     * 通过调用此接口可查询批次的可用商户号，判断券是否在某商户号可用，来决定是否展示。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryMerchantsByStockId(MchQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_MERCHANTS, params)
                .function(this::queryMerchantsByStockIdFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * Query stocks function request entity.
     *
     * @param type   the type
     * @param params the params
     * @return the request entity
     */
    private RequestEntity<?> queryMerchantsByStockIdFunction(WechatPayV3Type type, MchQueryParams params) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("offset", String.valueOf(params.getOffset()));
        queryParams.add("limit", String.valueOf(params.getLimit()));
        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();
        queryParams.add("stock_creator_mchid", v3.getMchId());
        String stockId = params.getStockId();
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .queryParams(queryParams)
                .build()
                .expand(stockId)
                .toUri();
        return Get(uri);
    }

    /**
     * 查询代金券可用单品API
     * <p>
     * 通过此接口可查询批次的可用商品编码，判断券是否可用于某些商品，来决定是否展示。
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryStockItems(MchQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_ITEMS, params)
                .function(this::queryMerchantsByStockIdFunction)
                .consumer(wechatResponseEntity::convert)
                .request();

        return wechatResponseEntity;
    }

    /**
     * 根据商户号查用户的券API
     * <p>
     * 可通过该接口查询用户在某商户号可用的全部券，可用于商户的小程序/H5中，用户"我的代金券"或"提交订单页"展示优惠信息。无法查询到微信支付立减金。本接口查不到用户的微信支付立减金（又称“全平台通用券”），即在所有商户都可以使用的券，例如：摇摇乐红包；当按可用商户号查询时，无法查询用户已经核销的券
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryUserCouponsByMchId(UserCouponsQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_USERS_COUPONS, params)
                .function(this::queryUserCouponsFunction)
                .consumer(wechatResponseEntity::convert)
                .request();

        return wechatResponseEntity;
    }

    /**
     * Query user coupons function request entity.
     *
     * @param type   the type
     * @param params the params
     * @return the request entity
     */
    private RequestEntity<?> queryUserCouponsFunction(WechatPayV3Type type, UserCouponsQueryParams params) {

        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("appid", v3.getAppId());
        queryParams.add("creator_mchid", v3.getMchId());
        String senderMchId = params.getSenderMchId();
        if (StringUtils.hasText(senderMchId)) {
            queryParams.add("sender_mchid", senderMchId);
        }
        String availableMchId = params.getAvailableMchId();
        if (StringUtils.hasText(availableMchId)) {
            queryParams.add("available_mchid", availableMchId);
        } else {
            String offset = Objects.isNull(params.getOffset()) ? null : params.getOffset().toString();
            queryParams.add("offset", offset);
            String limit = Objects.isNull(params.getLimit()) ? null : params.getLimit().toString();
            queryParams.add("limit", limit);
            String status = Objects.nonNull(params.getStatus()) ? params.getStatus().name() : null;
            queryParams.add("status", status);
            String stockId = params.getStockId();
            if (StringUtils.hasText(stockId)) {
                queryParams.add("stock_id", stockId);
            }
        }


        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .queryParams(queryParams)
                .build()
                .expand(params.getOpenId())
                .toUri();
        return Get(uri);

    }

    /**
     * 下载批次核销明细API
     * <p>
     * 数据结果包含在响应体的 <strong>csv</strong> 字段中
     * <p>
     * 可获取到某批次的核销明细数据，包括订单号、单品信息、银行流水号等，用于对账/数据分析。
     *
     * @param stockId the stock id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> downloadStockUseFlow(String stockId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_USE_FLOW, stockId)
                .function(this::downloadFlowFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        String csv = billDownload(wechatResponseEntity.getBody().get("url").asText());
        wechatResponseEntity.getBody().put("csv", csv);
        return wechatResponseEntity;
    }

    /**
     * 下载批次退款明细API
     * <p>
     * 数据结果包含在响应体的 <strong>csv</strong> 字段中
     * <p>
     * 可获取到某批次的退款明细数据，包括订单号、单品信息、银行流水号等，用于对账/数据分析。
     *
     * @param stockId the stock id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> downloadStockRefundFlow(String stockId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_REFUND_FLOW, stockId)
                .function(this::downloadFlowFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        String csv = billDownload(wechatResponseEntity.getBody().get("url").asText());
        wechatResponseEntity.getBody().put("csv", csv);
        return wechatResponseEntity;
    }

    /**
     * Download flow function request entity.
     *
     * @param type    the type
     * @param stockId the stock id
     * @return the request entity
     */
    private RequestEntity<?> downloadFlowFunction(WechatPayV3Type type, String stockId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .expand(stockId)
                .toUri();
        return Get(uri);
    }

    /**
     * 营销图片上传API
     * <p>
     * 媒体图片只支持JPG、BMP、PNG格式，文件大小不能超过2M。
     * <p>
     * 通过本接口上传图片后可获得图片url地址。图片url可在微信支付营销相关的API使用，包括商家券、代金券、支付有礼等。
     *
     * @param file the file
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> marketingImageUpload(MultipartFile file) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_IMAGE_UPLOAD, file)
                .function(this::marketingImageUploadFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }


    /**
     * Marketing image upload function request entity.
     *
     * @param type the type
     * @param file the file
     * @return the request entity
     */
    @SneakyThrows
    private RequestEntity<?> marketingImageUploadFunction(WechatPayV3Type type, MultipartFile file) {

        Map<String, Object> meta = new LinkedHashMap<>(2);

        String originalFilename = file.getOriginalFilename();
        String filename = StringUtils.hasText(originalFilename) ? originalFilename : file.getName();
        meta.put("filename", filename);

        byte[] digest = SHA256.Digest.getInstance("SHA-256").digest(file.getBytes());
        meta.put("sha256", Hex.toHexString(digest));
        MultiValueMap<Object, Object> body = new LinkedMultiValueMap<>();
        body.add("meta", meta);
        body.add("file", file.getResource());
        // 签名
        String metaStr = this.getMapper().writeValueAsString(meta);

        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();
        return RequestEntity.post(uri)
                .header("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("Meta-Info", metaStr)
                .header("Pay-TenantId", tenantId())
                .body(body);
    }

    /**
     * 代金券核销回调通知API。
     * <p>
     * 设置核销回调通知的{@code notifyUrl},{@code notifyUrl}需要设置应用白名单。开发者应该对代金券的核销结果进行流水记录。
     *
     * @param notifyUrl the notify url
     * @return the wechat response entity
     * @see WechatPayCallback#couponCallback(ResponseSignVerifyParams, Consumer) 核销回调
     */
    public WechatResponseEntity<ObjectNode> setMarketingFavorCallback(String notifyUrl) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_CALLBACKS, notifyUrl)
                .function(this::setMarketingFavorCallbackFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * Sets marketing favor callback function.
     *
     * @param type      the type
     * @param notifyUrl the notify url
     * @return the marketing favor callback function
     */
    private RequestEntity<?> setMarketingFavorCallbackFunction(WechatPayV3Type type, String notifyUrl) {
        WechatPayProperties.V3 v3 = this.wechatMetaBean().getV3();

        Map<String, Object> body = new HashMap<>(3);
        body.put("mchid", v3.getMchId());
        body.put("notify_url", notifyUrl);
        body.put("switch", true);
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();
        return Post(uri, body);
    }

    /**
     * csv对账单下载。
     *
     * @param link the link
     * @return the string
     * @see WechatMarketingFavorApi#downloadStockUseFlow(String) 下载批次核销明细API
     * @see WechatMarketingFavorApi#downloadStockRefundFlow(String) 下载批次退款明细API
     */
    public String billDownload(String link) {
        return this.client().withType(WechatPayV3Type.FILE_DOWNLOAD, link)
                .function(this::billDownloadFunction)
                .download();
    }


    /**
     * Bill download function request entity.
     *
     * @param type the type
     * @param link the link
     * @return the request entity
     */
    private RequestEntity<?> billDownloadFunction(WechatPayV3Type type, String link) {
        URI uri = UriComponentsBuilder.fromHttpUrl(link)
                .build()
                .toUri();
        return Get(uri);
    }
}



