package cn.felord.payment.wechat.v3;

import lombok.Data;

import java.util.*;

/**
 * 配置容器
 *
 * @author Dax
 * @since 15 :18
 */
public class WechatMetaContainer {
    private final Map<String, WechatMetaBean> wechatMetaBeanMap = new HashMap<>();
    private final Set<String> tenantIds = new HashSet<>();


    /**
     * Add wechat meta boolean.
     *
     * @param tenantId            the tenantId
     * @param wechatMetaBean the wechat meta bean
     * @return the boolean
     */
    public boolean addWechatMeta(String tenantId, WechatMetaBean wechatMetaBean) {
        return Objects.nonNull(this.wechatMetaBeanMap.put(tenantId, wechatMetaBean));
    }

    /**
     * Remove wechat meta wechat meta bean.
     *
     * @param tenantId the tenantId
     * @return the wechat meta bean
     */
    public WechatMetaBean removeWechatMeta(String tenantId) {
        return this.wechatMetaBeanMap.remove(tenantId);
    }

    /**
     * Gets wechat meta.
     *
     * @param tenantId the tenantId
     * @return the wechat meta
     */
    public WechatMetaBean getWechatMeta(String tenantId) {
        return Objects.requireNonNull(this.wechatMetaBeanMap.get(tenantId));
    }

    /**
     * Add key boolean.
     *
     * @param tenantId the tenant id
     * @return the boolean
     */
    public boolean addTenant(String tenantId) {
        return tenantIds.add(tenantId);
    }

    /**
     * Gets properties keys.
     *
     * @return the properties keys
     */
    public Set<String> getTenantIds() {
        return tenantIds;
    }
}
