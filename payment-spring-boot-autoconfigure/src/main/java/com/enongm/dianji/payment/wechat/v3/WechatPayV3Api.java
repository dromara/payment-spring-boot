package com.enongm.dianji.payment.wechat.v3;

import com.enongm.dianji.payment.PayException;
import com.enongm.dianji.payment.wechat.WechatPayProperties;
import com.enongm.dianji.payment.wechat.enumeration.V3PayType;
import com.enongm.dianji.payment.wechat.enumeration.WeChatServer;
import com.enongm.dianji.payment.wechat.v3.model.AppPayParams;
import com.enongm.dianji.payment.wechat.v3.model.StocksMchQueryParams;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Wechat pay v 3 api.
 *
 * @author Dax
 * @since 16 :15
 */
public class WechatPayV3Api {
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
    public WechatPayV3Api(WechatPayV3Client wechatPayV3Client, WechatMetaBean wechatMetaBean) {
        this.wechatPayV3Client = wechatPayV3Client;
        this.wechatMetaBean = wechatMetaBean;
    }


    /**
     * 激活代金券批次API
     *
     * @param stockId the stock id
     */
    public WechatResponseEntity<?> startStocks(String stockId) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        wechatPayV3Client.withType(V3PayType.MARKETING_FAVOR_STOCKS_START, stockId)
                .function(this::startStocksFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> startStocksFunction(V3PayType type, String stockId) {
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
     * 查询代金券可用商户API
     *
     * @param params the params
     */
    public WechatResponseEntity<?> queryMerchantsByStockId(StocksMchQueryParams params) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        wechatPayV3Client.withType(V3PayType.MARKETING_FAVOR_STOCKS_MERCHANTS, params)
                .function(this::queryMerchantsFunction)
                .consumer(wechatResponseEntity::convert)
                .request();

        return wechatResponseEntity;

    }


    private RequestEntity<?> queryMerchantsFunction(V3PayType type, StocksMchQueryParams params) {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("offset", String.valueOf(params.getOffset()));
        queryParams.add("limit", String.valueOf(params.getLimit()));
        queryParams.add("stock_creator_mchid", params.getStockCreatorMchid());
        String httpUrl = type.uri(WeChatServer.CHINA);

        URI uri = UriComponentsBuilder.fromHttpUrl(httpUrl)
                .queryParams(queryParams)
                .build()
                .expand(params.getStockId()).toUri();

        return RequestEntity.get(uri).build();
    }

    /**
     * APP下单API.
     *
     * @param payParams the pay params
     */
    public WechatResponseEntity<?> appPay(AppPayParams payParams) {
        WechatResponseEntity<ObjectNode> wechatResponseEntity = new WechatResponseEntity<>();
        wechatPayV3Client.withType(V3PayType.APP, payParams)
                .function(this::appPayFunction)
                .consumer(wechatResponseEntity::convert)
                .request();
        return wechatResponseEntity;
    }

    private RequestEntity<?> appPayFunction(V3PayType type, AppPayParams payParams) {
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
