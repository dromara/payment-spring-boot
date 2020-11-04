package com.enongm.dianji.payment.wechat.v3.model;


import com.enongm.dianji.payment.wechat.WechatPayProperties;
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
    private WechatPayProperties wechatPayProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(wechatPayProperties, "wechatPayProperties must not be null");
        Assert.notNull(keyPair, "wechat pay p12 certificate has not loaded");
        Assert.hasText(serialNumber, "wechat pay p12 certificate SerialNumber must not null");
    }
}
