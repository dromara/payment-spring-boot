package cn.felord.payment.wechat.v3.model.batchtransfer;

import cn.felord.payment.wechat.enumeration.FundFlowAccountType;
import lombok.Data;

import java.time.LocalDate;

/**
 * 商户银行来账查询API 请求参数
 *
 * @author felord.cn
 * @since 1.0.11.RELEASE
 */
@Data
public class QueryIncomeRecordParams {
    /**
     * 账户类型，必填
     * @see FundFlowAccountType
     */
    private FundFlowAccountType accountType;
    /**
     * 日期，必填
     */
    private LocalDate date;
    /**
     * 本次查询偏移量，选填
     * <p>
     * 表示该次请求资源的起始位置，从0开始计数。调用方选填，默认为0。offset为20，limit为100时，查询第20-119条数据
     */
    private Integer offset;
    /**
     * 本次请求最大查询条数，必填
     * <p>
     * 非0非负的整数，该次请求可返回的最大资源条数，最大支持100条。
     */
    private Integer limit;
}
