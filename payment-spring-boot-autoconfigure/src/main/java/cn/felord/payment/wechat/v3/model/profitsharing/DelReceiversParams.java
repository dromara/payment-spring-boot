package cn.felord.payment.wechat.v3.model.profitsharing;

import cn.felord.payment.wechat.enumeration.ReceiverType;
import lombok.Data;

/**
 * 直连商户-删除分账接收方API-请求参数
 *
 * @author felord.cn
 * @since 1.0.11.RELEASE
 */
@Data
public class DelReceiversParams {
    /**
     * 应用ID，自动注入
     */
    private String appid;
    /**
     * 分账接收方类型，必填
     */
    private ReceiverType type;
    /**
     * 分账接收方帐号，必填
     */
    private String account;
}