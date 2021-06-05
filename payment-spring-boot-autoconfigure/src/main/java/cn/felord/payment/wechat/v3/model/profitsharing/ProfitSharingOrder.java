package cn.felord.payment.wechat.v3.model.profitsharing;

import lombok.Data;

import java.util.List;

/**
 * 直连商户-请求分账API-请求参数
 *
 * @author felord.cn
 * @since 1.0.11.RELEASE
 */
@Data
public class ProfitSharingOrder {
    /**
     * 应用ID，自动注入
     */
    private String appid;
    /**
     * 微信订单号，必填
     */
    private String transactionId;
    /**
     * 商户分账单号，必填
     * <p>
     * 商户系统内部的分账单号，在商户系统内部唯一，同一分账单号多次请求等同一次。
     * 只能是数字、大小写字母_-|*@
     */
    private String outOrderNo;
    /**
     * 分账接收方列表，选填
     * <p>
     * 可以设置出资商户作为分账接受方，最多可有50个分账接收方
     */
    private List<Receiver> receivers;
    /**
     * 是否解冻剩余未分资金，必填
     * <p>
     * <ol>
     *     <li>如果为{@code true}，该笔订单剩余未分账的金额会解冻回分账方商户；</li>
     *     <li>如果为{@code false}，该笔订单剩余未分账的金额不会解冻回分账方商户，可以对该笔订单再次进行分账。</li>
     * </ol>
     */
    private Boolean unfreezeUnsplit;
}