
package cn.felord.payment.wechat.v3.model.combine;


import lombok.Data;

/**
 * 合单支付订单金额信息.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class CombineAmount {
    /**
     * 符合ISO 4217标准的三位字母代码，必填，人民币：CNY 。
     *
     * @since 1.0.0.RELEASE
     */
    private String currency = "CNY";
    /**
     * 子单金额，单位为分，必填
     * <p>
     * 境外场景下，标价金额要超过商户结算币种的最小单位金额，例如结算币种为美元，则标价金额必须大于1美分
     *
     * @since 1.0.0.RELEASE
     */
    private Long totalAmount;
}
