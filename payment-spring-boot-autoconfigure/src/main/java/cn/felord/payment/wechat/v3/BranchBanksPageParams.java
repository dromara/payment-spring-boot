package cn.felord.payment.wechat.v3;

import cn.felord.payment.wechat.v3.model.PageParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author felord.cn
 * @since 1.0.14.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BranchBanksPageParams extends PageParams {
    private String bankAliasCode;
    private Integer cityCode;
}
