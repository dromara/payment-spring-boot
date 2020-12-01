package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 支付金额 货币单位【分】默认使用人民币标识CNY
 * @author Dax
 * @since 16:45
 */
@Data
public class Amount {
    private int total;
    private String currency ="CNY";
}
