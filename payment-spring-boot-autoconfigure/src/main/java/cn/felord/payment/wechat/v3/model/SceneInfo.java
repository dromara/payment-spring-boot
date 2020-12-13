package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 场景信息
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class SceneInfo {
    /**
     * 用户终端IP
     */
    private String payerClientIp;
    /**
     * 商户端设备号
     */
    private String deviceId;
    /**
     * 商户门店信息
     */
    private StoreInfo storeInfo;
    /**
     * H5 场景信息
     */
    private H5Info h5Info;
}
