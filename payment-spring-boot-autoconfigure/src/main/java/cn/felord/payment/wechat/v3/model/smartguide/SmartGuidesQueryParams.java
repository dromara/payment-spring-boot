package cn.felord.payment.wechat.v3.model.smartguide;

import lombok.Data;

/**
 * 服务人员查询API参数
 *
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
@Data
public class SmartGuidesQueryParams {

    private Integer storeId;
    private String userid;
    private String mobile;
    private String workId;
    private Integer limit;
    private Integer offset;

}