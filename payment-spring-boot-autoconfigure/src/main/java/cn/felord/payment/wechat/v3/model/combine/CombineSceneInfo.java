
package cn.felord.payment.wechat.v3.model.combine;

import lombok.Data;

/**
 * 合单支付场景信息.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CombineSceneInfo {
    /**
     * 商户设备号，选填。
     */
    private String deviceId;
    /**
     * 用户终端ip,必填。
     */
    private String payerClientIp;
}
