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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 微信支付营销代金券API
 *
 * @author Dax
 * @since 18 :22
 */
public class WechatMarketingFavorApi extends AbstractApi {
    /**
     * Instantiates a new Wechat marketing api.
     *
     * @param wechatPayClient the wechat pay client
     * @param wechatMetaBean  the wechat meta bean
     */
    public WechatMarketingFavorApi(WechatPayClient wechatPayClient, WechatMetaBean wechatMetaBean) {
        super(wechatPayClient, wechatMetaBean);
    }

    /**
     * 创建代金券批次API
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

    private RequestEntity<?> createStocksFunction(WechatPayV3Type type, StocksCreateParams params) {
        WechatPayProperties.V3 v3 = this.meta().getWechatPayProperties().getV3();
        String mchId = v3.getMchId();
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .toUri();
        params.setBelongMerchant(mchId);
        return post(uri, params);
    }

    /**
     * 激活代金券批次API
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


    private RequestEntity<?> sendStocksFunction(WechatPayV3Type type, StocksSendParams params) {
        WechatPayProperties.V3 v3 = this.meta().getWechatPayProperties().getV3();
        // 服务号
        params.setAppid(v3.getMp().getAppId());
        params.setStockCreatorMchid(v3.getMchId());
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .expand(params.getOpenid())
                .toUri();
        params.setOpenid(null);
        return post(uri, params);
    }

    /**
     * 暂停代金券批次API
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

    private RequestEntity<?> startAndRestartAndPauseStockFunction(WechatPayV3Type type, String stockId) {
        WechatPayProperties.V3 v3 = this.meta().getWechatPayProperties().getV3();
        String mchId = v3.getMchId();
        Map<String, String> body = new HashMap<>();
        body.put("stock_creator_mchid", mchId);

        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .expand(stockId)
                .toUri();
        return post(uri, body);
    }

    /**
     * 条件查询批次列表API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryStocksByMch(StocksQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS, params)
                .function(this::queryStocksFunction)
                .consumer(wechatResponseEntity::convert)
                .request();

        return wechatResponseEntity;
    }


    private RequestEntity<?> queryStocksFunction(WechatPayV3Type type, StocksQueryParams params) {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("offset", String.valueOf(params.getOffset()));
        queryParams.add("limit", String.valueOf(params.getLimit()));
        WechatPayProperties.V3 v3 = this.meta().getWechatPayProperties().getV3();
        queryParams.add("stock_creator_mchid", v3.getMchId());
        LocalDateTime createStartTime = params.getCreateStartTime();
        if (Objects.nonNull(createStartTime)) {
            //rfc 3339   YYYY-MM-DDTHH:mm:ss.sss+TIMEZONE
            queryParams.add("create_start_time", createStartTime.atOffset(ZoneOffset.ofHours(8))
                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        LocalDateTime createEndTime = params.getCreateEndTime();
        if (Objects.nonNull(createEndTime)) {
            //rfc 3339   YYYY-MM-DDTHH:mm:ss.sss+TIMEZONE
            queryParams.add("create_end_time", createEndTime.atOffset(ZoneOffset.ofHours(8))
                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        }
        StockStatus status = params.getStatus();
        if (Objects.nonNull(status)) {
            queryParams.add("status", status.value());
        }

        String stockId = params.getStockId();

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .queryParams(queryParams)
                .build();

        if (StringUtils.hasText(stockId)) {
            uriComponents = uriComponents.expand(stockId);
        }

        URI uri = uriComponents
                .toUri();
        return RequestEntity.get(uri).build();
    }

    /**
     * 查询批次详情API
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


    private RequestEntity<?> stockDetailFunction(WechatPayV3Type type, String stockId) {
        WechatPayProperties.V3 v3 = this.meta().getWechatPayProperties().getV3();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("stock_creator_mchid", v3.getMchId());

        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .queryParams(queryParams)
                .build()
                .expand(stockId)
                .toUri();
        return RequestEntity.get(uri).build();

    }

    /**
     * 查询代金券详情API
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


    private RequestEntity<?> couponDetailFunction(WechatPayV3Type type, CouponDetailsQueryParams params) {

        WechatPayProperties.V3 v3 = this.meta().getWechatPayProperties().getV3();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("appid", v3.getMp().getAppId());

        MultiValueMap<String, String> pathParams = new LinkedMultiValueMap<>();
        pathParams.add("openid", params.getOpenId());
        pathParams.add("coupon_id", params.getCouponId());
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .queryParams(queryParams)
                .build()
                .expand(pathParams)
                .toUri();
        return RequestEntity.get(uri).build();

    }

    /**
     * 查询代金券可用商户API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryMerchantsByStockId(StocksQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_MERCHANTS, params)
                .function(this::queryStocksFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 查询代金券可用单品API
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryStockItems(StocksQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_ITEMS, params)
                .function(this::queryStocksFunction)
                .consumer(wechatResponseEntity::convert)
                .request();

        return wechatResponseEntity;
    }

    /**
     * 根据商户号查用户的券API
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

    private RequestEntity<?> queryUserCouponsFunction(WechatPayV3Type type, UserCouponsQueryParams params) {
        final String ignore = "available_mchid";
        WechatPayProperties.V3 v3 = this.meta().getWechatPayProperties().getV3();

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("appid", v3.getMp().getAppId());
        String stockId = params.getStockId();
        if (StringUtils.hasText(stockId)) {
            queryParams.add("stock_id", stockId);
        }
        String status = Objects.nonNull(params.getStatus()) ? params.getStatus().name() : ignore;
        queryParams.add("status", status);
        String creatorMchId = params.getCreatorMchId();
        if (StringUtils.hasText(creatorMchId)) {
            queryParams.add("creator_mchid", creatorMchId);
        }
        String senderMchId = params.getSenderMchId();
        if (StringUtils.hasText(senderMchId)) {
            queryParams.add("sender_mchid", senderMchId);
        }
        String availableMchId = params.getAvailableMchId();
        if (StringUtils.hasText(availableMchId)) {
            queryParams.add("available_mchid", availableMchId);
        }
        String offset = Objects.isNull(params.getOffset()) ? ignore : params.getOffset().toString();
        queryParams.add("offset", offset);
        String limit = Objects.isNull(params.getLimit()) ? ignore : params.getLimit().toString();
        queryParams.add("limit", limit);

        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .queryParams(queryParams)
                .build()
                .expand(params.getOpenId())
                .toUri();
        return RequestEntity.get(uri).build();

    }

    /**
     * 下载批次核销明细API
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

        return wechatResponseEntity;
    }

    /**
     * 下载批次退款明细API
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
        return wechatResponseEntity;
    }

    private RequestEntity<?> downloadFlowFunction(WechatPayV3Type type, String stockId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(type.uri(WeChatServer.CHINA))
                .build()
                .expand(stockId)
                .toUri();
        return RequestEntity.get(uri).build();
    }

    /**
     * 营销图片上传API
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


    @SneakyThrows
    private RequestEntity<?> marketingImageUploadFunction(WechatPayV3Type type, MultipartFile file) {

        Map<String, Object> meta = new LinkedHashMap<>(2);
        meta.put("filename", file.getOriginalFilename());

        byte[] digest = SHA256.Digest.getInstance("SHA-256").digest(file.getBytes());
        meta.put("sha256", Hex.toHexString(digest));
        String httpUrl = type.uri(WeChatServer.CHINA);
        URI uri = UriComponentsBuilder.fromHttpUrl(httpUrl).build().toUri();
        MultiValueMap<Object, Object> body = new LinkedMultiValueMap<>();
        body.add("meta", meta);
        body.add("file", file.getResource());
        // 签名
        String metaStr = this.getMapper().writeValueAsString(meta);
        return RequestEntity.post(uri)
                .header("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE)
                .header("Meta-Info", metaStr)
                .body(body);
    }

    /**
     * 代金券核销回调通知API
     * @param notifyUrl the notify url
     * @see WechatPayCallback#wechatPayCouponCallback(ResponseSignVerifyParams, Consumer)
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> setMarketingFavorCallback(String notifyUrl) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_CALLBACKS, notifyUrl)
                .function(this::setMarketingFavorCallbackFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> setMarketingFavorCallbackFunction(WechatPayV3Type type, String notifyUrl) {
        WechatPayProperties.V3 v3 = this.meta().getWechatPayProperties().getV3();
        Map<String, Object> body = new HashMap<>(3);
        body.put("mchid", v3.getMchId());
        body.put("notify_url", notifyUrl);
        body.put("switch", true);
        String httpUrl = type.uri(WeChatServer.CHINA);
        URI uri = UriComponentsBuilder.fromHttpUrl(httpUrl).build().toUri();
        return post(uri, body);
    }

}



