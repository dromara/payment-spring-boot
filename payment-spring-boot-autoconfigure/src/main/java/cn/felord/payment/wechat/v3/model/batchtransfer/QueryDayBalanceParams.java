package cn.felord.payment.wechat.v3.model.batchtransfer;

import cn.felord.payment.wechat.enumeration.FundFlowAccountType;
import lombok.Data;

import java.time.LocalDate;

/**
 * 查询账户日终余额API 请求参数
 *
 * @author felord.cn
 * @since 1.0.11.RELEASE
 */
@Data
public class QueryDayBalanceParams {
    /**
     * 账户类型，必填
     * @see FundFlowAccountType
     */
    private FundFlowAccountType accountType;
    /**
     * 日期，必填
     */
    private LocalDate date;
}
