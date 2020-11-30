package com.enongm.dianji.payment.wechat.v3;

import com.enongm.dianji.payment.PayException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.http.RequestEntity;

import java.net.URI;

/**
 * @author Dax
 * @since 18:23
 */
public abstract class AbstractApi {
    private final ObjectMapper mapper;
    private final WechatPayClient wechatPayClient;
    private final WechatMetaBean wechatMetaBean;


    public AbstractApi(WechatPayClient wechatPayClient, WechatMetaBean wechatMetaBean) {
        this.mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.wechatPayClient = wechatPayClient;
        this.wechatMetaBean = wechatMetaBean;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public WechatPayClient getWechatPayClient() {
        return wechatPayClient;
    }

    public WechatMetaBean getWechatMetaBean() {
        return wechatMetaBean;
    }

    protected RequestEntity<?> postRequestEntity(URI uri, Object params) {
        try {
            return RequestEntity.post(uri)
                    .body(mapper.writeValueAsString(params));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new PayException("wechat app pay json failed");
    }
}
