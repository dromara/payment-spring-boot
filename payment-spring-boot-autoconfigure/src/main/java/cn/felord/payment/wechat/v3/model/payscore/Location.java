
package cn.felord.payment.wechat.v3.model.payscore;

import lombok.Data;

/**
 * 服务位置信息
 * <p>
 * 如果传入，用户侧则显示此参数。
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
@Data
public class Location {

    /**
     * 服务开始地点，选填。
     * <p>
     * 开始使用服务的地点，不超过50个字符，超出报错处理。
     * 【建议】
     * 1、用户下单时【未确定】服务结束地点，不填写。
     * 2、服务在同一地点开始和结束，不填写。
     * 3、用户下单时【已确定】服务结束地点，填写。
     */
    private String startLocation;
    /**
     * 预计服务结束地点，有开始地点时为必填。
     *
     * 1、结束使用服务的地点，不超过50个字符，超出报错处理 。
     * 2、填写了服务开始地点，才能填写服务结束地点。
     */
    private String endLocation;
}
