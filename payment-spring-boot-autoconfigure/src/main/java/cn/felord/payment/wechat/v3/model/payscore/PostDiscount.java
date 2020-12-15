
package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 微信支付回调请求参数.
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class PostDiscount {

    private String description;
    private String name;
}
