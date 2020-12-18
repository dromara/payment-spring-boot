package cn.felord.payment.wechat.enumeration;

/**
 * 目标完成类型、优惠使用类型
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
public enum StrategyType {
    /**
     * 增加数量，表示用户发生了履约行为
     */
    INCREASE,
    /**
     * 减少数量，表示取消用户的履约行为（例如用户取消购买、退货退款等）
     */
    DECREASE
}
