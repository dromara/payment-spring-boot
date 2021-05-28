package cn.felord.payment.wechat.enumeration;

/**
 * 批量转账到零钱 - 电子回单受理类型
 *
 * @author felord.cn
 * @since 1.0.11.RELEASE
 */
public enum TransferAcceptType {
    /**
     * 批量转账明细电子回单
     */
    BATCH_TRANSFER,
    /**
     * 企业付款至零钱电子回单
     */
    TRANSFER_TO_POCKET,
    /**
     * 企业付款至银行卡电子回单
     */
    TRANSFER_TO_BANK
}
