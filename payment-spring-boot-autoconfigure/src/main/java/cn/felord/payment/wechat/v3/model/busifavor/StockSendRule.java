package cn.felord.payment.wechat.v3.model.busifavor;

import cn.felord.payment.wechat.enumeration.StockType;
import lombok.Data;

/**
 * 商家券发放规则.
 *
 * @author felord.cn
 * @since 1.0.4.RELEASE
 */
@Data
public class StockSendRule {

    /**
     * 批次最大发放个数
     * <p>
     * 特殊规则：取值范围 1 ≤ maxCoupons ≤ 1000000000
     */
    private Integer maxCoupons;
    /**
     * 用户最大可领个数
     * <p>
     * 每个用户最多100张券 。
     */
    private Integer maxCouponsPerUser;
    /**
     * 单天发放上限个数
     * <p>
     * {@link StockType#DISCOUNT}或者{@link StockType#DISCOUNT}时可传入此字段控制单天发放上限
     * <p>
     * 特殊规则：取值范围 1 ≤ maxCouponsByDay ≤ 1000000000
     */
    private Integer maxCouponsByDay;
    /**
     * 是否开启自然人限制
     */
    private Boolean naturalPersonLimit;
    /**
     * 可疑账号拦截
     */
    private Boolean preventApiAbuse;
    /**
     * 是否允许转赠
     */
    private Boolean transferable;
    /**
     * 是否允许分享链接
     */
    private Boolean shareable;
}