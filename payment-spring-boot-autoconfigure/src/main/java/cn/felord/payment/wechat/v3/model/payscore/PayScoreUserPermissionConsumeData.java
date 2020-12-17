
package cn.felord.payment.wechat.v3.model.payscore;


import lombok.Data;

/**
 * 授权、解除授权服务回调解密.
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class PayScoreUserPermissionConsumeData {

    /**
     * 是否是解除授权，此参数并非微信返回参数
     *
     * 是：true，不是：false
     *
     * 用来判断是授权还是解除授权
     */
    private boolean closed;
    /**
     * The Appid.
     */
    private String appid;
    /**
     * The Mchid.
     */
    private String mchid;
    /**
     * The Openid.
     */
    private String openid;
    /**
     * The Openorclose time.
     */
    private String openorcloseTime;
    /**
     * 只在 微信支付分授权回调中返回
     */
    private String outRequestNo;
    /**
     * The Service id.
     */
    private String serviceId;
    /**
     * The User service status.
     */
    private String userServiceStatus;

}
