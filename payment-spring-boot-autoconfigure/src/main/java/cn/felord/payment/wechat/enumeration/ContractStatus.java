package cn.felord.payment.wechat.enumeration;

/**
 * 微信先享卡的守约状态
 *
 * @author felord.cn
 * @since 1.0.2.RELEASE
 */
public enum ContractStatus {
    /**
     * 约定进行中，表示用户在约定有效期内，尚未完成所有目标时，守约状态为约定进行中。
     */
    ONGOING,

    /**
     * 约定到期核对中，在约定有效期结束后的一段时间，商户可对卡记录进行校对并做必要调整，守约状态为约定到期核对调整中。
     */
    SETTLING,
    /**
     * 已完成约定，表示用户在约定有效期内，已完成所有目标，守约状态为已完成约定。
     */
    FINISHED,

    /**
     * 未完成约定，表示用户在约定有效期到期后，最终未完成所有约定目标，或用户提前退出约定，守约状态为未完成约定。
     */
    UNFINISHED
}
