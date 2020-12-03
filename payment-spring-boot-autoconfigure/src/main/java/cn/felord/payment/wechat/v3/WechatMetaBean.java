package cn.felord.payment.wechat.v3;


import cn.felord.payment.wechat.WechatPayProperties;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.security.KeyPair;

/**
 * @author Dax
 * @since 15:50
 */
@Data
public class WechatMetaBean implements InitializingBean {
    private KeyPair keyPair;
    private String serialNumber;
    private String tenantId;
    private WechatPayProperties.V3 v3;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(v3, "wechatPayProperties.V3 is required");
        Assert.notNull(keyPair, "wechat pay p12 certificate is required");
        Assert.hasText(serialNumber, "wechat pay p12 certificate SerialNumber is required");
        Assert.hasText(tenantId, "wechat pay tenantId is required");
    }
}
