package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 支付金额 货币单位【分】默认使用人民币标识CNY
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class Amount {
    /**
     * The Total.
     */
    private int total;
    /**
     * The Currency.
     */
    private String currency ="CNY";
}
