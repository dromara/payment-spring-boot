
package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 订单风险金信息，必填
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class RiskFund {

    /**
     * 风险金名称，必填
     */
    private Type name;
    /**
     * 风险金额，必填
     * <p>
     * 1、数字，必须>0（单位分）。
     * 2、风险金额≤每个服务ID的风险金额上限。
     * 3、当商户优惠字段为空时，付费项目总金额≤服务ID的风险金额上限 （未填写金额的付费项目，视为该付费项目金额为0）。
     * 4、完结金额可大于、小于或等于风险金额。详细可见QA <a src = "https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/payscore/chapter11_2.shtml#menu1">关于订单风险金额问题</a>
     */
    private Long amount;
    /**
     * 风险说明，选填
     * <p>
     * 文字，不超过30个字。
     */
    private String description;


    /**
     * 风险金类型
     */
    enum Type {
        /**
         * 押金
         */
        DEPOSIT,
        /**
         * 预付款
         */
        ADVANCE,
        /**
         * 保证金
         */
        CASH_DEPOSIT,
        /**
         * 预估订单费用
         * <p>
         * 【先享模式】（评估不通过不可使用服务）
         */
        ESTIMATE_ORDER_COST
    }


}
