package cn.felord.payment.wechat.enumeration;


/**
 * 分账接收方类型
 *
 * @author n1
 * @since 2021/5/31 17:47
 */
public enum ReceiverType {
    /**
     * 商户号
     */
    MERCHANT_ID,
    /**
     * 个人openid（由父商户APPID转换得到）
     */
    PERSONAL_OPENID,
    /**
     * 个人sub_openid（由子商户APPID转换得到），服务商模式
     */
    PERSONAL_SUB_OPENID
}