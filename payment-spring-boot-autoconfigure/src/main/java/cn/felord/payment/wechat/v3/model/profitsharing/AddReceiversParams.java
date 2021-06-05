package cn.felord.payment.wechat.v3.model.profitsharing;

import cn.felord.payment.wechat.enumeration.ReceiverType;
import lombok.Data;

/**
 * 直连商户-添加分账接收方API-请求参数
 *
 * @author felord.cn
 * @since 1.0.11.RELEASE
 */
@Data
public class AddReceiversParams {
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
    /**
     * 分账个人接收方姓名，选填
     * <p>
     * 分账接收方类型是{@code MERCHANT_ID}时，是商户全称（必传），当商户是小微商户或个体户时，是开户人姓名 分账接收方类型是{@code PERSONAL_OPENID}时，是个人姓名（选传，传则校验）
     * <ol>
     *     <li>分账接收方类型是{@code PERSONAL_OPENID}，是个人姓名的密文（选传，传则校验） 此字段的加密方法详见：敏感信息加密说明</li>
     *     <li>使用微信支付平台证书中的公钥</li>
     *     <li>使用RSAES-OAEP算法进行加密</li>
     *     <li>将请求中HTTP头部的Wechatpay-Serial设置为证书序列号</li>
     * </ol>
     */
    private String name;
    /**
     * 与分账方的关系类型，必填
     */
    private RelationType relationType;
    /**
     * 自定义的分账关系，选填
     */
    private String customRelation;

    /**
     * 子商户与接收方的关系
     */
    public enum RelationType {
        /**
         * 门店.
         */
        STORE,
        /**
         * 员工.
         */
        STAFF,
        /**
         * 店主.
         */
        STORE_OWNER,
        /**
         * 合作伙伴.
         */
        PARTNER,
        /**
         * 总部.
         */
        HEADQUARTER,
        /**
         * 品牌方.
         */
        BRAND,
        /**
         * 分销商.
         */
        DISTRIBUTOR,
        /**
         * 用户.
         */
        USER,
        /**
         * 供应商.
         */
        SUPPLIER,
        /**
         * 自定义.
         */
        CUSTOM
    }
}