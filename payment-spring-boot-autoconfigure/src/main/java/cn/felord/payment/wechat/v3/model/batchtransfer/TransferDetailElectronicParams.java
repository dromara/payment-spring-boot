package cn.felord.payment.wechat.v3.model.batchtransfer;

import cn.felord.payment.wechat.enumeration.TransferAcceptType;
import lombok.Data;

/**
 * 转账明细电子回单受理API请求参数
 *
 * @author felord.cn
 * @since 1.0.11.RELEASE
 */
@Data
public class TransferDetailElectronicParams {
    /**
     * 电子回单受理类型，必填。
     *
     * @see TransferAcceptType
     */
    private TransferAcceptType acceptType;
    /**
     * 商家转账批次单号，选填。
     * <p>
     * 需要电子回单的批量转账明细单所在的转账批次单号，该单号为商户申请转账时生成的商户单号。
     * 受理类型为{@code BATCH_TRANSFER}时该单号必填，否则该单号留空。
     */
    private String outBatchNo;
    /**
     * 商家转账明细单号，必填。
     * <p>
     * <ul>
     *     <li>受理类型为{@code BATCH_TRANSFER}时填写商家批量转账明细单号。</li>
     *     <li>受理类型为{@code TRANSFER_TO_POCKET}或{@code TRANSFER_TO_BANK}时填写商家转账单号。</li>
     * </ul>
     */
    private String outDetailNo;
}
