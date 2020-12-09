
package cn.felord.payment.wechat.v3.model;

import lombok.Data;

import java.util.List;

@Data
public class TransactionConsumeData {

    private Amount amount;
    private String appid;
    private String attach;
    private String bankType;
    private String mchid;
    private String outTradeNo;
    private Payer payer;
    private List<PromotionDetail> promotionDetail;
    private SceneInfo sceneInfo;
    private String successTime;
    private String tradeState;
    private String tradeStateDesc;
    private String tradeType;
    private String transactionId;


    @Data
    public static class Payer {
        private String openid;
    }

    @Data
    public static class SceneInfo {
        private String deviceId;
    }

    @Data
    public static class Amount {
        private int total;
        private int payerTotal;
        private String currency;
        private String payerCurrency;
    }


}
