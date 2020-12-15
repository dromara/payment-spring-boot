
package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 后付费商户优惠，选填
 * <p>
 * 最多包含30条商户优惠。如果传入，用户侧则显示此参数。
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class PostDiscount {

    /**
     * 优惠名称，条件选填
     *
     * 优惠名称说明；name和description若填写，则必须同时填写，优惠名称不可重复描述。
     */
    private String name;
    /**
     * 优惠说明，条件选填
     *
     * 优惠使用条件说明。{@link PostDiscount#name}若填写，则必须同时填写。
     */
    private String description;
    /**
     * 优惠数量，选填。
     * <p>
     * 优惠的数量。
     * 特殊规则：数量限制100，不填时默认1。
     */
    private Long count = 1L;
}
