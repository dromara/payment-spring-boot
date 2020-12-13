package cn.felord.payment.wechat.v3;


import java.util.*;

/**
 * 配置容器
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
public class WechatMetaContainer {
    private final Map<String, WechatMetaBean> wechatMetaBeanMap = new HashMap<>();
    private final Set<String> tenantIds = new HashSet<>();


    /**
     * Add wechat meta boolean.
     *
     * @param tenantId       the tenantId
     * @param wechatMetaBean the wechat meta bean
     * @return the boolean
     */
    public WechatMetaBean addWechatMeta(String tenantId, WechatMetaBean wechatMetaBean) {
        tenantIds.add(tenantId);
        return this.wechatMetaBeanMap.put(tenantId, wechatMetaBean);
    }

    /**
     * Remove wechat meta wechat meta bean.
     *
     * @param tenantId the tenantId
     * @return the wechat meta bean
     */
    public WechatMetaBean removeWechatMeta(String tenantId) {
        tenantIds.remove(tenantId);
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
     * Gets properties keys.
     *
     * @return the properties keys
     */
    public Set<String> getTenantIds() {
        return tenantIds;
    }
}
