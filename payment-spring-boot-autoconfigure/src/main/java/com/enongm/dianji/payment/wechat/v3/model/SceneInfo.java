package com.enongm.dianji.payment.wechat.v3.model;

import lombok.Data;

/**
 * @author Dax
 * @since 17:05
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
