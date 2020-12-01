package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.WechatPayProperties;
import cn.felord.payment.wechat.enumeration.StockStatus;
import cn.felord.payment.wechat.enumeration.WeChatServer;
import cn.felord.payment.wechat.enumeration.WechatPayV3Type;
import cn.felord.payment.wechat.v3.model.StocksCreateParams;
import cn.felord.payment.wechat.v3.model.StocksQueryParams;
import cn.felord.payment.wechat.v3.model.StocksSendParams;
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

/**
 * The type Wechat marketing api.
 *
 * @author Dax
 * @since 18 :22
 */
public class WechatMarketingApi extends AbstractApi {
    /**
     * Instantiates a new Wechat marketing api.
     *
     * @param wechatPayClient the wechat pay client
     * @param wechatMetaBean  the wechat meta bean
     */
    public WechatMarketingApi(WechatPayClient wechatPayClient, WechatMetaBean wechatMetaBean) {
        super(wechatPayClient, wechatMetaBean);
    }

    /**
     * 创建代金券批次API.
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
        String httpUrl = type.uri(WeChatServer.CHINA);
        URI uri = UriComponentsBuilder.fromHttpUrl(httpUrl).build().toUri();
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
                .function(this::startAndRestartStockFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    /**
     * 重启代金券批次API.
     *
     * @param stockId the stock id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> restartStock(String stockId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_RESTART, stockId)
                .function(this::startAndRestartStockFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> startAndRestartStockFunction(WechatPayV3Type type, String stockId) {
        WechatPayProperties.V3 v3 = this.meta().getWechatPayProperties().getV3();
        String mchId = v3.getMchId();
        Map<String, String> body = new HashMap<>();
        body.put("stock_creator_mchid", mchId);
        String httpUrl = type.uri(WeChatServer.CHINA);
        URI uri = UriComponentsBuilder.fromHttpUrl(httpUrl).build().expand(stockId).toUri();
        return post(uri, body);
    }

    /**
     * 查询批次详情API.
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

        String httpUrl = type.uri(WeChatServer.CHINA);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("stock_creator_mchid", v3.getMchId());
        URI uri = UriComponentsBuilder.fromHttpUrl(httpUrl).queryParams(queryParams).build().expand(stockId).toUri();
        return RequestEntity.get(uri).build();

    }


    /**
     * 查询该代金券可用的商户
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
     * 分页查询商户下的代金券批次.
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
     * 发放代金券API.
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
        String httpUrl = type.uri(WeChatServer.CHINA);
        URI uri = UriComponentsBuilder.fromHttpUrl(httpUrl).build().expand(params.getOpenid()).toUri();
        params.setOpenid(null);
        return post(uri, params);
    }

    /**
     * 营销图片上传API.
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
                .header("Meta-Info",metaStr)
                .body(body);
    }

    /**
     * 代金券核销回调通知.
     *
     * @param notifyUrl the notify url
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> marketingFavorCallback(String notifyUrl) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        this.client().withType(WechatPayV3Type.MARKETING_FAVOR_CALLBACKS, notifyUrl)
                .function(this::marketingFavorCallbackFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> marketingFavorCallbackFunction(WechatPayV3Type type, String notifyUrl) {
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



