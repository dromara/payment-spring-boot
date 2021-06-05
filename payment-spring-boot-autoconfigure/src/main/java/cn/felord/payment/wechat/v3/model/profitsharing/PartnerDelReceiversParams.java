package cn.felord.payment.wechat.v3.model.profitsharing;

import cn.felord.payment.wechat.enumeration.ReceiverType;
import lombok.Data;

/**
 * 服务商-删除分账接收方API-请求参数
 *
 * @author felord.cn
 * @since 1.0.11.RELEASE
 */
@Data
public class PartnerDelReceiversParams {
    /**
     * 子商户号，选填
     */
    private String subMchid;
    /**
     * 应用ID，自动注入
     */
    private String appid;
    /**
     * 子商户应用ID，选填
     * <p>
     * 分账接收方类型包含{@code PERSONAL_SUB_OPENID}时必填
     */
    private String subAppid;
    /**
     * 分账接收方类型，必填
     */
    private ReceiverType type;
    /**
     * 分账接收方帐号，必填
     */
    private String account;
}