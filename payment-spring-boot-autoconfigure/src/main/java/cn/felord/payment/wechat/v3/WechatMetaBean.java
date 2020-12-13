package cn.felord.payment.wechat.v3;


import cn.felord.payment.wechat.WechatPayProperties;
import lombok.Data;

import java.security.KeyPair;

/**
 * 微信支付元数据Bean.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class WechatMetaBean {
    /**
     * The Key pair.
     */
    private KeyPair keyPair;
    /**
     * The Serial number.
     */
    private String serialNumber;
    /**
     * The Tenant id.
     */
    private String tenantId;
    /**
     * The V3.
     */
    private WechatPayProperties.V3 v3;

}
