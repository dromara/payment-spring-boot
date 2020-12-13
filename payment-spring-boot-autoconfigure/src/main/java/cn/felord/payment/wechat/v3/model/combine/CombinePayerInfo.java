
package cn.felord.payment.wechat.v3.model.combine;


import lombok.Data;

/**
 * 合单支付，支付者信息.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CombinePayerInfo {
    /**
     * 使用合单appid获取的对应用户openid。是用户在商户appid下的唯一标识。
     */
    private String openid;
}
