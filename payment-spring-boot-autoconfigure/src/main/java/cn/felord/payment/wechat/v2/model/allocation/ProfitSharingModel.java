package cn.felord.payment.wechat.v2.model.allocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 单次分账请求按照传入的分账接收方账号和资金进行分账，同时会将订单剩余的待分账金额解冻给本商户。
 * 故操作成功后，订单不能再进行分账，也不能进行分账完结。
 *
 * @since 1.0.10.RELEASE
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProfitSharingModel extends BaseProfitSharingModel {

    /**
     * 分账接收方列表，不超过50个json对象，不能设置分账方作为分账接受方
     */
    private List<Receiver> receivers;

}
