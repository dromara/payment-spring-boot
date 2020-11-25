package com.enongm.dianji.payment.wechat.v3;

import com.enongm.dianji.payment.PayException;
import com.enongm.dianji.payment.wechat.WechatPayProperties;
import com.enongm.dianji.payment.wechat.enumeration.StockStatus;
import com.enongm.dianji.payment.wechat.enumeration.WeChatServer;
import com.enongm.dianji.payment.wechat.enumeration.WechatPayV3Type;
import com.enongm.dianji.payment.wechat.v3.model.AppPayParams;
import com.enongm.dianji.payment.wechat.v3.model.StocksCreateParams;
import com.enongm.dianji.payment.wechat.v3.model.StocksQueryParams;
import com.enongm.dianji.payment.wechat.v3.model.StocksSendParams;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The type Wechat pay v 3 api.
 *
 * @author Dax
 * @since 16 :15
 */
public class WechatPayApi {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final WechatPayV3Client wechatPayV3Client;
    private final WechatMetaBean wechatMetaBean;

    static {
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


    /**
     * Wechat Pay V3 Api.
     *
     * @param wechatPayV3Client the wechat pay v 3 client
     * @param wechatMetaBean    the wechat meta bean
     */
    public WechatPayApi(WechatPayV3Client wechatPayV3Client, WechatMetaBean wechatMetaBean) {
        this.wechatPayV3Client = wechatPayV3Client;
        this.wechatMetaBean = wechatMetaBean;
    }


    /**
     * 创建代金券批次API.
     *
     * @param params the params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> createStock(StocksCreateParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        wechatPayV3Client.withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_COUPON_STOCKS, params)
                .function(this::createStocksFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> createStocksFunction(WechatPayV3Type type, StocksCreateParams params) {
        WechatPayProperties.V3 v3 = wechatMetaBean.getWechatPayProperties().getV3();
        String mchId = v3.getMchId();
        String httpUrl = type.uri(WeChatServer.CHINA);
        URI uri = UriComponentsBuilder.fromHttpUrl(httpUrl).build().toUri();
        params.setBelongMerchant(mchId);
        try {
            return RequestEntity.post(uri)
                    .body(MAPPER.writeValueAsString(params));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new PayException("wechat app pay json failed");
    }

    /**
     * 激活代金券批次API
     *
     * @param stockId the stock id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> startStock(String stockId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        wechatPayV3Client.withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_START, stockId)
                .function(this::startStockFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> startStockFunction(WechatPayV3Type type, String stockId) {
        WechatPayProperties.V3 v3 = wechatMetaBean.getWechatPayProperties().getV3();
        String mchId = v3.getMchId();
        String httpUrl = type.uri(WeChatServer.CHINA);
        URI uri = UriComponentsBuilder.fromHttpUrl(httpUrl).build().expand(stockId).toUri();

        Map<String, String> map = new HashMap<>();
        map.put("stock_creator_mchid", mchId);
        try {
            return RequestEntity.post(uri)
                    .body(MAPPER.writeValueAsString(map));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new PayException("wechat app pay json failed");
    }

    /**
     * 查询批次详情API.
     *
     * @param stockId the stock id
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> queryStockDetail(String stockId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        wechatPayV3Client.withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_DETAIL, stockId)
                .function(this::stockDetailFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }


    private RequestEntity<?> stockDetailFunction(WechatPayV3Type type, String stockId) {
        WechatPayProperties.V3 v3 = wechatMetaBean.getWechatPayProperties().getV3();

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
        wechatPayV3Client.withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS_MERCHANTS, params)
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
        wechatPayV3Client.withType(WechatPayV3Type.MARKETING_FAVOR_STOCKS, params)
                .function(this::queryStocksFunction)
                .consumer(wechatResponseEntity::convert)
                .request();

        return wechatResponseEntity;
    }


    private RequestEntity<?> queryStocksFunction(WechatPayV3Type type, StocksQueryParams params) {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("offset", String.valueOf(params.getOffset()));
        queryParams.add("limit", String.valueOf(params.getLimit()));
        WechatPayProperties.V3 v3 = wechatMetaBean.getWechatPayProperties().getV3();
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
    public WechatResponseEntity<ObjectNode> sendStocks(StocksSendParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        wechatPayV3Client.withType(WechatPayV3Type.MARKETING_FAVOR_USERS_COUPONS, params)
                .function(this::sendStocksFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }


    private RequestEntity<?> sendStocksFunction(WechatPayV3Type type, StocksSendParams params) {

        String httpUrl = type.uri(WeChatServer.CHINA);
        URI uri = UriComponentsBuilder.fromHttpUrl(httpUrl).build().expand(params.getOpenid()).toUri();
        params.setOpenid(null);
        try {
            return RequestEntity.post(uri)
                    .body(MAPPER.writeValueAsString(params));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new PayException("wechat app pay json failed");
    }

    /**
     * APP下单API.
     *
     * @param payParams the pay params
     * @return the wechat response entity
     */
    public WechatResponseEntity<ObjectNode> appPay(AppPayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        wechatPayV3Client.withType(WechatPayV3Type.APP, payParams)
                .function(this::appPayFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> appPayFunction(WechatPayV3Type type, AppPayParams payParams) {
        WechatPayProperties.V3 v3 = wechatMetaBean.getWechatPayProperties().getV3();
        payParams.setAppid(v3.getAppId());
        payParams.setMchid(v3.getMchId());
        String httpUrl = type.uri(WeChatServer.CHINA);
        URI uri = UriComponentsBuilder.fromHttpUrl(httpUrl).build().toUri();
        try {
            return RequestEntity.post(uri)
                    .body(MAPPER.writeValueAsString(payParams));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new PayException("wechat app pay json failed");
    }

}
