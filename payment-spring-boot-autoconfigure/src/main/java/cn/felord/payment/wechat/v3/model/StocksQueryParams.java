package cn.felord.payment.wechat.v3.model;

import cn.felord.payment.wechat.enumeration.StockStatus;
import cn.felord.payment.wechat.v3.WechatMarketingFavorApi;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * 查询参数，适用以下接口：
 * <p>
 * 条件查询批次列表API、查询代金券可用商户API、查询代金券可用单品API
 *
 * @author Dax
 * @since 15 :16
 */
@Data
public class StocksQueryParams {
    /**
     * 必填
     * <p>
     * 条件查询批次列表API 页码从0开始，默认第0页，传递1可能出错。
     * <p>
     * 查询代金券可用商户API 分页页码，最大1000。
     * <p>
     * 查询代金券可用单品API 最大500。
     */
    private Integer offset = 0;
    /**
     * 必填
     * <p>
     * 条件查询批次列表API 分页大小，最大10。
     * <p>
     * 查询代金券可用商户API 最大50。
     * <p>
     * 查询代金券可用单品API 最大100。
     */
    private Integer limit = 10;
    /**
     * 根据API而定
     * <p>
     * 批次ID，对条件查询批次列表API{@link WechatMarketingFavorApi#queryStocksByMch(StocksQueryParams)}无效。
     */
    private String stockId;
    /**
     * 选填
     * <p>
     * 起始时间  最终满足格式 {@code yyyy-MM-dd'T'HH:mm:ss.SSSXXX}
     */
    private OffsetDateTime createStartTime;
    /**
     * 选填
     * <p>
     * 终止时间  最终满足格式 {@code yyyy-MM-dd'T'HH:mm:ss.SSSXXX}
     */
    private OffsetDateTime createEndTime;
    /**
     * 根据API而定
     * <p>
     * 批次状态，只对条件查询批次列表API{@link WechatMarketingFavorApi#queryStocksByMch(StocksQueryParams)}有效。
     */
    private StockStatus status;
}
