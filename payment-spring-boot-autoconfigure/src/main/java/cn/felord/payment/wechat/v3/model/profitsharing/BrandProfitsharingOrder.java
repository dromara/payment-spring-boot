package cn.felord.payment.wechat.v3.model.profitsharing;

import lombok.Data;

import java.util.List;

/**
 * @author xiafang
 * @since 2023/1/3 10:27
 */
@Data
public class BrandProfitsharingOrder {
    private final String brandMchid;
    private final String subMchid;
    private String appid;
    private final String transactionId;
    private final String outOrderNo;
    private final List<Receiver> receivers;
    private final Boolean finish;
    private String subAppid;

}
