
package cn.felord.payment.wechat.v3.model.discountcard;


import lombok.Data;

/**
 * 预受理领卡请求参数
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class DiscountCardPreRequestParams {

    /**
     * 商户领卡号，必传
     */
    private String outCardCode;
    /**
     * 先享卡模板ID，必传
     */
    private String cardTemplateId;
    /**
     * APPID，必传，自动从配置中注入
     */
    private String appid;
    /**
     * 通知商户URL，必传
     */
    private String notifyUrl;

}
