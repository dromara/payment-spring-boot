package cn.felord.payment.wechat.v3;

import cn.felord.payment.PayException;
import cn.felord.payment.wechat.WechatPayProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.http.RequestEntity;
import org.springframework.util.Assert;

import java.net.URI;

/**
 * The type Abstract api.
 *
 * @author Dax
 * @since 18 :23
 */
public abstract class AbstractApi {
    private final ObjectMapper mapper;
    private final WechatPayClient wechatPayClient;
    private final String tenantId;


    /**
     * Instantiates a new Abstract api.
     *
     * @param wechatPayClient the wechat pay client
     * @param tenantId        the tenant id
     */
    public AbstractApi(WechatPayClient wechatPayClient, String tenantId) {
        this.mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.wechatPayClient = wechatPayClient;
        Assert.hasText(tenantId, "tenantId is required");
        if (!container().getTenantIds().contains(tenantId)) {
            throw new PayException("tenantId is not in wechatMetaContainer");
        }
        this.tenantId = tenantId;

    }

    /**
     * Gets mapper.
     *
     * @return the mapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * Client wechat pay client.
     *
     * @return the wechat pay client
     */
    public WechatPayClient client() {
        return wechatPayClient;
    }

    /**
     * Tenant id string.
     *
     * @return the string
     */
    public String tenantId() {
        return tenantId;
    }

    /**
     * Container wechat meta container.
     *
     * @return the wechat meta container
     */
    public WechatMetaContainer container() {
        return wechatPayClient.signatureProvider().wechatMetaContainer();
    }

    /**
     * Wechat meta bean wechat meta bean.
     *
     * @return the wechat meta bean
     */
    public WechatMetaBean wechatMetaBean() {
        return container().getWechatMeta(tenantId);
    }

    /**
     * Post request entity.
     *
     * @param uri    the uri
     * @param params the params
     * @return the request entity
     */
    protected RequestEntity<?> Post(URI uri, Object params) {
        try {
            return RequestEntity.post(uri).header("Pay-TenantId", tenantId)
                    .body(mapper.writeValueAsString(params));
        } catch (JsonProcessingException e) {
            throw new PayException("wechat app pay json failed");
        }
    }

    /**
     * Get request entity.
     *
     * @param uri the uri
     * @return the request entity
     */
    protected RequestEntity<?> Get(URI uri) {
        return RequestEntity.get(uri).header("Pay-TenantId", tenantId)
                .build();
    }
}
