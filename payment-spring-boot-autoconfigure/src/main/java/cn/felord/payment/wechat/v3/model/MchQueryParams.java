package cn.felord.payment.wechat.v3.model;

import lombok.Data;

/**
 * 查询代金券可用商户.
 *
 * @author felord.cn
 * @since 1.0.0.RELEASE
 */
@Data
public class MchQueryParams {
    /**
     * 必填
     *
     * 查询代金券可用商户API 分页页码，最大1000。
     */
    private Integer offset = 0;
    /**
     * 必填
     *
     * 查询代金券可用商户API 最大50。
     */
    private Integer limit = 10;
    /**
     * 批次ID
     */
    private String stockId;
}
